package com.example.ama.android2_lesson03.ui.search.mvp;

import android.net.Uri;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.repo.base.SearchManager;
import com.example.ama.android2_lesson03.ui.base.BasePresenter;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class SearchOnTheMapPresenter<T extends SearchOnTheMapView>
        extends BasePresenter<T>
        implements SearchPresenter<T> {

    private SearchManager queryManager;

    public SearchOnTheMapPresenter() {
        queryManager = PocketMap.getQueryManager();
    }

    @Override
    public void findAddressByQuery(String query) {
        queryManager.getFullLocationName(query, new SearchManager.OnFullNamePreparedCallback() {
            @Override
            public void onSuccess(String fullLocationName, LatLng latLng, float zoom) {
                view.showOnInnerMap(fullLocationName, latLng, zoom);
            }
        });
    }

    @Override
    public void findAddressByLatLng(LatLng latLng) {
        queryManager.getFullLocationName(latLng, new SearchManager.OnFullNamePreparedCallback() {
            @Override
            public void onSuccess(String fullLocationName, LatLng latLng, float zoom) {
                view.showOnInnerMap(fullLocationName, latLng, zoom);
            }
        });
    }

    @Override
    public void sendQueryToGMapsApp(boolean isMarkerOnTheMap, LatLng cameraPosition, float zoom) {
        queryManager.getPreparedUri(isMarkerOnTheMap, cameraPosition, zoom, new SearchManager.OnUriPreparedCallback() {
            @Override
            public void onSuccess(Uri uri) {
                view.showOnGMapsApp(uri);
            }
        });
    }

    @Override
    public void findMyLocation() {
        queryManager.getMyLocation(new SearchManager.OnLocationSearchResultCallback() {
            @Override
            public void onLocationFound(LatLng latLng, float zoom) {
                view.zoomToLocation(latLng, zoom);
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
    public void saveMarker(Marker marker) {
        queryManager.saveMarker(marker, new SearchManager.OnMarkerSavedCallback() {
            @Override
            public void onSuccess(String message) {
                view.showMessage(message);
            }
        });
    }

    @Override
    public void onSaveMarkerClick(Marker marker) {
        queryManager.prepareSaveMarkerDialog(marker, new SearchManager.OnDialogDataPrepared() {
            @Override
            public void onSuccess(String title, String message, Marker marker) {
                view.showDialog(title, message, marker);
            }
        });
    }

    @Override
    public void getCurrentMarker() {
        queryManager.getCurrentMarker(new SearchManager.OnLoadMarkerCallback() {
            @Override
            public void onSuccess(String title, LatLng position, float zoom) {
                view.showOnInnerMap(title, position, zoom);
            }

            @Override
            public void onNotFound() {
                findMyLocation();
            }
        });
    }
}
