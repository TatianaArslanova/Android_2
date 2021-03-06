package com.example.ama.android2_lesson03.repo.base;

import com.example.ama.android2_lesson03.repo.data.model.SimpleMarker;

import java.util.ArrayList;

/**
 * Interface describes basic methods for managing markers by presenter's queries
 */
public interface MarkerListManager {
    void getAllMarkers(OnGetMarkersCallback callback);

    void updateMarker(SimpleMarker marker, String newName, OnGetMarkersCallback callback);

    void deleteMarker(SimpleMarker marker, OnGetMarkersCallback callback);

    void saveCurrentMarker(SimpleMarker marker);

    void prepareEditMarkerNameDialog(SimpleMarker marker, OnDialogPreparedCallback callback);

    interface OnGetMarkersCallback {
        void onSuccess(ArrayList<SimpleMarker> markers);
    }

    interface OnDialogPreparedCallback {
        void onSuccess(String dialogTitle, String dialogMessage, SimpleMarker targetMarker);
    }
}
