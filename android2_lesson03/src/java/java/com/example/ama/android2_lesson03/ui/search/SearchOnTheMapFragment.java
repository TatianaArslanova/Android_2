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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.ui.Launcher;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SearchOnTheMapFragment extends Fragment implements SearchOnTheMapView {

    public static final int PERMISSION_REQUEST = 1;
    public static final float ON_MAP_CLICK_ZOOM = 15f;
    public static final float ON_ADDRESS_FOUND_ZOOM = 10f;

    private EditText etSearch;
    private TextView tvAddress;
    private MapView mapView;

    private SearchPresenter<SearchOnTheMapView> presenter;
    private GoogleMap map;

    public static SearchOnTheMapFragment newInstance() {
        return new SearchOnTheMapFragment();
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
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                tuneMap();
            }
        });
        super.onViewCreated(view, savedInstanceState);
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
                prepareToGMap(etSearch.getText().toString());
            }
        });
    }

    private void tuneMap() {
        if (PermissionManager.checkPermission(PocketMap.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            map.setMyLocationEnabled(true);
            setMapUi();
        } else {
            PermissionManager.requestPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION, PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tuneMap();
            }
        }
    }

    private void setMapUi() {
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                setMarkerOnTheMap(tvAddress.getText().toString(), latLng, ON_MAP_CLICK_ZOOM);
            }
        });
    }

    private void startSearching(String query) {
        presenter.findAddress(query);
    }

    private void prepareToGMap(String query) {
        presenter.sendUserInput(query);
    }

    public void setMarkerOnTheMap(String address, LatLng latLng, float zoom) {
        map.clear();
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(address));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void showOnGMapsApp(Uri uri) {
        Launcher.sendGoogleMapsIntent((AppCompatActivity) getActivity(), uri);
    }

    @Override
    public void showOnInnerMap(String address, LatLng latLng) {
        tvAddress.setText(address);
        etSearch.setText(address);
        setMarkerOnTheMap(address, latLng, ON_ADDRESS_FOUND_ZOOM);
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
}
