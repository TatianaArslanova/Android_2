package com.example.ama.android2_lesson03.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.widget.model.WidgetModel
import com.example.ama.android2_lesson03.widget.utils.THEME_DARK
import com.example.ama.android2_lesson03.widget.utils.THEME_LIGHT
import com.example.ama.android2_lesson03.widget.utils.WIDGET_PREFERENCES_NAME

class LocationWidgetProvider : AppWidgetProvider() {

    companion object {
        fun updateWidgets(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray, model: WidgetModel) {
            for (id in appWidgetIds) {
                val remoteViews = when (getTheme(context, id)) {
                    THEME_DARK -> RemoteViews(context.packageName, R.layout.widget_location_dark)
                    else -> RemoteViews(context.packageName, R.layout.widget_location_light)
                }
                remoteViews.setTextViewText(R.id.tv_latlng, model.coordinates)
                remoteViews.setTextViewText(R.id.tv_location_address, model.address)
                remoteViews.setOnClickPendingIntent(R.id.ib_update_location, model.pendingIntent)
                appWidgetManager.updateAppWidget(id, remoteViews)
            }
        }

        private fun getTheme(context: Context, widgetId: Int) =
                context.getSharedPreferences(WIDGET_PREFERENCES_NAME, Context.MODE_PRIVATE)
                        .getInt(widgetId.toString(), THEME_LIGHT)
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        if (context != null) {
            startLocationService(context)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    private fun startLocationService(context: Context) {
        val intent = Intent(context, LocationWidgetService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

}