package com.example.ama.android2_lesson04.background.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.support.v4.app.NotificationCompat
import android.support.v4.content.LocalBroadcastManager
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.background.utils.*
import java.io.IOException

class LoadPictureCombinedService : Service() {

    private lateinit var thread: HandlerThread
    private lateinit var handler: Handler
    private val binder: IBinder = MyBinder()

    override fun onCreate() {
        thread = HandlerThread(this::class.java.simpleName, Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        handler = Handler(thread.looper) { msg ->
            try {
                val extras = msg.data.getStringArray(EXTRA_KEY)
                for (o in extras) {
                    val bitmap = NetworkUtils.loadBitmapFromUrl(o)
                    if (bitmap != null) {
                        LocalBroadcastManager.getInstance(this)
                                .sendBroadcast(Intent(ACTION_UPDATE).putExtra(EXTRA_KEY, bitmap))
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(Intent(ACTION_FINISH))
            stopSelf(msg.what)
            true
        }
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            createNotificationChannel()
            startForeground(NOTIFICATION_ID, buildNotification())
            loadImages(intent.extras, startId)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        thread.quit()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = binder

    private fun buildNotification() =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                    .setContentTitle(NOTIFICATION_TITLE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setProgress(PROGRESS_BAR_MAX, PROGRESS_BAR_START, true)
                    .build()

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT)
            val manager: NotificationManager? =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager?.createNotificationChannel(channel)
        }
    }

    private fun loadImages(data: Bundle?, startId: Int = BOUND_SERVICE_MESSAGE_ID) {
        val message = handler.obtainMessage(startId)
        message.data = data
        handler.sendMessage(message)
    }

    inner class MyBinder : Binder() {
        fun sendUrls(vararg urls: String) {
            val bundle = Bundle()
            bundle.putStringArray(EXTRA_KEY, urls)
            loadImages(bundle)
        }
    }
}