package com.example.ama.android2_lesson04.background.service

import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import android.support.v4.content.LocalBroadcastManager
import com.example.ama.android2_lesson04.ui.viewer.base.ACTION_FINISH
import com.example.ama.android2_lesson04.ui.viewer.base.ACTION_UPDATE
import com.example.ama.android2_lesson04.ui.viewer.base.EXTRA_KEY
import java.io.IOException
import java.io.InputStream
import java.net.URL

class LoadPictureStartedService : Service() {

    lateinit var thread: HandlerThread
    lateinit var handler: Handler

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
        val msg = handler.obtainMessage(startId)
        msg.data = intent?.extras
        handler.sendMessage(msg)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        thread.quit()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null
}