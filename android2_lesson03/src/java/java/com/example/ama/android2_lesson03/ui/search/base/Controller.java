package com.example.ama.android2_lesson03.ui.search.base;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface Controller {
    void attachMap(GoogleMap map);

    void saveState();

    void tuneMyLocation();

    void showOnInnerMap(String markerTitle, String address, LatLng latLng);

    void moveMapCamera(LatLng latLng, float zoom, boolean cameraAnimation);

    void prepareToGMaps();

    interface SomeNameCallback {
        void onPermissionRequired(String permission, int requestCode);

        void clearAddress();
    }
}
