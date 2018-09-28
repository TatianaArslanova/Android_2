package com.example.ama.android2_lesson03.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.widget.model.WidgetModel;
import com.example.ama.android2_lesson03.widget.utils.WidgetConstants;

public class LocationWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        startLocationService(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                                     WidgetModel model) {
        for (int id : appWidgetIds) {
            RemoteViews remoteViews;
            int theme = getThemeType(context, id);
            if (theme == WidgetConstants.THEME_DARK) {
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_location_dark);
            } else {
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_location_light);
            }
            remoteViews.setTextViewText(R.id.tv_location_address, model.getAddress());
            remoteViews.setTextViewText(R.id.tv_latlng, model.getCoordinates());
            remoteViews.setOnClickPendingIntent(R.id.ib_update_location, model.getPendingIntent());
            appWidgetManager.updateAppWidget(id, remoteViews);
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.getSharedPreferences(WidgetConstants.WIDGET_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit().clear().apply();
    }

    private static int getThemeType(Context context, int widgetId) {
        return context.getSharedPreferences(WidgetConstants.WIDGET_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getInt(String.valueOf(widgetId), WidgetConstants.THEME_LIGHT);
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
