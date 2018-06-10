package com.example.ama.android2_lesson03.ui.search.base;

import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface Controller extends ActivityCompat.OnRequestPermissionsResultCallback {
    void tuneMap();

    void attachMap(GoogleMap map);

    void detachMap();

    boolean isMapAttached();

    void setMarkerOnTheMap(String address, LatLng latLng, float zoom);

    void setCameraOnLocation(LatLng latLng);
}
