package com.example.ama.android2_lesson01.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.ui.MainActivity;

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
            Intent intent = new Intent(context, ListNoteService.class);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_notes);
            remoteViews.setRemoteAdapter(R.id.lv_notelist, intent);

            Intent activityIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent
                    .getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.lv_notelist, pendingIntent);

            appWidgetManager.notifyAppWidgetViewDataChanged(ID, R.id.lv_notelist);
            appWidgetManager.updateAppWidget(ID, remoteViews);
        }
    }
}
