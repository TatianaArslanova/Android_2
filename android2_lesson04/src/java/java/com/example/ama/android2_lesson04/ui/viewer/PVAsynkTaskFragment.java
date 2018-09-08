package com.example.ama.android2_lesson04.ui.viewer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.ama.android2_lesson04.background.asynk.LoadingPicturesAsynkTask;
import com.example.ama.android2_lesson04.ui.viewer.base.BasePictureViewerFragment;
import com.example.ama.android2_lesson04.utils.ResourcesUtils;

public class PVAsynkTaskFragment extends BasePictureViewerFragment {

    private LoadingPicturesAsynkTask asynkTask;

    public static PVAsynkTaskFragment newInstance() {
        return new PVAsynkTaskFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (asynkTask != null) {
            asynkTask.setListener(getListener());
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onLoadImagesClick() {
        if (asynkTask == null) {
            asynkTask = new LoadingPicturesAsynkTask(getListener());
            asynkTask.execute(ResourcesUtils.getUrlsArray());
        }
    }

    private LoadingPicturesAsynkTask.OnLoadingListener getListener() {
        return new LoadingPicturesAsynkTask.OnLoadingListener() {
            @Override
            public void onUpdate(Bitmap bitmap) {
                onUpdateLoading(bitmap);
            }

            @Override
            public void onFinish() {
                onFinishLoading();
                asynkTask = null;
            }
        };
    }

    @Override
    public void onDestroyView() {
        if (asynkTask != null) {
            asynkTask.removeListener();
        }
        super.onDestroyView();
    }
}
