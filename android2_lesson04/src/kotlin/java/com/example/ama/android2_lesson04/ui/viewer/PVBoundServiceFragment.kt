package com.example.ama.android2_lesson04.ui.viewer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.ServiceTestApp
import com.example.ama.android2_lesson04.background.service.LoadPictureCombinedService
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment

class PVBoundServiceFragment : BaseReceiverFragment() {

    companion object {
        fun newInstance() = PVBoundServiceFragment()
    }

    private var isBound = false
    private var binder: LoadPictureCombinedService.MyBinder? = null
    private val serviceConnection: PVServiceConnection = PVServiceConnection()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ServiceTestApp.instance.bindService(Intent(ServiceTestApp.instance,
                LoadPictureCombinedService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        ServiceTestApp.instance.unbindService(serviceConnection)
        super.onDestroyView()
    }

    override fun onLoadImagesClick() {
        if (isBound) {
            binder?.sendUrls(*resources.getStringArray(R.array.image_urls))
        }
    }

    inner class PVServiceConnection : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as LoadPictureCombinedService.MyBinder?
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
            isBound = false
        }
    }
}