package com.example.ama.android2_lesson01.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.ama.android2_lesson01.R;

public class NotesWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        updateWidgets(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateWidgets(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] IDs = appWidgetManager.getAppWidgetIds(new ComponentName(context, NotesWidgetProvider.class));
        for (int ID : IDs) {
            Intent intent = new Intent(context, StackNoteService.class);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_notes);
            remoteViews.setRemoteAdapter(R.id.lv_notelist, intent);
            appWidgetManager.notifyAppWidgetViewDataChanged(ID, R.id.lv_notelist);
            appWidgetManager.updateAppWidget(ID, remoteViews);
        }
    }
}
