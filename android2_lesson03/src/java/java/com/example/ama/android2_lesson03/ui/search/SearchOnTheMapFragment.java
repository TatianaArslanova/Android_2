package com.example.ama.android2_lesson03.ui.search;

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

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.ui.Launcher;
import com.example.ama.android2_lesson03.ui.search.base.Controller;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class SearchOnTheMapFragment extends Fragment implements SearchOnTheMapView {

    private EditText etSearch;
    private TextView tvAddress;
    private MapView mapView;

    private SearchPresenter<SearchOnTheMapView> presenter;
    private Controller mapController;

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
        mapController = new MapController(
                presenter,
                new MapController.OnPermissionRequiredCallback() {
                    @Override
                    public void onPermissionRequired(String permission, int requestCode) {
                        requestPermission(permission, requestCode);
                    }
                });
        initUI(view);
        addListeners(view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapController.attachMap(googleMap);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mapController.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startSearching(String query) {
        presenter.findAddressByQuery(query);
    }

    private void prepareToGMap(String query) {
        presenter.sendQueryToGMapsApp(query);
    }

    @Override
    public void showOnGMapsApp(Uri uri) {
        Launcher.sendGoogleMapsIntent((AppCompatActivity) getActivity(), uri);
    }

    @Override
    public void showOnInnerMap(String address, LatLng latLng, float zoom) {
        tvAddress.setText(address);
        etSearch.setText(address);
        mapController.setMarkerOnTheMap(address, latLng, zoom);
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        PermissionManager.requestPermission(this, permission, requestCode);
    }

    @Override
    public void showErrorMessage(String message) {
        Launcher.showToast(message);
    }

    @Override
    public void zoomToLocation(LatLng latLng) {
        mapController.setCameraOnLocation(latLng);
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
