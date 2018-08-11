package com.example.ama.android2_lesson04.background.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
        for (String o : urls) {
            try {
                InputStream inputStream = (InputStream) new URL(o).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Thread.sleep(2000);
                bitmaps.add(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
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
