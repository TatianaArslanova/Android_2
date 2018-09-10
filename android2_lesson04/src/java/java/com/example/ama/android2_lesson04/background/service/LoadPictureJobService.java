package com.example.ama.android2_lesson04.background.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ama.android2_lesson04.background.BackgroundConstants;
import com.example.ama.android2_lesson04.background.utils.NetworkUtils;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadPictureJobService extends JobService {
    private Disposable disposable;

    @Override
    public boolean onStartJob(JobParameters job) {
        if (job.getExtras() != null) {
            String[] extras = job.getExtras().getStringArray(BackgroundConstants.EXTRA_KEY);
            if (extras != null) {
                disposable = Observable.fromIterable(Arrays.asList(extras))
                        .map(NetworkUtils::loadBitmapFromUrl)
                        .filter(bitmap -> bitmap != null)
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .doOnError(throwable -> finishJob(job))
                        .doOnComplete(() -> finishJob(job))
                        .subscribe(bitmap -> LocalBroadcastManager.getInstance(this)
                                .sendBroadcast(new Intent(BackgroundConstants.ACTION_UPDATE)
                                        .putExtra(BackgroundConstants.EXTRA_KEY, bitmap)));
            }
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        finishJob(job);
        return false;
    }

    private void finishJob(JobParameters job) {
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(new Intent(BackgroundConstants.ACTION_FINISH));
        disposable.dispose();
        jobFinished(job, false);
    }
}
