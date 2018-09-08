package com.example.ama.android2_lesson04.background.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ama.android2_lesson04.background.BackgroundConstants;
import com.example.ama.android2_lesson04.background.utils.NetworkUtils;

public class LoadPictureCombinedService extends Service {

    private Handler handler;
    private HandlerThread thread;
    private IBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        thread = new HandlerThread(
                LoadPictureCombinedService.class.getSimpleName(),
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String[] extras = msg.getData().getStringArray(BackgroundConstants.EXTRA_KEY);
                if (extras != null && extras.length > 0) {
                    for (String o : extras) {
                        Bitmap bitmap = NetworkUtils.loadBitmapFromUrl(o);
                        if (bitmap != null) {
                            LocalBroadcastManager.getInstance(LoadPictureCombinedService.this)
                                    .sendBroadcast(new Intent(BackgroundConstants.ACTION_UPDATE)
                                            .putExtra(BackgroundConstants.EXTRA_KEY, bitmap));
                        }
                    }
                }
                LocalBroadcastManager.getInstance(LoadPictureCombinedService.this)
                        .sendBroadcast(new Intent(BackgroundConstants.ACTION_FINISH));
                stopSelf(msg.what);
            }
        };
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            loadImages(startId, intent.getExtras());
        }
        return START_NOT_STICKY;
    }

    private void loadImages(int startId, Bundle data) {
        Message msg = handler.obtainMessage(startId);
        msg.setData(data);
        handler.sendMessage(msg);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        thread.quit();
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        public void sendUrls(String... urls) {
            Bundle bundle = new Bundle();
            bundle.putStringArray(BackgroundConstants.EXTRA_KEY, urls);
            LoadPictureCombinedService.this.loadImages(BackgroundConstants.BOUND_SERVICE_MESSAGE_ID, bundle);
        }
    }
}
