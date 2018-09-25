package com.example.ama.android2_lesson03.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.widget.model.WidgetModel;

public class LocationWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        startLocationService(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                                     WidgetModel model) {
        for (int id : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_current_location);
            remoteViews.setTextViewText(R.id.tv_location_address, model.getAddress());
            remoteViews.setTextViewText(R.id.tv_latlng, model.getCoordinates());
            remoteViews.setOnClickPendingIntent(R.id.ib_update_location, model.getPendingIntent());
            appWidgetManager.updateAppWidget(id, remoteViews);
        }
    }

    private void startLocationService(Context context) {
        Intent intent = new Intent(context, LocationWidgetService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }
}
