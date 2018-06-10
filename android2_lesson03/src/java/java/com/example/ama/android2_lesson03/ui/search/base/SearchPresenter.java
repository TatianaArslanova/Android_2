package com.example.ama.android2_lesson03.ui.search.base;

import com.example.ama.android2_lesson03.base.Presenter;
import com.google.android.gms.maps.model.LatLng;

public interface SearchPresenter<T extends SearchOnTheMapView> extends Presenter<T> {
    void findAddressByQuery(String query);

    void findAddressByLatLng(LatLng latLng);

    void sendQueryToGMapsApp(String query);

    void findMyLocation();
}
