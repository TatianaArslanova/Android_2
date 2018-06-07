package com.example.ama.android2_lesson03.ui.search.base;

import android.net.Uri;

import com.example.ama.android2_lesson03.base.PocketMapView;
import com.google.android.gms.maps.model.LatLng;

public interface SearchOnTheMapView extends PocketMapView {
    void showOnGMapsApp(Uri uri);

    void showOnInnerMap(String address, LatLng latLng);

}
