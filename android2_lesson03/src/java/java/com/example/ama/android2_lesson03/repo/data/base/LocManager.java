package com.example.ama.android2_lesson03.repo.data.base;

import android.location.Address;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Interface describes basic methods for work with locating
 */
public interface LocManager {
    Address findAddressByQuery(String query);

    Address findAddressByCoords(LatLng coords);

    void findMyLocation(OnLocationSearchResultCallback callback);

    void subscribeOnLocationChanges(OnLocationSearchResultCallback callback);

    void unsubscribeOfLocationChanges();

    interface OnLocationSearchResultCallback {
        void onLocationFound(Location location);

        void onError(String message);
    }
}
