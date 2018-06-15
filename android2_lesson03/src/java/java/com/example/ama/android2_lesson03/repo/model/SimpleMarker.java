package com.example.ama.android2_lesson03.repo.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class SimpleMarker {
    private LatLng position;
    private String title;

    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SimpleMarker(String title, LatLng position) {
        this.title = title;
        this.position = position;
    }

    public static SimpleMarker getFromMarker(Marker marker) {
        return new SimpleMarker(marker.getTitle(), marker.getPosition());
    }

    @Override
    public String toString() {
        return title;
    }
}
