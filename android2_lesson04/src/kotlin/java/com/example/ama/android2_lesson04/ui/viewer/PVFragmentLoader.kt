package com.example.ama.android2_lesson04.ui.viewer

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.view.View
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.ServiceTestApp
import com.example.ama.android2_lesson04.background.loader.LOADER_ARGS
import com.example.ama.android2_lesson04.background.loader.PictureLoader
import com.example.ama.android2_lesson04.ui.viewer.base.BasePictureViewerFragment

class PVFragmentLoader
    : BasePictureViewerFragment(),
        LoaderManager.LoaderCallbacks<ArrayList<Bitmap>> {

    companion object {
        const val LOADER_ID = 1
        fun newInstance() = PVFragmentLoader()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isLoading) initLoader()
    }

    override fun onLoadImagesClick() {
        initLoader()
    }

    private fun initLoader() {
        val args = Bundle()
        args.putStringArray(LOADER_ARGS, resources.getStringArray(R.array.image_urls))
        if (loaderManager.getLoader<PictureLoader>(LOADER_ID) == null) {
            loaderManager.initLoader(LOADER_ID, args, this)
        } else {
            loaderManager.restartLoader(LOADER_ID, args, this)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?) =
            PictureLoader(ServiceTestApp.instance, args)

    override fun onLoadFinished(loader: Loader<ArrayList<Bitmap>>, data: ArrayList<Bitmap>?) {
        if (data != null) {
            ServiceTestApp.data.bitmaps.addAll(data)
            showBitmaps(ServiceTestApp.data.bitmaps)
        }
        onFinishLoading()
    }

    override fun onLoaderReset(loader: Loader<ArrayList<Bitmap>>) {

    }

}