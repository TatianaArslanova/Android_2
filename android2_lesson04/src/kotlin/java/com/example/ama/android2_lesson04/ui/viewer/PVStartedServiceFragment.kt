package com.example.ama.android2_lesson04.ui.viewer

import android.content.Intent
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.ServiceTestApp
import com.example.ama.android2_lesson04.background.utils.EXTRA_KEY
import com.example.ama.android2_lesson04.background.service.LoadPictureCombinedService
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment

class PVStartedServiceFragment : BaseReceiverFragment() {

    companion object {
        fun newInstance() = PVStartedServiceFragment()
    }

    override fun onLoadImagesClick() {
        startService()
    }

    private fun startService() {
        val intent = Intent(ServiceTestApp.instance, LoadPictureCombinedService::class.java)
        intent.putExtra(EXTRA_KEY, resources.getStringArray(R.array.image_urls))
        activity?.startService(intent)
    }
}