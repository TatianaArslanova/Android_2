package com.example.ama.android2_lesson03.repo.data;

import android.Manifest;
import android.location.Location;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.SearchQueryManager;
import com.example.ama.android2_lesson03.repo.base.SearchManager;
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Class for locating by Google Maps API
 */
public class LocationManagerGoogle extends BaseLocationManager {

    /**
     * Find user location by {@link FusedLocationProviderClient} and notify about result by callback
     *
     * @param callback for sending result
     */
    @Override
    public void findMyLocation(final SearchManager.OnLocationSearchResultCallback callback) {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(PocketMap.getInstance());
        if (PermissionManager.checkPermission(PocketMap.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        callback.onLocationFound(new LatLng(
                                        location.getLatitude(),
                                        location.getLongitude()),
                                SearchQueryManager.DEFAULT_ZOOM);
                    } else {
                        callback.onNotFound(PocketMap.getInstance().getString(R.string.message_location_not_found));
                    }
                }
            });
        } else {
            callback.onPermissionRequired(Manifest.permission.ACCESS_COARSE_LOCATION, PermissionManager.FIND_MY_LOCATION_REQUEST);
        }
    }
}
