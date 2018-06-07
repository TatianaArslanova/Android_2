package com.example.ama.android2_lesson03.model.base;

import android.content.Context;
import android.location.Address;

import com.google.android.gms.maps.model.LatLng;

public interface LocManager {
    Address findAddressByQuery(Context context, String query);

    Address findAddressByCoords(Context context, LatLng coords);
}
