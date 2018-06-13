package com.example.ama.android2_lesson03.repo.data;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.repo.data.base.BaseLocationManager;
import com.example.ama.android2_lesson03.repo.base.QueryManager;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.google.android.gms.maps.model.LatLng;

public class LocationManagerAndroid extends BaseLocationManager {
    @Override
    public void findMyLocation(QueryManager.OnLocationSearchResultCallback callback) {
        LocationManager locManager = (LocationManager) PocketMap.getInstance().getSystemService(Context.LOCATION_SERVICE);
        if (PermissionManager.checkPermission(PocketMap.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
            if (locManager != null) {
                Location location = locManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if (location != null) {
                    callback.onLocationFound(new LatLng(
                            location.getLatitude(),
                            location.getLongitude()));
                } else {
                    callback.onNotFound();
                }
            }
        } else {
            callback.onPermissionRequired(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    PermissionManager.FIND_MY_LOCATION_REQUEST);
        }
    }
}