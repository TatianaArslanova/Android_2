package com.example.ama.android2_lesson03.repo;

import android.location.Address;
import android.net.Uri;

import com.example.ama.android2_lesson03.repo.data.base.LocManager;
import com.example.ama.android2_lesson03.repo.base.QueryManager;
import com.example.ama.android2_lesson03.repo.data.LocationManagerAndroid;
import com.google.android.gms.maps.model.LatLng;

public class SearchQueryManager implements QueryManager {
    private LocManager locManager;

    public static final float DEFAULT_ZOOM = 15f;

    private static final String BASE_GEO_QUERY = "geo:0,0?q=";

    public SearchQueryManager() {
        locManager = new LocationManagerAndroid();
    }

    @Override
    public void prepareGMapUri(String query, OnUriPreparedCallback callback) {
        Uri uri = Uri.parse(BASE_GEO_QUERY + query);
        callback.onSuccess(uri);
    }

    @Override
    public void getFullLocationName(String query, OnFullNamePreparedCallback callback) {
        Address address = locManager.findAddressByQuery(query);
        if (address != null) {
            callback.onSuccess(
                    buildFullName(address),
                    new LatLng(address.getLatitude(), address.getLongitude()),
                    chooseZoom(address));
        }
    }

    @Override
    public void getFullLocationName(LatLng latLng, OnFullNamePreparedCallback callback) {
        Address address = locManager.findAddressByCoords(latLng);
        if (address != null) {
            callback.onSuccess(
                    buildFullName(address),
                    latLng,
                    chooseZoom(address)
            );
        }
    }

    @Override
    public void getMyLocation(OnLocationSearchResultCallback callback) {
        locManager.findMyLocation(callback);
    }

    private String buildFullName(Address address) {
        StringBuilder fullLocationName = new StringBuilder();
        if (address != null) {
            int index = address.getMaxAddressLineIndex();
            for (int i = 0; i <= index; i++) {
                if (fullLocationName.length() != 0) fullLocationName.append(", ");
                fullLocationName.append(address.getAddressLine(i));
            }
        }
        return fullLocationName.toString();
    }

    private float chooseZoom(Address address) {
        //TODO: chooseZoom
        return DEFAULT_ZOOM;
    }
}
