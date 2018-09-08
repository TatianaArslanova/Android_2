package com.example.ama.android2_lesson04.ui.viewer;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ama.android2_lesson04.background.utils.NetworkUtils;
import com.example.ama.android2_lesson04.ui.viewer.base.BasePictureViewerFragment;
import com.example.ama.android2_lesson04.utils.ResourcesUtils;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PVRxJavaFragment extends BasePictureViewerFragment {
    private Disposable disposable;

    public static PVRxJavaFragment newInstance() {
        return new PVRxJavaFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onLoadImagesClick() {
        if (disposable == null) {
            loadWithRx(ResourcesUtils.getUrlsArray());
        }
    }

    private void loadWithRx(String... urls) {
        disposable = Observable.fromIterable(Arrays.asList(urls))
                .map(NetworkUtils::loadBitmapFromUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::onFinishLoading)
                .doOnError(throwable -> onFinishLoading())
                .subscribe(this::onUpdateLoading);
    }

    @Override
    protected void onFinishLoading() {
        super.onFinishLoading();
        dispose();
    }

    @Override
    public void onDestroy() {
        dispose();
        super.onDestroy();
    }

    private void dispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
