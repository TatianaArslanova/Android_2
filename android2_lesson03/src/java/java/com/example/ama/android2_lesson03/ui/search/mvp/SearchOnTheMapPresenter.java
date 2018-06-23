package com.example.ama.android2_lesson03.ui.search.mvp;

import android.net.Uri;

import com.example.ama.android2_lesson03.repo.SearchQueryManager;
import com.example.ama.android2_lesson03.repo.base.SearchManager;
import com.example.ama.android2_lesson03.ui.base.BasePresenter;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Presenter implementation for {@link SearchOnTheMapView}
 *
 * @param <T> view for work with
 */
public class SearchOnTheMapPresenter<T extends SearchOnTheMapView>
        extends BasePresenter<T>
        implements SearchPresenter<T> {

    private SearchManager queryManager;

    public SearchOnTheMapPresenter() {
        queryManager = new SearchQueryManager();
    }

    @Override
    public void loadSavedState() {
        queryManager.loadSavedState(new SearchManager.OnMarkerPreparedCallback() {
            @Override
            public void onSuccess(String title, String address, LatLng position, float zoom) {
                view.moveMapCamera(position, zoom, false);
                view.showOnInnerMap(title, address, position);
            }
        });
    }

    @Override
    public void saveState(Marker currentMarker) {
        queryManager.saveState(currentMarker);
    }

    @Override
    public void findAddressByQuery(String query) {
        queryManager.getFullLocationName(query, new SearchManager.OnFullNamePreparedCallback() {
            @Override
            public void onSuccess(String fullLocationName, LatLng latLng, float zoom) {
                view.moveMapCamera(latLng, zoom, true);
                view.showOnInnerMap(null, fullLocationName, latLng);
            }
        });
    }

    @Override
    public void findAddressByLatLng(LatLng latLng) {
        queryManager.getFullLocationName(latLng, new SearchManager.OnFullNamePreparedCallback() {
            @Override
            public void onSuccess(String fullLocationName, LatLng latLng, float zoom) {
                view.moveMapCamera(latLng, zoom, true);
                view.showOnInnerMap(null, fullLocationName, latLng);
            }
        });
    }

    @Override
    public void sendQueryToGMapsApp(Marker currentMarker, LatLng cameraPosition, float zoom) {
        queryManager.prepareUriForGMaps(currentMarker, cameraPosition, zoom, new SearchManager.OnUriPreparedCallback() {
            @Override
            public void onSuccess(Uri uri) {
                view.showOnGMapsApp(uri);
            }
        });
    }

    @Override
    public void findMyLocation() {
        queryManager.getMyLocation(new SearchManager.OnLatLngSearchResultCallback() {
            @Override
            public void onLocationFound(LatLng latLng, float zoom) {
                view.moveMapCamera(latLng, zoom, true);
            }

            @Override
            public void onNotFound(String message) {
                view.showMessage(message);
            }

            @Override
            public void onPermissionRequired(String permission, int requestCode) {
                view.requestPermission(permission, requestCode);
            }
        });
    }

    @Override
    public void onMarkerClick(Marker marker) {
        queryManager.prepareSaveMarkerDialog(marker, new SearchManager.OnDialogDataPrepared() {
            @Override
            public void onSuccess(String title, String message, Marker marker) {
                view.showDialog(title, message, marker);
            }
        });
    }

    @Override
    public void saveMarker(Marker currentMarker, String customName) {
        queryManager.saveMarkerToList(currentMarker, customName, new SearchManager.OnMarkerSavedCallback() {
            @Override
            public void onSuccess(String message) {
                view.showMessage(message);
            }
        });
    }

    @Override
    public void subscribeOnLocationUpdates() {
        queryManager.subscribeOnLocationUpdates(new SearchManager.OnLatLngSearchResultCallback() {
            @Override
            public void onLocationFound(LatLng latLng, float zoom) {

            }

            @Override
            public void onNotFound(String message) {
                view.showMessage(message);
            }

            @Override
            public void onPermissionRequired(String permission, int requestCode) {
                view.requestPermission(permission, requestCode);
            }
        });
    }

    @Override
    public void unsubscribeOfLocationUpdates() {
        queryManager.unsubscribeOfLocationUpdates();
    }
}
