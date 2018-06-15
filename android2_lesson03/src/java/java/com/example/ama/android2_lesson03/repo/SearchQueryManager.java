package com.example.ama.android2_lesson03.repo;

import android.location.Address;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.base.MarkerListManager;
import com.example.ama.android2_lesson03.repo.base.SearchManager;
import com.example.ama.android2_lesson03.repo.data.LocationManagerAndroid;
import com.example.ama.android2_lesson03.repo.data.PreferencesMarkerManager;
import com.example.ama.android2_lesson03.repo.data.UriManager;
import com.example.ama.android2_lesson03.repo.data.base.LocManager;
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager;
import com.example.ama.android2_lesson03.repo.model.SimpleMarker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class SearchQueryManager implements SearchManager, MarkerListManager {
    private LocManager locManager;
    private UriManager uriManager;
    private MarkerManager markerManager;

    private SimpleMarker savedMarker;

    public static final float DEFAULT_ZOOM = 15f;

    public SearchQueryManager() {
        locManager = new LocationManagerAndroid();
        uriManager = new UriManager();
        markerManager = new PreferencesMarkerManager();
    }

    @Override
    public void getPreparedUri(boolean isMarkerOnTheMap, LatLng cameraPosition, float zoom, OnUriPreparedCallback callback) {
        if (!isMarkerOnTheMap) {
            uriManager.prepareUriByCameraPosition(cameraPosition, zoom);
        }
        callback.onSuccess(uriManager.getPreparedUri());
    }

    @Override
    public void getFullLocationName(String query, OnFullNamePreparedCallback callback) {
        Address address = locManager.findAddressByQuery(query);
        if (address != null) {
            uriManager.prepareUriByQuery(query);
            callback.onSuccess(
                    buildFullName(address),
                    new LatLng(address.getLatitude(), address.getLongitude()),
                    DEFAULT_ZOOM);
        }
    }

    @Override
    public void getFullLocationName(LatLng latLng, OnFullNamePreparedCallback callback) {
        Address address = locManager.findAddressByCoords(latLng);
        if (address != null) {
            String fullName = buildFullName(address);
            uriManager.prepareUriByMarkerLocation(latLng, fullName);
            callback.onSuccess(
                    fullName,
                    latLng,
                    DEFAULT_ZOOM
            );
        }
    }

    @Override
    public void getMyLocation(OnLocationSearchResultCallback callback) {
        locManager.findMyLocation(callback);
    }

    @Override
    public void prepareSaveMarkerDialog(Marker marker, OnDialogDataPrepared callback) {
        callback.onSuccess(
                PocketMap.getInstance().getString(R.string.save_marker_dialog_title),
                PocketMap.getInstance().getString(R.string.save_marker_dialog_message),
                marker
        );
    }

    @Override
    public void saveMarker(Marker marker, OnMarkerSavedCallback callback) {
        markerManager.addMarker(SimpleMarker.getFromMarker(marker));
        callback.onSuccess(PocketMap.getInstance().getString(R.string.marker_saved_message));
    }

    @Override
    public void getAllMarkers(OnGetMarkersCallback callback) {
        callback.onSuccess(markerManager.getAllMarkers());
    }

    @Override
    public void updateMarker(SimpleMarker marker, String newName, OnGetMarkersCallback callback) {
        markerManager.updateMarker(marker, newName);
        getAllMarkers(callback);
    }

    @Override
    public void deleteMarker(SimpleMarker marker, OnGetMarkersCallback callback) {
        markerManager.deleteMarker(marker);
        getAllMarkers(callback);
    }

    @Override
    public void sendMarker(SimpleMarker marker) {
        savedMarker = marker;
    }

    @Override
    public void getCurrentMarker(OnLoadMarkerCallback callback) {
        if (savedMarker != null) {
            callback.onSuccess(savedMarker);
            savedMarker = null;
        } else {
            callback.onNotFound();
        }
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
}
