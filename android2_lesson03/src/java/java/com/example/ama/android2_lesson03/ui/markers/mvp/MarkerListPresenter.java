package com.example.ama.android2_lesson03.ui.markers.mvp;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.repo.base.MarkerListManager;
import com.example.ama.android2_lesson03.repo.model.SimpleMarker;
import com.example.ama.android2_lesson03.ui.base.BasePresenter;
import com.example.ama.android2_lesson03.ui.markers.base.MarkerPresenter;
import com.example.ama.android2_lesson03.ui.markers.base.MarkerView;

import java.util.ArrayList;

/**
 * Presenter implementation for {@link MarkerView}
 *
 * @param <T> view for work with
 */
public class MarkerListPresenter<T extends MarkerView> extends BasePresenter<T> implements MarkerPresenter<T> {

    private MarkerListManager markerManager;

    public MarkerListPresenter() {
        if (PocketMap.getQueryManager() instanceof MarkerListManager) {
            markerManager = (MarkerListManager) PocketMap.getQueryManager();
        }
    }

    @Override
    public void getMarkerList() {
        markerManager.getAllMarkers(new MarkerListManager.OnGetMarkersCallback() {
            @Override
            public void onSuccess(ArrayList<SimpleMarker> markers) {
                view.showMarkerList(markers);
            }
        });
    }

    @Override
    public void sendMarker(SimpleMarker marker) {
        markerManager.saveCurrentMarker(marker);
    }

    @Override
    public void editMarkerName(SimpleMarker marker, String newName) {
        markerManager.updateMarker(marker, newName, new MarkerListManager.OnGetMarkersCallback() {
            @Override
            public void onSuccess(ArrayList<SimpleMarker> markers) {
                view.showMarkerList(markers);
            }
        });
    }

    @Override
    public void deleteMarker(SimpleMarker marker) {
        markerManager.deleteMarker(marker, new MarkerListManager.OnGetMarkersCallback() {
            @Override
            public void onSuccess(ArrayList<SimpleMarker> markers) {
                view.showMarkerList(markers);
            }
        });
    }
}
