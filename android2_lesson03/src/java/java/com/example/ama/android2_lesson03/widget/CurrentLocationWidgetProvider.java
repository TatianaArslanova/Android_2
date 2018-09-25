package com.example.ama.android2_lesson03.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.ama.android2_lesson03.R;
import com.google.android.gms.maps.model.LatLng;

public class CurrentLocationWidgetProvider extends AppWidgetProvider {
    private static final int REQUEST_CODE = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        startLocationService(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                                     LatLng latLng, String address) {
        for (int id : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_current_location);
            remoteViews.setTextViewText(R.id.tv_location_address, address);
            remoteViews.setTextViewText(R.id.tv_latlng, String.format(context.getResources()
                    .getString(R.string.widget_latlng_format), latLng.latitude, latLng.longitude));
            remoteViews.setOnClickPendingIntent(R.id.ib_update_location, buildPendingIntent(context));
            appWidgetManager.updateAppWidget(id, remoteViews);
        }
    }

    private static void startLocationService(Context context) {
        Intent intent = new Intent(context, CurrentLocationForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    private static PendingIntent buildPendingIntent(Context context) {
        Intent intent = new Intent(context, CurrentLocationForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return PendingIntent.getForegroundService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            return PendingIntent.getService(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }
}
