package com.example.ama.android2_lesson03.ui.search;

import com.example.ama.android2_lesson03.ui.search.base.Controller;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapController implements Controller {
    private GoogleMap map;
    private Marker currentMarker;

    private SearchPresenter<SearchOnTheMapView> presenter;
    private ClearAddressCallback callback;

    private boolean isLocationAccessEnabled;

    public MapController(SearchPresenter<SearchOnTheMapView> presenter, ClearAddressCallback callback) {
        this.presenter = presenter;
        this.callback = callback;
    }

    @Override
    public void attachMap(GoogleMap map) {
        this.map = map;
        tuneMap();
        presenter.loadSavedState();
    }

    @Override
    public void setLocationAccess(boolean enabled) {
        isLocationAccessEnabled = enabled;
        tuneMyLocation();
    }

    @Override
    public void onResume() {
        if (isLocationAccessEnabled) {
            presenter.subscribeOnLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        if (isLocationAccessEnabled) {
            presenter.unsubscribeOfLocationUpdates();
        }
    }

    @Override
    public void saveState() {
        presenter.saveState(currentMarker);
    }

    @Override
    public void showOnInnerMap(String title, String address, LatLng latLng) {
        map.clear();
        if (title == null || title.equals(address)) {
            currentMarker = setMarkerOnTheMap(address, latLng);
        } else {
            currentMarker = setMarkerOnTheMapWithTitle(title, address, latLng);
        }
        currentMarker.showInfoWindow();
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
    public void prepareToGMaps() {
        presenter.sendQueryToGMapsApp(
                currentMarker,
                map.getCameraPosition().target,
                map.getCameraPosition().zoom);
    }


    private void tuneMap() {
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(true);
        map.setOnMapLongClickListener(latLng -> presenter.findAddressByLatLng(latLng));
        map.setOnMapClickListener(latLng -> clearMap());
        map.setOnMarkerClickListener(marker -> {
            presenter.onMarkerClick(marker);
            return true;
        });
    }

    @SuppressWarnings({"MissingPermission"})
    private void tuneMyLocation() {
        if (map != null && isLocationAccessEnabled) {
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            if (currentMarker == null) {
                presenter.findMyLocation();
            }
        }
    }

    private void zoomToLocation(LatLng latLng, float zoom) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void setOnLocation(LatLng latLng, float zoom) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void clearMap() {
        map.clear();
        callback.clearAddress();
        currentMarker = null;
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
