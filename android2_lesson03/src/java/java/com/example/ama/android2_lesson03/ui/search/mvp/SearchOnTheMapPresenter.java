package com.example.ama.android2_lesson03.ui.search.mvp;

import android.net.Uri;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.base.BasePresenter;
import com.example.ama.android2_lesson03.model.base.QueryManager;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.google.android.gms.maps.model.LatLng;

public class SearchOnTheMapPresenter<T extends SearchOnTheMapView>
        extends BasePresenter<T>
        implements SearchPresenter<T> {

    @Override
    public void findAddressByQuery(String query) {
        queryManager.getFullLocationName(query, new QueryManager.OnFullNamePreparedCallback() {
            @Override
            public void onSuccess(String fullLocationName, LatLng latLng, float zoom) {
                view.showOnInnerMap(fullLocationName, latLng, zoom);
            }
        });
    }

    @Override
    public void findAddressByLatLng(LatLng latLng) {
        queryManager.getFullLocationName(latLng, new QueryManager.OnFullNamePreparedCallback() {
            @Override
            public void onSuccess(String fullLocationName, LatLng latLng, float zoom) {
                view.showOnInnerMap(fullLocationName, latLng, zoom);
            }
        });
    }

    @Override
    public void sendQueryToGMapsApp(String query) {
        queryManager.prepareGMapUri(query, new QueryManager.OnUriPreparedCallback() {
            @Override
            public void onSuccess(Uri uri) {
                view.showOnGMapsApp(uri);
            }
        });
    }

    @Override
    public void findMyLocation() {
        queryManager.getMyLocation(new QueryManager.OnLocationSearchResultCallback() {
            @Override
            public void onLocationFound(LatLng latLng) {
                view.zoomToLocation(latLng);
            }

            @Override
            public void onNotFound() {
                view.showErrorMessage(PocketMap.getInstance().getString(R.string.message_location_not_found));
            }

            @Override
            public void onPermissionRequired(String permission, int requestCode) {
                view.requestPermission(permission, requestCode);
            }
        });
    }

}
