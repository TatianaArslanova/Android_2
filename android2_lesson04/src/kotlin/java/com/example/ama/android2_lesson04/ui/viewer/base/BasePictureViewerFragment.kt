package com.example.ama.android2_lesson04.ui.viewer.base

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.ServiceTestApp
import com.example.ama.android2_lesson04.ui.viewer.adapter.TestPagerAdapter
import kotlinx.android.synthetic.main.fragment_picture_viewer.*

abstract class BasePictureViewerFragment : Fragment() {
    companion object {
        const val IS_LOADING = "isLoading"
    }

    protected var isLoading = false
        private set(value) {
            field = value
            if (view != null) {
                pb_progress.visibility = if (field) View.VISIBLE else View.GONE
                btn_load_images.isEnabled = !field
            }
        }
    private lateinit var adapter: TestPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState == null) ServiceTestApp.data.bitmaps = ArrayList()
        return inflater.inflate(R.layout.fragment_picture_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        isLoading = savedInstanceState?.getBoolean(IS_LOADING) ?: false
        adapter = TestPagerAdapter(ServiceTestApp.instance, ServiceTestApp.data.bitmaps)
        vp_main.adapter = adapter
        btn_load_images.setOnClickListener {
            isLoading = true
            onLoadImagesClick()
        }
        setLoadingCount()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_LOADING, isLoading)
        super.onSaveInstanceState(outState)
    }

    protected fun showBitmaps(bitmaps: ArrayList<Bitmap>) {
        adapter.bitmaps = bitmaps
        setLoadingCount()
    }

    protected fun onUpdateLoading(bitmap: Bitmap) {
        ServiceTestApp.data.addBitmap(bitmap)
        showBitmaps(ServiceTestApp.data.bitmaps)
    }

    protected fun onFinishLoading() {
        isLoading = false
    }

    private fun setLoadingCount() {
        tv_count.text = resources.getString(R.string.fragment_picture_viewer_tv_count, adapter.count)
    }

    abstract fun onLoadImagesClick()
}