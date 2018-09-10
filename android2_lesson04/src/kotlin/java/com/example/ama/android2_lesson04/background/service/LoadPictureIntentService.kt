package com.example.ama.android2_lesson04.background.service

import android.app.IntentService
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.example.ama.android2_lesson04.background.utils.ACTION_FINISH
import com.example.ama.android2_lesson04.background.utils.ACTION_UPDATE
import com.example.ama.android2_lesson04.background.utils.EXTRA_KEY
import com.example.ama.android2_lesson04.background.utils.NetworkUtils
import java.io.IOException

class LoadPictureIntentService
    : IntentService(LoadPictureIntentService::class.java.simpleName) {

    override fun onHandleIntent(intent: Intent?) {
        try {
            val extras = intent?.extras?.getStringArray(EXTRA_KEY)
            if (extras != null) {
                for (o in extras) {
                    val bitmap = NetworkUtils.loadBitmapFromUrl(o)
                    if (bitmap != null) {
                        LocalBroadcastManager.getInstance(this)
                                .sendBroadcast(Intent(ACTION_UPDATE).putExtra(EXTRA_KEY, bitmap))
                    }
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