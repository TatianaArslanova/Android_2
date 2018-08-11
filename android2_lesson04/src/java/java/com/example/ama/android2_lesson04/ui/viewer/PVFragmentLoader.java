package com.example.ama.android2_lesson04.ui.viewer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;

import com.example.ama.android2_lesson04.ServiceTestApp;
import com.example.ama.android2_lesson04.background.loader.MyLoader;
import com.example.ama.android2_lesson04.ui.viewer.base.BasePictureViewerFragment;
import com.example.ama.android2_lesson04.utils.ResourcesUtils;

import java.util.ArrayList;

public class PVFragmentLoader
        extends BasePictureViewerFragment
        implements LoaderManager.LoaderCallbacks<ArrayList<Bitmap>> {

    public static final int LOADER_ID = 1;

    public static PVFragmentLoader newInstance() {
        return new PVFragmentLoader();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isLoading) {
            initLoader();
        }
    }

    @Override
    protected void onLoadImagesClick() {
        initLoader();
    }

    @NonNull
    @Override
    public Loader<ArrayList<Bitmap>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyLoader(ServiceTestApp.getInstance(), args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Bitmap>> loader, ArrayList<Bitmap> data) {
        ServiceTestApp.getData().addBitmaps(data);
        showPictures(ServiceTestApp.getData().getBitmaps());
        onFinishLoading();
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    private void initLoader() {
        Bundle args = new Bundle();
        args.putStringArray(MyLoader.LOADER_ARGS, ResourcesUtils.getUrlsArray());
        if (getLoaderManager().getLoader(LOADER_ID) == null) {
            getLoaderManager().initLoader(LOADER_ID, args, this);
        } else {
            getLoaderManager().restartLoader(LOADER_ID, args, this);
        }
    }
}
