package com.example.ama.android2_lesson03.ui.search.mvp;

import android.net.Uri;

import com.example.ama.android2_lesson03.base.BasePresenter;
import com.example.ama.android2_lesson03.model.base.QueryManager;
import com.example.ama.android2_lesson03.ui.search.base.SearchOnTheMapView;
import com.example.ama.android2_lesson03.ui.search.base.SearchPresenter;
import com.google.android.gms.maps.model.LatLng;

public class SearchOnTheMapPresenter<T extends SearchOnTheMapView>
        extends BasePresenter<T>
        implements SearchPresenter<T> {

    @Override
    public void findAddress(String query) {
        queryManager.getFullLocationName(query, new QueryManager.OnFullNamePreparedCallback() {
            @Override
            public void onSuccess(String fullLocationName, LatLng latLng) {
                view.showOnInnerMap(fullLocationName, latLng);
            }
        });
    }

    @Override
    public void sendUserInput(String query) {
        queryManager.prepareGMapUri(query, new QueryManager.OnUriPreparedCallback() {
            @Override
            public void onSuccess(Uri uri) {
                view.showOnGMapsApp(uri);
            }
        });
    }
}
