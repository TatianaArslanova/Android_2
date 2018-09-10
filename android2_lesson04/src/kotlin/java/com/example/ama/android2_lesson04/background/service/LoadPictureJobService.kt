package com.example.ama.android2_lesson04.background.service

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.example.ama.android2_lesson04.background.utils.ACTION_FINISH
import com.example.ama.android2_lesson04.background.utils.ACTION_UPDATE
import com.example.ama.android2_lesson04.background.utils.EXTRA_KEY
import com.example.ama.android2_lesson04.background.utils.NetworkUtils
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoadPictureJobService : JobService() {

    private var disposable: Disposable? = null

    override fun onStartJob(job: JobParameters?): Boolean {
        if (job == null || job.extras == null) return false
        disposable = Observable.fromIterable(listOf(*job.extras!!.getStringArray(EXTRA_KEY)))
                .map { it -> NetworkUtils.loadBitmapFromUrl(it)!! }
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .doOnError { t -> finishJob(job) }
                .doOnComplete { finishJob(job) }
                .subscribe { bitmap ->
                    LocalBroadcastManager.getInstance(applicationContext)
                            .sendBroadcast(Intent(ACTION_UPDATE).putExtra(EXTRA_KEY, bitmap))
                }
        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }

    private fun finishJob(job: JobParameters) {
        LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(Intent(ACTION_FINISH))
        disposable?.dispose()
        jobFinished(job, false)
    }
}