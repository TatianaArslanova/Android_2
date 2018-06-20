package com.example.ama.android2_lesson03.ui.search.base;

import android.net.Uri;

import com.example.ama.android2_lesson03.ui.base.PocketMapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public interface SearchOnTheMapView extends PocketMapView {
    void showOnGMapsApp(Uri uri);

    void showOnInnerMap(String markerTitle, String address, LatLng latLng);

    void requestPermission(String permission, int requestCode);

    void showMessage(String message);

    void moveMapCamera(LatLng latLng, float zoom, boolean cameraAnimation);

    void showDialog(String title, String message, Marker marker);
}
