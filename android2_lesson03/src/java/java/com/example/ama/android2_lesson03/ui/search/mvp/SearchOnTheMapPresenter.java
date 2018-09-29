package com.example.ama.android2_lesson03.ui.search.mvp;

import android.util.Log;

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
        queryManager.loadSavedState((title, address, position, zoom) -> {
            if (view != null) {
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
        queryManager.getFullLocationName(query, (fullLocationName, latLng, zoom) -> {
            if (view != null) {
                view.moveMapCamera(latLng, zoom, true);
                view.showOnInnerMap(null, fullLocationName, latLng);
            }
        });
    }

    @Override
    public void findAddressByLatLng(LatLng latLng) {
        queryManager.getFullLocationName(latLng, (fullLocationName, latLng1, zoom) -> {
            if (view != null) {
                view.moveMapCamera(latLng1, zoom, true);
                view.showOnInnerMap(null, fullLocationName, latLng1);
            }
        });
    }

    @Override
    public void sendQueryToGMapsApp(Marker currentMarker, LatLng cameraPosition, float zoom) {
        queryManager.prepareUriForGMaps(currentMarker, cameraPosition, zoom, uri -> {
            if (view != null) {
                view.showOnGMapsApp(uri);
            }
        });
    }

    @Override
    public void findMyLocation() {
        queryManager.getMyLocation(new SearchManager.OnLatLngSearchResultCallback() {
            @Override
            public void onLocationFound(LatLng latLng, float zoom) {
                if (view != null) {
                    view.moveMapCamera(latLng, zoom, true);
                }
            }

            @Override
            public void onNotFound(String message) {
                if (view != null) {
                    view.showMessage(message);
                }
            }
        });
    }

    @Override
    public void onMarkerClick(Marker marker) {
        queryManager.prepareSaveMarkerDialog(marker, (title, message, marker1) -> {
            if (view != null) {
                view.showDialog(title, message, marker1);
            }
        });
    }

    @Override
    public void saveMarker(Marker currentMarker, String customName) {
        queryManager.saveMarkerToList(currentMarker, customName, message -> {
            if (view != null) {
                view.showMessage(message);
            }
        });
    }

    @Override
    public void subscribeOnLocationUpdates() {
        queryManager.subscribeOnLocationUpdates(new SearchManager.OnLatLngSearchResultCallback() {
            @Override
            public void onLocationFound(LatLng latLng, float zoom) {
                Log.d("onLocationFound", "location updated " + latLng.toString());
            }

            @Override
            public void onNotFound(String message) {
                if (view != null) {
                    view.showMessage(message);
                }
            }
        });
    }

    @Override
    public void unsubscribeOfLocationUpdates() {
        queryManager.unsubscribeOfLocationUpdates();
    }
}
