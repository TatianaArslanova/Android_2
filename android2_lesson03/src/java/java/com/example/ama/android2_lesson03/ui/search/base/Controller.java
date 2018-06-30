package com.example.ama.android2_lesson03.ui.search.base;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface Controller {
    void attachMap(GoogleMap map);

    void setLocationAccess(boolean enabled);

    void onResume();

    void onPause();

    void saveState();

    void showOnInnerMap(String markerTitle, String address, LatLng latLng);

    void moveMapCamera(LatLng latLng, float zoom, boolean cameraAnimation);

    void prepareToGMaps();

    interface ClearAddressCallback {
        void clearAddress();
    }
}
