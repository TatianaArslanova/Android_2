package com.example.ama.android2_lesson04.background.asynk;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.example.ama.android2_lesson04.background.utils.NetworkUtils;

import java.util.ArrayList;

public class LoadingPicturesAsynkTask extends AsyncTask<String, Bitmap, ArrayList<Bitmap>> {
    private OnLoadingListener listener;

    public LoadingPicturesAsynkTask(OnLoadingListener listener) {
        this.listener = listener;
    }

    public void setListener(OnLoadingListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        listener = null;
    }

    @Override
    protected ArrayList<Bitmap> doInBackground(String... strings) {
        if (strings == null || strings.length == 0) return null;
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (String o : strings) {
            if (isCancelled()) return null;
            Bitmap bitmap = NetworkUtils.loadBitmapFromUrl(o);
            if (bitmap != null) {
                bitmaps.add(bitmap);
                publishProgress(bitmap);
            }
        }
        return bitmaps;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        if (listener != null) {
            listener.onUpdate(values[0]);
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
        if (listener != null) {
            listener.onFinish();
            removeListener();
        }
    }

    public interface OnLoadingListener {
        void onUpdate(Bitmap bitmap);

        void onFinish();
    }
}
