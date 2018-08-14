package com.example.ama.android2_lesson04.ui.viewer

import android.content.Intent
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.ServiceTestApp
import com.example.ama.android2_lesson04.background.service.LoadPictureIntentService
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment
import com.example.ama.android2_lesson04.ui.viewer.base.EXTRA_KEY

class PVFragmentIntentService : BaseReceiverFragment() {

    companion object {
        fun newInstance() = PVFragmentIntentService()
    }

    override fun onLoadImagesClick() {
        startIntentService()
    }

    private fun startIntentService() {
        val intent = Intent(ServiceTestApp.instance, LoadPictureIntentService::class.java)
        intent.putExtra(EXTRA_KEY, resources.getStringArray(R.array.image_urls))
        activity?.startService(intent)
    }
}