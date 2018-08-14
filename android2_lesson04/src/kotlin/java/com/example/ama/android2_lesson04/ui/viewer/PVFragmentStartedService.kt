package com.example.ama.android2_lesson04.ui.viewer

import android.content.Intent
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.ServiceTestApp
import com.example.ama.android2_lesson04.background.service.LoadPictureStartedService
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment
import com.example.ama.android2_lesson04.ui.viewer.base.EXTRA_KEY

class PVFragmentStartedService : BaseReceiverFragment() {

    companion object {
        fun newInstance() = PVFragmentStartedService()
    }

    override fun onLoadImagesClick() {
        startService()
    }

    private fun startService() {
        val intent = Intent(ServiceTestApp.instance, LoadPictureStartedService::class.java)
        intent.putExtra(EXTRA_KEY, resources.getStringArray(R.array.image_urls))
        activity?.startService(intent)
    }
}