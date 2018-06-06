package com.example.ama.android2_lesson03.model;

import android.location.Address;
import android.net.Uri;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.model.base.LocManager;
import com.example.ama.android2_lesson03.model.base.QueryManager;

public class UserQueryManager implements QueryManager {
    private LocManager locManager;

    private static final String BASE_GEO_QUERY = "geo:0,0?q=";

    public UserQueryManager() {
        locManager = new GeoLocationManager();
    }

    @Override
    public void prepareGMapUri(String query, OnUriPreparedCallback callback) {
        Uri uri = Uri.parse(BASE_GEO_QUERY + query);
        callback.onSuccess(uri);
    }

    @Override
    public void getFullLocationName(String query, OnFullNamePreparedCallback callback) {
        Address address = locManager.findAddressByQuery(PocketMap.getInstance(), query);
        StringBuilder fullLocationName = new StringBuilder();
        int index = address.getMaxAddressLineIndex();
        for (int i = 0; i <= index; i++) {
            if (fullLocationName.length() != 0) fullLocationName.append(", ");
            fullLocationName.append(address.getAddressLine(i));
        }
        callback.onSuccess(fullLocationName.toString());
    }
}
