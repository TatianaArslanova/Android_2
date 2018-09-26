package com.example.ama.android2_lesson01.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.ama.android2_lesson01.R

class NotesWidgetProvider : AppWidgetProvider() {

    companion object {
        fun updateWidget(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = appWidgetManager
                    .getAppWidgetIds(ComponentName(context, NotesWidgetProvider::class.java))
            for (id in widgetIds) {
                val remoteViews = RemoteViews(context.packageName, R.layout.widget_notes)
                remoteViews.setRemoteAdapter(R.id.lv_notelist, Intent(context, ListNoteService::class.java))
                appWidgetManager.notifyAppWidgetViewDataChanged(id, R.id.lv_notelist)
                appWidgetManager.updateAppWidget(id, remoteViews)
            }
        }
    }

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        if (context != null) {
            updateWidget(context)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }
}