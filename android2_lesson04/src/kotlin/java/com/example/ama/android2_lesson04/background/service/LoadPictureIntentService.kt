package com.example.ama.android2_lesson04.background.service

import android.app.IntentService
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.content.LocalBroadcastManager
import java.io.IOException
import java.io.InputStream
import java.net.URL

class LoadPictureIntentService
    : IntentService(LoadPictureIntentService::class.java.simpleName) {

    override fun onHandleIntent(intent: Intent?) {
        try {
            val extras = intent?.extras?.getStringArray(EXTRA_KEY)
            if (extras != null) {
                for (o in extras) {
                    val inputStream = URL(o).content as InputStream
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    Thread.sleep(2000)
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
    }
}