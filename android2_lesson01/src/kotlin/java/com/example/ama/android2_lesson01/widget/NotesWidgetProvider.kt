package com.example.ama.android2_lesson01.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.ui.MainActivity

class NotesWidgetProvider : AppWidgetProvider() {

    companion object {
        fun updateWidget(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = appWidgetManager
                    .getAppWidgetIds(ComponentName(context, NotesWidgetProvider::class.java))
            for (id in widgetIds) {
                val remoteViews = RemoteViews(context.packageName, R.layout.widget_notes)
                remoteViews.setRemoteAdapter(R.id.lv_notelist, Intent(context, ListNoteService::class.java))

                val pendingIntent = PendingIntent
                        .getActivity(context, 0, Intent(context, MainActivity::class.java),
                                PendingIntent.FLAG_UPDATE_CURRENT)
                remoteViews.setPendingIntentTemplate(R.id.lv_notelist, pendingIntent)

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