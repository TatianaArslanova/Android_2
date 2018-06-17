package com.example.ama.android2_lesson03.repo.base;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Interface describes methods for search manager queries
 */
public interface SearchManager {
    void getPreparedUri(boolean isMarkerOnTheMap, LatLng cameraPosition, float zoom, OnUriPreparedCallback callback);

    void getFullLocationName(String query, OnFullNamePreparedCallback callback);

    void getFullLocationName(LatLng latLng, OnFullNamePreparedCallback callback);

    void getMyLocation(OnLocationSearchResultCallback callback);

    void prepareSaveMarkerDialog(Marker marker, OnDialogDataPrepared callback);

    void saveMarker(Marker marker, OnMarkerSavedCallback callback);

    void getCurrentMarker(OnLoadMarkerCallback callback);

    interface OnDialogDataPrepared {
        void onSuccess(String title, String message, Marker marker);
    }

    interface OnLoadMarkerCallback {
        void onSuccess(String title, LatLng position, float zoom);

        void onNotFound();
    }

    interface OnMarkerSavedCallback {
        void onSuccess(String message);
    }

    interface OnUriPreparedCallback {
        void onSuccess(Uri uri);
    }

    interface OnFullNamePreparedCallback {
        void onSuccess(String fullLocationName, LatLng latLng, float zoom);
    }

    interface OnLocationSearchResultCallback {
        void onLocationFound(LatLng latLng, float zoom);

        void onNotFound(String message);

        void onPermissionRequired(String permission, int requestCode);
    }
}
