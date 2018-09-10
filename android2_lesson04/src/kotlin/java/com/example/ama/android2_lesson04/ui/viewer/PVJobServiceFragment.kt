package com.example.ama.android2_lesson04.ui.viewer

import android.os.Bundle
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.background.service.LoadPictureJobService
import com.example.ama.android2_lesson04.background.utils.EXTRA_KEY
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment
import com.firebase.jobdispatcher.*

class PVJobServiceFragment : BaseReceiverFragment() {
    private lateinit var dispatcher: FirebaseJobDispatcher

    companion object {
        fun newInstance() = PVJobServiceFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
        retainInstance = true
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        dispatcher.cancel(LoadPictureJobService::class.java.simpleName)
        super.onDestroy()
    }

    override fun onLoadImagesClick() {
        scheduleLoadingImagesJob()
    }

    private fun scheduleLoadingImagesJob() {
        val bundle = Bundle()
        bundle.putStringArray(EXTRA_KEY, resources.getStringArray(R.array.image_urls))
        val job = dispatcher.newJobBuilder()
                .setService(LoadPictureJobService::class.java)
                .setExtras(bundle)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.NOW)
                .setTag(LoadPictureJobService::class.java.simpleName)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build()
        dispatcher.schedule(job)
    }
}