package com.example.ama.android2_lesson03.repo.data.base;

import android.location.Address;
import android.location.Geocoder;

import com.example.ama.android2_lesson03.PocketMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Base implementation for {@link LocManager}
 */
public abstract class BaseLocationManager implements LocManager {

    private Geocoder geocoder;

    public BaseLocationManager() {
        geocoder = new Geocoder(PocketMap.getInstance());
    }

    @Override
    public Address findAddressByQuery(String query) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(query, 1);
            if (!addresses.isEmpty()) {
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address findAddressByCoords(LatLng coords) {
        try {
            List<Address> addresses = geocoder.getFromLocation(coords.latitude, coords.longitude, 1);
            if (!addresses.isEmpty()) {
                return addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
