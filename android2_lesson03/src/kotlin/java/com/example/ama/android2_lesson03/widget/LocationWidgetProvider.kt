package com.example.ama.android2_lesson03.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.widget.model.WidgetModel

class LocationWidgetProvider : AppWidgetProvider() {

    companion object {
        fun updateWidgets(context: Context, appWidgetManager: AppWidgetManager,
                          appWidgetIds: IntArray, model: WidgetModel) {
            for (id in appWidgetIds) {
                val remoteViews = RemoteViews(context.packageName, R.layout.widget_current_location)
                remoteViews.setTextViewText(R.id.tv_latlng, model.coordinates)
                remoteViews.setTextViewText(R.id.tv_location_address, model.address)
                remoteViews.setOnClickPendingIntent(R.id.ib_update_location, model.pendingIntent)
                appWidgetManager.updateAppWidget(id, remoteViews)
            }
        }
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