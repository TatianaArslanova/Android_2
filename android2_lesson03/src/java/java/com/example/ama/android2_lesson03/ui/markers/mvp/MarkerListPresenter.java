package com.example.ama.android2_lesson03.ui.markers.mvp;

import com.example.ama.android2_lesson03.repo.MarkerListQueryManager;
import com.example.ama.android2_lesson03.repo.base.MarkerListManager;
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker;
import com.example.ama.android2_lesson03.ui.base.BasePresenter;
import com.example.ama.android2_lesson03.ui.markers.base.MarkerPresenter;
import com.example.ama.android2_lesson03.ui.markers.base.MarkerView;

/**
 * Presenter implementation for {@link MarkerView}
 *
 * @param <T> view for work with
 */
public class MarkerListPresenter<T extends MarkerView> extends BasePresenter<T> implements MarkerPresenter<T> {

    private MarkerListManager markerManager;

    public MarkerListPresenter() {
        markerManager = new MarkerListQueryManager();
    }

    @Override
    public void getMarkerList() {
        markerManager.getAllMarkers(markers -> view.showMarkerList(markers));
    }

    @Override
    public void tuneMapCurrentMarker(SimpleMarker marker) {
        markerManager.saveCurrentMarker(marker);
    }

    @Override
    public void editMarkerName(SimpleMarker marker, String newName) {
        markerManager.updateMarker(marker, newName, markers -> view.showMarkerList(markers));
    }

    @Override
    public void deleteMarker(SimpleMarker marker) {
        markerManager.deleteMarker(marker, markers -> view.showMarkerList(markers));
    }

    @Override
    public void onUpdateMarker(SimpleMarker marker) {
        markerManager.prepareEditMarkerNameDialog(marker,
                (dialogTitle, dialogMessage, targetMarker) ->
                        view.showEditDialog(dialogTitle, dialogMessage, targetMarker));
    }
}
