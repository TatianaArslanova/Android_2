package com.example.ama.android2_lesson03.ui.search;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.ui.Launcher;
import com.example.ama.android2_lesson03.ui.search.base.BaseMapFragment;
import com.example.ama.android2_lesson03.ui.search.base.Controller;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.example.ama.android2_lesson03.ui.search.mvp.SearchOnTheMapPresenter;
import com.example.ama.android2_lesson03.utils.DialogLauncher;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import io.reactivex.disposables.Disposable;

/**
 * Fragment for getting search user queries and showing on the map
 */
public class SearchOnTheMapFragment extends BaseMapFragment implements SearchOnTheMapView {

    private SearchPresenter<SearchOnTheMapView> presenter;
    private Controller mapController;
    private Disposable disposable;

    public static SearchOnTheMapFragment newInstance() {
        return new SearchOnTheMapFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter = new SearchOnTheMapPresenter<>();
        mapController = new MapController(presenter, new Controller.ClearAddressCallback() {
            @Override
            public void clearAddress() {
                etSearch.getText().clear();
                tvAddress.setText("");
            }
        });
        addListeners(view);
        super.onViewCreated(view, savedInstanceState);
        getMap();
    }

    private void getMap() {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapController.attachMap(googleMap);
                disposable = PermissionManager.requestPermission(
                        getActivity(),
                        PermissionManager.FINE_LOCATION,
                        new PermissionManager.OnGrantResult() {
                            @Override
                            public void sendResult(boolean granted) {
                                mapController.setLocationAccess(granted);
                            }
                        });
            }
        });
    }

    @Override
    public void onStart() {
        presenter.attachView(this);
        super.onStart();
    }

    @Override
    public void onResume() {
        mapController.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mapController.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mapController.saveState();
        presenter.detachView();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void showOnGMapsApp(Uri uri) {
        Launcher.sendGoogleMapsIntent((AppCompatActivity) getActivity(), uri);
    }

    @Override
    public void showOnInnerMap(String title, String address, LatLng latLng) {
        tvAddress.setText(address);
        etSearch.setText(address);
        mapController.showOnInnerMap(title, address, latLng);
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
