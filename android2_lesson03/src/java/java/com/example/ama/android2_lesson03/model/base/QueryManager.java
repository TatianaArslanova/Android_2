package com.example.ama.android2_lesson03.model.base;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public interface QueryManager {
    void prepareGMapUri(String query, OnUriPreparedCallback callback);

    void getFullLocationName(String query, OnFullNamePreparedCallback callback);

    void getFullLocationName(LatLng latLng, OnFullNamePreparedCallback callback);

    void getMyLocation(OnLocationSearchResultCallback callback);

    interface OnUriPreparedCallback {
        void onSuccess(Uri uri);
    }

    interface OnFullNamePreparedCallback {
        void onSuccess(String fullLocationName, LatLng latLng, float zoom);
    }

    interface OnLocationSearchResultCallback {
        void onLocationFound(LatLng latLng);

        void onNotFound();

        void onPermissionRequired(String permission, int requestCode);
    }
}
