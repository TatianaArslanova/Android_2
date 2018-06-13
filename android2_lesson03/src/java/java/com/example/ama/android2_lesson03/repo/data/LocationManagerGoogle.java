package com.example.ama.android2_lesson03.repo.data;

import android.Manifest;
import android.location.Location;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager;
import com.example.ama.android2_lesson03.repo.base.QueryManager;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationManagerGoogle extends BaseLocationManager {

    @Override
    public void findMyLocation(final QueryManager.OnLocationSearchResultCallback callback) {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(PocketMap.getInstance());
        if (PermissionManager.checkPermission(PocketMap.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        callback.onLocationFound(new LatLng(
                                location.getLatitude(),
                                location.getLongitude()));
                    } else {
                        callback.onNotFound();
                    }
                }
            });
        } else {
            callback.onPermissionRequired(Manifest.permission.ACCESS_COARSE_LOCATION, PermissionManager.FIND_MY_LOCATION_REQUEST);
        }
    }
}
