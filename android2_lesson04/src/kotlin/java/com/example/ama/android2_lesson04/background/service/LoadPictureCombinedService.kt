package com.example.ama.android2_lesson04.background.service

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.*
import android.support.v4.content.LocalBroadcastManager
import java.io.IOException
import java.io.InputStream
import java.net.URL

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
                    val inputStream = URL(o).content as InputStream
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    Thread.sleep(2000)
                    LocalBroadcastManager.getInstance(this)
                            .sendBroadcast(Intent(ACTION_UPDATE).putExtra(EXTRA_KEY, bitmap))
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
            loadImages(intent.extras)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        thread.quit()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = binder

    private fun loadImages(data: Bundle, startId: Int = BOUND_SERVICE_MESSAGE_ID) {
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