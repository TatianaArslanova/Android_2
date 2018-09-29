package com.example.ama.android2_lesson03.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.widget.utils.THEME_DARK
import com.example.ama.android2_lesson03.widget.utils.THEME_LIGHT
import com.example.ama.android2_lesson03.widget.utils.WIDGET_PREFERENCES_NAME
import kotlinx.android.synthetic.main.widget_config_activity.*

class WidgetConfigActivity : AppCompatActivity() {
    private var widgetId: Int = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var resultIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widget_config_activity)
        widgetId = intent.extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }
        resultIntent = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        setResult(Activity.RESULT_CANCELED, resultIntent)
        setListeners()
    }

    private fun setListeners() {
        ib_dark_theme.setOnClickListener { setWidgetTheme(THEME_DARK) }
        iv_light_theme.setOnClickListener { setWidgetTheme(THEME_LIGHT) }
    }

    private fun setWidgetTheme(themeId: Int) {
        getSharedPreferences(WIDGET_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .edit().putInt(widgetId.toString(), themeId).apply()
        setResult(Activity.RESULT_OK, intent)
        updateWidget()
        finish()
    }

    private fun updateWidget() {
        val updateIntent = Intent(applicationContext, LocationWidgetProvider::class.java)
        updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(widgetId))
        sendBroadcast(updateIntent)
    }
}