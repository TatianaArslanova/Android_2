package com.example.ama.android2_lesson04.ui.viewer

import android.os.Bundle
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.background.utils.NetworkUtils
import com.example.ama.android2_lesson04.ui.viewer.base.BasePictureViewerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PVRxJavaFragment : BasePictureViewerFragment() {
    var disposable: Disposable? = null

    companion object {
        fun newInstance() = PVRxJavaFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        dispose()
        super.onDestroy()
    }

    override fun onFinishLoading() {
        super.onFinishLoading()
        dispose()
    }

    override fun onLoadImagesClick() {
        if (disposable == null) {
            loadWithRx()
        }
    }

    private fun loadWithRx() {
        disposable = Observable.fromIterable(listOf(*resources.getStringArray(R.array.image_urls)))
                .map { it -> NetworkUtils.loadBitmapFromUrl(it)!! }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { onFinishLoading() }
                .doOnComplete { onFinishLoading() }
                .subscribe { it -> onUpdateLoading(it) }
    }

    private fun dispose() {
        disposable?.dispose()
        disposable = null
    }
}