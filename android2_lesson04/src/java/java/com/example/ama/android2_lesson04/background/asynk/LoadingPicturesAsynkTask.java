package com.example.ama.android2_lesson04.background.asynk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (String o : strings) {
            try {
                if (isCancelled()) return null;
                InputStream inputStream = (InputStream) new URL(o).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Thread.sleep(2000);
                bitmaps.add(bitmap);
                publishProgress(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
