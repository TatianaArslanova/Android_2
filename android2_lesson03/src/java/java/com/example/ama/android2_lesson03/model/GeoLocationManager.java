package com.example.ama.android2_lesson03.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.ama.android2_lesson03.model.base.LocManager;

import java.io.IOException;
import java.util.List;

public class GeoLocationManager implements LocManager {

    @Override
    public Address findAddressByQuery(Context context, String query) {
        Geocoder geo = new Geocoder(context);
        List<Address> addresses;
        try {
            addresses = geo.getFromLocationName(query, 1);
            return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
