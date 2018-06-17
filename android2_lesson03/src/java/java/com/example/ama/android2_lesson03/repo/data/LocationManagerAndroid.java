package com.example.ama.android2_lesson03.repo.data;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.SearchQueryManager;
import com.example.ama.android2_lesson03.repo.base.SearchManager;
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.maps.model.LatLng;

/**
 * Class for locating by android.location
 */
public class LocationManagerAndroid extends BaseLocationManager {

    /**
     * Find user location by Passive Provider and notify about result by callback
     *
     * @param callback for sending result
     */
    @Override
    public void findMyLocation(SearchManager.OnLocationSearchResultCallback callback) {
        LocationManager locManager = (LocationManager) PocketMap.getInstance().getSystemService(Context.LOCATION_SERVICE);
        if (PermissionManager.checkPermission(PocketMap.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            if (locManager != null) {
                Location location = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (location != null) {
                    callback.onLocationFound(new LatLng(
                                    location.getLatitude(),
                                    location.getLongitude()),
                            SearchQueryManager.DEFAULT_ZOOM);
                } else {
                    callback.onNotFound(PocketMap.getInstance().getString(R.string.message_location_not_found));
                }
            }
        } else {
            callback.onPermissionRequired(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    PermissionManager.FIND_MY_LOCATION_REQUEST);
        }
    }
}