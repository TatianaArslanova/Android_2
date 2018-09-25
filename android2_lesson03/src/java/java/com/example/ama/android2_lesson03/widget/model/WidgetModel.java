package com.example.ama.android2_lesson03.widget.model;

import android.app.PendingIntent;

public class WidgetModel {
    private final String coordinates;
    private final String address;
    private final PendingIntent pendingIntent;

    WidgetModel(String coordinates, String address, PendingIntent pendingIntent) {
        this.coordinates = coordinates;
        this.address = address;
        this.pendingIntent = pendingIntent;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getAddress() {
        return address;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }
}
