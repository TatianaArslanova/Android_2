package com.example.ama.android2_lesson04.background.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.ama.android2_lesson04.background.utils.NetworkUtils;

import java.util.ArrayList;

public class MyLoader extends AsyncTaskLoader<ArrayList<Bitmap>> {
    public static final String LOADER_ARGS = "loader_args";
    private String[] urls;

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null) {
            urls = args.getStringArray(LOADER_ARGS);
        }
    }

    @Nullable
    @Override
    public ArrayList<Bitmap> loadInBackground() {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        if (urls == null || urls.length == 0) return null;
        for (String o : urls) {
            Bitmap bitmap = NetworkUtils.loadBitmapFromUrl(o);
            if (bitmap != null) {
                bitmaps.add(bitmap);
            }
        }
        return bitmaps;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
