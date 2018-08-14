package com.example.ama.android2_lesson04.ui.viewer

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.background.asynktask.LoadPicturesAsynkTask
import com.example.ama.android2_lesson04.ui.viewer.base.BasePictureViewerFragment

class PVFragmentAsynkTask : BasePictureViewerFragment() {

    companion object {
        fun newInstance() = PVFragmentAsynkTask()
    }

    private var asynkTask: LoadPicturesAsynkTask? = null
    private val updateListener = { bitmap: Bitmap ->
        onUpdateLoading(bitmap)
    }
    private val finishListener = {
        onFinishLoading()
        asynkTask = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        asynkTask?.setListeners(finishListener, updateListener)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onLoadImagesClick() {
        if (asynkTask == null) {
            asynkTask = LoadPicturesAsynkTask(finishListener, updateListener)
            asynkTask?.execute(*resources.getStringArray(R.array.image_urls))
        }
    }

    override fun onDestroyView() {
        asynkTask?.removeListeners()
        super.onDestroyView()
    }
}