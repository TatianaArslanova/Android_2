package com.example.ama.android2_lesson03.ui.search.base;

import com.example.ama.android2_lesson03.repo.model.SimpleMarker;
import com.example.ama.android2_lesson03.ui.base.Presenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public interface SearchPresenter<T extends SearchOnTheMapView> extends Presenter<T> {
    void getCurrentMarker();

    void findAddressByQuery(String query);

    void findAddressByLatLng(LatLng latLng);

    void sendQueryToGMapsApp(boolean isMarkerOnTheMap, LatLng cameraPosition, float zoom);

    void findMyLocation();

    void saveMarker(Marker marker);

    void onSaveMarkerClick(Marker marker);
}
