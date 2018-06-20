package com.example.ama.android2_lesson03.ui.search;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.ui.Launcher;
import com.example.ama.android2_lesson03.ui.MainActivity;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Fragment for getting search user queries and showing on the map
 */
public class SearchOnTheMapFragment extends Fragment implements SearchOnTheMapView {

    private EditText etSearch;
    private TextView tvAddress;
    private MapView mapView;

    private SearchPresenter<SearchOnTheMapView> presenter;
    private GoogleMap map;

    private Marker currentMarker;

    public static SearchOnTheMapFragment newInstance() {
        return new SearchOnTheMapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new SearchOnTheMapPresenter<>();
        initUI(view);
        addListeners(view);
        mapView.onCreate(savedInstanceState);
        getMap();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                tuneMap();
                tuneMyLocation();
                presenter.loadSavedState();
                if (currentMarker == null) {
                    presenter.findMyLocation();
                }
            }
        });
    }

    @Override
    public void onStart() {
        presenter.attachView(this);
        mapView.onStart();
        super.onStart();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        presenter.saveState(currentMarker);
        presenter.detachView();
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_showsettings:
                Launcher.runMarkerListFragment((MainActivity) getActivity(), true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PermissionManager.TUNE_MY_LOCATION_REQUEST: {
                    tuneMyLocation();
                    break;
                }
                case PermissionManager.FIND_MY_LOCATION_REQUEST: {
                    presenter.findMyLocation();
                    break;
                }
            }
        }
    }

    @Override
    public void showOnGMapsApp(Uri uri) {
        if (getActivity() != null) {
            Launcher.sendGoogleMapsIntent((AppCompatActivity) getActivity(), uri);
        }
    }

    @Override
    public void showOnInnerMap(String title, String address, LatLng latLng) {
        map.clear();
        tvAddress.setText(address);
        etSearch.setText(address);
        if (title == null || title.equals(address)) {
            currentMarker = setMarkerOnTheMap(address, latLng);
        } else {
            currentMarker = setMarkerOnTheMapWithTitle(title, address, latLng);
        }
        currentMarker.showInfoWindow();
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        PermissionManager.requestPermission(this, permission, requestCode);
    }

    @Override
    public void showMessage(String message) {
        Launcher.showToast(message);
    }

    @Override
    public void moveMapCamera(LatLng latLng, float zoom, boolean cameraAnimation) {
        if (cameraAnimation) {
            zoomToLocation(latLng, zoom);
        } else {
            setOnLocation(latLng, zoom);
        }
    }

    @Override
    public void showDialog(String title, String message, final Marker marker) {
        Launcher.showDialog(getActivity(), title, message, marker.getTitle(), new Launcher.OnDialogResult() {
            @Override
            public void onPositiveResult(String inputText) {
                presenter.saveMarker(marker, inputText);
            }
        });
    }

    private void initUI(View view) {
        etSearch = view.findViewById(R.id.et_search);
        tvAddress = view.findViewById(R.id.tv_address);
        mapView = view.findViewById(R.id.mv_main_map);
    }

    private void addListeners(View view) {
        view.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearching(etSearch.getText().toString());
            }
        });
        view.findViewById(R.id.btn_ongmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareToGMap();
            }
        });
    }

    private void tuneMyLocation() {
        if (PermissionManager.checkPermission(PocketMap.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION, PermissionManager.TUNE_MY_LOCATION_REQUEST);
        }
    }

    private void tuneMap() {
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                presenter.findAddressByLatLng(latLng);
            }
        });
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                clearMap();
            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                presenter.onMarkerClick(marker);
                return true;
            }
        });
    }

    private void zoomToLocation(LatLng latLng, float zoom) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void setOnLocation(LatLng latLng, float zoom) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void clearMap() {
        map.clear();
        etSearch.getText().clear();
        tvAddress.setText("");
        currentMarker = null;
    }

    private void startSearching(String query) {
        presenter.findAddressByQuery(query);
    }

    private void prepareToGMap() {
        presenter.sendQueryToGMapsApp(
                currentMarker,
                map.getCameraPosition().target,
                map.getCameraPosition().zoom);
    }

    private Marker setMarkerOnTheMapWithTitle(String title, String address, LatLng position) {
        return map.addMarker(new MarkerOptions()
                .title(title)
                .snippet(address)
                .position(position));
    }

    private Marker setMarkerOnTheMap(String address, LatLng position) {
        return map.addMarker(new MarkerOptions()
                .title(address)
                .position(position));
    }
}
