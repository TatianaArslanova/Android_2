package com.example.ama.android2_lesson03.repo.data.base;

import android.location.Address;

import com.example.ama.android2_lesson03.repo.base.SearchManager;
import com.google.android.gms.maps.model.LatLng;

public interface LocManager {
    Address findAddressByQuery(String query);

    Address findAddressByCoords(LatLng coords);

    void findMyLocation(SearchManager.OnLocationSearchResultCallback callback);


}
