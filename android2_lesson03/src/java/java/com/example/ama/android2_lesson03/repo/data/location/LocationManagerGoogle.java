package com.example.ama.android2_lesson03.repo.data.location;

import android.location.Location;
import android.os.Looper;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Class for locating by Google Maps API
 */
public class LocationManagerGoogle extends BaseLocationManager {

    private static final long REQUEST_INTERVAL = 3000L;

    private FusedLocationProviderClient client;
    private LocationCallback listener;

    public LocationManagerGoogle() {
        client = LocationServices.getFusedLocationProviderClient(PocketMap.getInstance());
    }

    /**
     * Find user location by {@link FusedLocationProviderClient} and notify about result by callback
     *
     * @param callback for sending result
     */

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void findMyLocation(final OnLocationSearchResultCallback callback) {
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    callback.onLocationFound(location);
                } else {
                    callback.onError(PocketMap.getInstance().getString(R.string.message_location_not_found));
                }
            }
        });
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void subscribeOnLocationChanges(OnLocationSearchResultCallback callback) {
        registerListener(callback);
        client.requestLocationUpdates(
                LocationRequest.create().setInterval(REQUEST_INTERVAL),
                listener,
                Looper.getMainLooper());
    }

    @Override
    public void unsubscribeOfLocationChanges() {
        if (listener != null) {
            client.removeLocationUpdates(listener);
            listener = null;
        }
    }

    private void registerListener(final OnLocationSearchResultCallback callback) {
        listener = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                    callback.onLocationFound(locationResult.getLocations().get(0));
                }
            }
        };
    }
}
