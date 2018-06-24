package com.example.ama.android2_lesson03.repo.data.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager;
import com.example.ama.android2_lesson03.utils.PermissionManager;

/**
 * Class for locating by android.location
 */
public class LocationManagerAndroid extends BaseLocationManager {

    private static final long REQUEST_MIN_TIME = 3000L;
    private static final float REQUEST_MIN_DISTANCE = 0;

    private LocationManager locManager;
    private LocationListener locListener;

    public LocationManagerAndroid() {
        super();
        locManager = (LocationManager) PocketMap.getInstance().getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Find user location by Passive Provider and notify about result by callback
     *
     * @param callback for sending result
     */

    @Override
    public void findMyLocation(OnLocationSearchResultCallback callback) {
        if (PermissionManager.checkPermission(PocketMap.getInstance(), PermissionManager.FINE_LOCATION)) {
            if (locManager != null) {
                Location location = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (location != null) {
                    callback.onLocationFound(location);
                } else {
                    callback.onError(PocketMap.getInstance().getString(R.string.message_location_not_found));
                }
            }
        } else {
            callback.onPermissionRequired(
                    PermissionManager.FINE_LOCATION,
                    PermissionManager.FIND_MY_LOCATION_REQUEST);
        }
    }

    @Override
    public void subscribeOnLocationChanges(OnLocationSearchResultCallback callback) {
        if (PermissionManager.checkPermission(PocketMap.getInstance(), PermissionManager.FINE_LOCATION)) {
            registerListener(callback);
            if (locManager != null) {
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, REQUEST_MIN_TIME, REQUEST_MIN_DISTANCE, locListener);
            }
        } else
            callback.onPermissionRequired(PermissionManager.FINE_LOCATION, PermissionManager.SUBSCRIBE_ON_LOCATION_UPDATES);
    }

    private void registerListener(final OnLocationSearchResultCallback callback) {
        locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    callback.onLocationFound(location);
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                switch (i) {
                    case LocationProvider.AVAILABLE:
                        Log.d("onStatusChanged", "LocationProvider avaliable");
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.d("onStatusChanged", "LocationProvider temporarily unavailable");
                        break;
                    case LocationProvider.OUT_OF_SERVICE:
                        callback.onError(PocketMap.getInstance().getString(R.string.message_location_provider_out_of_service));
                        Log.d("onStatusChanged", "LocationProvider out of service");
                        break;
                }
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("onProviderEnabled", "LocationProvider enabled");
            }

            @Override

            public void onProviderDisabled(String s) {
                Log.d("onProviderDisabled", "LocationProvider disabled");
            }
        };
    }

    @Override
    public void unsubscribeOfLocationChanges() {
        if (locListener != null) {
            locManager.removeUpdates(locListener);
            locListener = null;
        }
    }
}