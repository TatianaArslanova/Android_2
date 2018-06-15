package com.example.ama.android2_lesson03.ui.search.base;

import android.net.Uri;

import com.example.ama.android2_lesson03.ui.base.PocketMapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public interface SearchOnTheMapView extends PocketMapView {
    void showOnGMapsApp(Uri uri);

    void showOnInnerMap(String address, LatLng latLng, float zoom);

    void requestPermission(String permission, int requestCode);

    void showMessage(String message);

    void zoomToLocation(LatLng latLng);

    void showDialog(String title, String message, Marker marker);
}
