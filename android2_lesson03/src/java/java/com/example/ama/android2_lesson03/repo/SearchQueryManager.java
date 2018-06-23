package com.example.ama.android2_lesson03.repo;

import android.location.Address;
import android.location.Location;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.base.SearchManager;
import com.example.ama.android2_lesson03.repo.data.base.LocManager;
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager;
import com.example.ama.android2_lesson03.repo.data.location.LocationManagerAndroid;
import com.example.ama.android2_lesson03.repo.data.location.LocationManagerGoogle;
import com.example.ama.android2_lesson03.repo.data.markers.PreferencesMarkerManager;
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker;
import com.example.ama.android2_lesson03.repo.data.state.SearchOnTheMapStateSaver;
import com.example.ama.android2_lesson03.repo.data.state.UriManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Class for execute presenter's queries
 */
public class SearchQueryManager implements SearchManager {
    private LocManager locManager;
    private UriManager uriManager;
    private MarkerManager markerManager;

    private static final float DEFAULT_ZOOM = 15f;

    public SearchQueryManager() {
        locManager = new LocationManagerAndroid();
        uriManager = new UriManager();
        markerManager = new PreferencesMarkerManager();
    }

    @Override
    public void prepareUriForGMaps(Marker marker, LatLng cameraPosition, float zoom, OnUriPreparedCallback callback) {
        if (marker != null) {
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
    public void getMyLocation(OnLatLngSearchResultCallback callback) {
        locManager.findMyLocation(getLocationCallback(callback));
    }

    @Override
    public void prepareSaveMarkerDialog(Marker marker, OnDialogDataPrepared callback) {
        SimpleMarker savingMarker = SimpleMarker.getFromMarker(marker);
        if (savingMarker != null) {
            callback.onSuccess(
                    PocketMap.getInstance().getString(R.string.save_marker_dialog_title),
                    markerManager.isMarkerExists(savingMarker) ?
                            PocketMap.getInstance().getString(R.string.message_marker_already_exists) :
                            PocketMap.getInstance().getString(R.string.save_marker_dialog_message),
                    marker
            );
        }
    }

    @Override
    public void saveMarkerToList(Marker marker, String customName, OnMarkerSavedCallback callback) {
        SimpleMarker savingMarker = SimpleMarker.getFromMarker(marker);
        if (savingMarker != null) {
            if (markerManager.isMarkerExists(savingMarker)) {
                markerManager.updateMarker(savingMarker, customName);
            } else {
                markerManager.addMarker(savingMarker);
            }
            callback.onSuccess(PocketMap.getInstance().getString(R.string.marker_saved_message));
        }
    }

    @Override
    public void saveState(Marker currentMarker) {
        SearchOnTheMapStateSaver.saveCurrentMarker(SimpleMarker.getFromMarker(currentMarker));
    }


    @Override
    public void loadSavedState(OnMarkerPreparedCallback callback) {
        SimpleMarker currentMarker = SearchOnTheMapStateSaver.loadCurrentMarker();
        if (currentMarker != null) {
            uriManager.prepareUriByMarkerLocation(currentMarker.getPosition(), currentMarker.getAddress());
            callback.onSuccess(
                    currentMarker.getTitle(),
                    currentMarker.getAddress(),
                    currentMarker.getPosition(),
                    DEFAULT_ZOOM);
        }
    }

    @Override
    public void subscribeOnLocationUpdates(OnLatLngSearchResultCallback callback) {
        locManager.subscribeOnLocationChanges(getLocationCallback(callback));
    }

    @Override
    public void unsubscribeOfLocationUpdates() {
        locManager.unsubscribeOfLocationChanges();
    }

    private LocManager.OnLocationSearchResultCallback getLocationCallback(final SearchManager.OnLatLngSearchResultCallback callback) {
        return new LocManager.OnLocationSearchResultCallback() {
            @Override
            public void onLocationFound(Location location) {
                callback.onLocationFound(new LatLng(
                                location.getLatitude(),
                                location.getLongitude()),
                        DEFAULT_ZOOM);
            }

            @Override
            public void onError(String message) {
                callback.onNotFound(message);
            }

            @Override
            public void onPermissionRequired(String permission, int requestCode) {
                callback.onPermissionRequired(permission, requestCode);
            }
        };
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
