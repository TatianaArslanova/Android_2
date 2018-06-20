package com.example.ama.android2_lesson03.repo;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.base.MarkerListManager;
import com.example.ama.android2_lesson03.repo.data.base.MarkerManager;
import com.example.ama.android2_lesson03.repo.data.markers.PreferencesMarkerManager;
import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker;
import com.example.ama.android2_lesson03.repo.data.state.SearchOnTheMapStateSaver;

public class MarkerListQueryManager implements MarkerListManager {

    private MarkerManager markerManager;

    public MarkerListQueryManager() {
        markerManager = new PreferencesMarkerManager();
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
    public void saveCurrentMarker(SimpleMarker marker) {
        SearchOnTheMapStateSaver.saveCurrentMarker(marker);
    }

    @Override
    public void prepareEditMarkerNameDialog(SimpleMarker marker, OnDialogPreparedCallback callback) {
        callback.onSuccess(
                PocketMap.getInstance().getString(R.string.edit_dialog_title),
                PocketMap.getInstance().getString(R.string.edit_dialog_message_marker_name),
                marker
        );
    }

}
