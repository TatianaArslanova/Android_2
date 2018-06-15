package com.example.ama.android2_lesson03.ui.markers.base;

import com.example.ama.android2_lesson03.repo.model.SimpleMarker;
import com.example.ama.android2_lesson03.ui.base.PocketMapView;

import java.util.ArrayList;

public interface MarkerView extends PocketMapView {
    void showMarkerList(ArrayList<SimpleMarker> markers);
}
