package com.example.ama.android2_lesson03.widget.model

import android.app.PendingIntent

data class WidgetModel(val coordinates: String,
                       val address: String,
                       val pendingIntent: PendingIntent) {
}