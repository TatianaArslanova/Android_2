package com.example.ama.android2_lesson03.repo.data.base;

import com.example.ama.android2_lesson03.repo.model.SimpleMarker;

import java.util.ArrayList;

public interface MarkerManager {
    ArrayList<SimpleMarker> getAllMarkers();

    void addMarker(SimpleMarker marker);

    void updateMarker(SimpleMarker marker, String newName);

    void deleteMarker(SimpleMarker marker);
}
