package com.example.ama.android2_lesson03.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.widget.utils.WidgetConstants;

public class WidgetConfigActivity extends AppCompatActivity {
    private int widgetId;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config_activity);
        initWidgetId();
        intent = new Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_CANCELED, intent);
        setListeners();
    }

    private void initWidgetId() {
        if (getIntent().getExtras() != null) {
            widgetId = getIntent().getExtras()
                    .getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    private void setListeners() {
        findViewById(R.id.ib_dark_theme).setOnClickListener(
                view -> setWidgetTheme(WidgetConstants.THEME_DARK));
        findViewById(R.id.iv_light_theme).setOnClickListener(
                view -> setWidgetTheme(WidgetConstants.THEME_LIGHT));
    }

    private void setWidgetTheme(int themeId) {
        getSharedPreferences(WidgetConstants.WIDGET_PREFERENCES_NAME, MODE_PRIVATE)
                .edit().putInt(String.valueOf(widgetId), themeId).apply();
        setResult(RESULT_OK, intent);
        updateWidget();
        finish();
    }

    private void updateWidget() {
        Intent intent = new Intent(getApplicationContext(), LocationWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{widgetId});
        sendBroadcast(intent);
    }
}
