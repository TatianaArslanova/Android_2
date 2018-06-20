package com.example.ama.android2_lesson03.repo.base;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Interface describes methods for search manager queries
 */
public interface SearchManager {
    void prepareUriForGMaps(Marker currentMarker, LatLng cameraPosition, float zoom, OnUriPreparedCallback callback);

    void getFullLocationName(String query, OnFullNamePreparedCallback callback);

    void getFullLocationName(LatLng latLng, OnFullNamePreparedCallback callback);

    void getMyLocation(OnLocationSearchResultCallback callback);

    void prepareSaveMarkerDialog(Marker marker, OnDialogDataPrepared callback);

    void saveMarkerToList(Marker marker, String customName, OnMarkerSavedCallback callback);

    void saveState(Marker currentMarker);

    void loadSavedState(OnMarkerPreparedCallback callback);

    interface OnDialogDataPrepared {
        void onSuccess(String title, String message, Marker marker);
    }

    interface OnMarkerPreparedCallback {
        void onSuccess(String title, String address, LatLng position, float zoom);
    }

    interface OnMarkerSavedCallback {
        void onSuccess(String message);
    }

    interface OnUriPreparedCallback {
        void onSuccess(Uri uri);
    }

    interface OnFullNamePreparedCallback {
        void onSuccess(String address, LatLng latLng, float zoom);
    }

    interface OnLocationSearchResultCallback {
        void onLocationFound(LatLng latLng, float zoom);

        void onNotFound(String message);

        void onPermissionRequired(String permission, int requestCode);
    }
}
