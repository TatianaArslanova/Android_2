package com.example.ama.android2_lesson03.widget

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.*
import android.support.v4.app.NotificationCompat
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.repo.data.base.LocManager
import com.example.ama.android2_lesson03.repo.data.location.LocationManagerAndroid
import com.example.ama.android2_lesson03.utils.PermissionManager
import com.example.ama.android2_lesson03.widget.model.WidgetModel
import com.example.ama.android2_lesson03.widget.model.WidgetModelFactory
import com.google.android.gms.maps.model.LatLng

class LocationWidgetService : Service() {

    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "location_service_channel"
    }

    private val locManager: LocManager = LocationManagerAndroid()

    private lateinit var thread: HandlerThread
    private lateinit var handler: Handler
    private lateinit var factory: WidgetModelFactory

    override fun onCreate() {
        factory = WidgetModelFactory(applicationContext)
        thread = HandlerThread(this.javaClass.simpleName, Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        handler = Handler(thread.looper) { message ->
            if (PermissionManager.checkLocationPermission()) {
                requestLocationUpdate(message.what)
            } else {
                sendWidgetUpdate(factory.permissionRequiredModel())
                stopSelf(message.what)
            }
            true
        }
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification())
        handler.sendMessage(handler.obtainMessage(startId))
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        thread.quit()
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification() =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                    .setContentTitle(resources.getString(R.string.notification_message_detecting_location))
                    .build()

    private fun requestLocationUpdate(startId: Int) {
        locManager.subscribeOnLocationUpdates(
                locationFound = { location ->
                    sendWidgetUpdate(factory.fullModel(
                            location,
                            locManager.findAddressByLatLng(LatLng(location.latitude, location.longitude))))
                    unsubscribe(startId)
                },
                error = { unsubscribe(startId) })
    }

    private fun sendWidgetUpdate(model: WidgetModel) {
        val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
        val widgetIds = appWidgetManager
                .getAppWidgetIds(ComponentName(applicationContext, LocationWidgetProvider::class.java))
        LocationWidgetProvider.updateWidgets(
                applicationContext,
                appWidgetManager,
                widgetIds,
                model)
    }

    private fun unsubscribe(startId: Int) {
        locManager.unsubscribeOfLocationUpdates()
        stopSelf(startId)
    }
}