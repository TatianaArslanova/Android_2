package com.example.ama.android2_lesson04.ui.viewer.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import com.example.ama.android2_lesson04.ServiceTestApp

const val ACTION_UPDATE = "com.example.ama.android2_lesson04.action.UPDATE"
const val ACTION_FINISH = "com.example.ama.android2_lesson04.action.FINISH"
const val EXTRA_KEY = "extra_key"

abstract class BaseReceiverFragment : BasePictureViewerFragment() {
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                when (intent.action) {
                    ACTION_UPDATE -> {
                        onUpdateLoading(intent.extras.getParcelable<Bitmap>(EXTRA_KEY))
                    }
                    ACTION_FINISH -> onFinishLoading()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initReceiver()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initReceiver() {
        val filter = IntentFilter()
        filter.addAction(ACTION_UPDATE)
        filter.addAction(ACTION_FINISH)
        LocalBroadcastManager.getInstance(ServiceTestApp.instance).registerReceiver(receiver, filter)
    }

    override fun onDestroyView() {
        LocalBroadcastManager.getInstance(ServiceTestApp.instance).unregisterReceiver(receiver)
        super.onDestroyView()
    }
}
