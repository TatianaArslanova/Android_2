package com.example.ama.android2_lesson03.ui.search;

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
import android.widget.Toast;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.ui.Launcher;
import com.example.ama.android2_lesson03.ui.MainActivity;
import com.example.ama.android2_lesson03.ui.search.base.Controller;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter;
import com.example.ama.android2_lesson03.utils.DialogLauncher;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Fragment for getting search user queries and showing on the map
 */
public class SearchOnTheMapFragment extends Fragment implements SearchOnTheMapView {

    private EditText etSearch;
    private TextView tvAddress;
    private MapView mapView;

    private SearchPresenter<SearchOnTheMapView> presenter;
    private Controller mapController;

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
        mapController = new MapController(presenter, new Controller.SomeNameCallback() {
            @Override
            public void onPermissionRequired(String permission, int requestCode) {
                requestPermission(permission, requestCode);
            }

            @Override
            public void clearAddress() {
                etSearch.getText().clear();
                tvAddress.setText("");
            }
        });
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
                mapController.attachMap(googleMap);
                mapController.tuneMyLocation();
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
        presenter.subscribeOnLocationUpdates();
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        presenter.unsubscribeOfLocationUpdates();
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mapController.saveState();
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
                    mapController.tuneMyLocation();
                    break;
                }
                case PermissionManager.SUBSCRIBE_ON_LOCATION_UPDATES: {
                    presenter.subscribeOnLocationUpdates();
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
        tvAddress.setText(address);
        etSearch.setText(address);
        mapController.showOnInnerMap(title, address, latLng);
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        PermissionManager.requestPermission(this, permission, requestCode);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(PocketMap.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moveMapCamera(LatLng latLng, float zoom, boolean cameraAnimation) {
        mapController.moveMapCamera(latLng, zoom, cameraAnimation);
    }

    @Override
    public void showDialog(String title, String message, final Marker marker) {
        DialogLauncher.showEditDialog(getActivity(), title, message, marker.getTitle(), new DialogLauncher.OnDialogResult() {
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
                mapController.prepareToGMaps();
            }
        });
    }

    private void startSearching(String query) {
        presenter.findAddressByQuery(query);
    }
}
