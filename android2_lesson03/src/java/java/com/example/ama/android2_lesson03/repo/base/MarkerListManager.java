package com.example.ama.android2_lesson03.repo.base;

import com.example.ama.android2_lesson03.repo.model.SimpleMarker;

import java.util.ArrayList;

public interface MarkerListManager {
    void getAllMarkers(OnGetMarkersCallback callback);

    void updateMarker(SimpleMarker marker, String newName, OnGetMarkersCallback callback);

    void deleteMarker(SimpleMarker marker, OnGetMarkersCallback callback);

    void sendMarker(SimpleMarker marker);

    interface OnGetMarkersCallback {
        void onSuccess(ArrayList<SimpleMarker> markers);
    }
}
