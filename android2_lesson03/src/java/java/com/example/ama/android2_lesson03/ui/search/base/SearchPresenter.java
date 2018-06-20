package com.example.ama.android2_lesson03.ui.search.base;

import com.example.ama.android2_lesson03.ui.base.Presenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public interface SearchPresenter<T extends SearchOnTheMapView> extends Presenter<T> {
    void loadSavedState();

    void saveState(Marker currentMarker);

    void findAddressByQuery(String query);

    void findAddressByLatLng(LatLng latLng);

    void sendQueryToGMapsApp(Marker currentMarker, LatLng cameraPosition, float zoom);

    void findMyLocation();

    void onMarkerClick(Marker marker);

    void saveMarker(Marker currentMarker, String customName);

}
