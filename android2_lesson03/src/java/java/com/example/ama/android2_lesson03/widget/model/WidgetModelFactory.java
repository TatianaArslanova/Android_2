package com.example.ama.android2_lesson03.widget.model;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.utils.ResourceUtils;
import com.example.ama.android2_lesson03.widget.LocationWidgetService;

public class WidgetModelFactory {
    private final static int REQUEST_CODE = 1;

    private Context context;

    public WidgetModelFactory(Context context) {
        this.context = context;
    }

    public WidgetModel fullModel(Location location, String address) {
        return new WidgetModel(
                ResourceUtils.formatLocationCoordinates(location),
                address,
                buildPendingIntent());
    }

    public WidgetModel permissionRequiredModel() {
        return new WidgetModel(
                ResourceUtils.getStringResource(R.string.message_location_not_found),
                ResourceUtils.getStringResource(R.string.message_permission_required),
                buildPendingIntent());
    }

    private PendingIntent buildPendingIntent() {
        Intent intent = new Intent(context, LocationWidgetService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return PendingIntent.getForegroundService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            return PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }
}
