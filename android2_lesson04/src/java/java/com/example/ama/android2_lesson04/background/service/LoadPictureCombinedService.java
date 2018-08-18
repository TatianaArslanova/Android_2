package com.example.ama.android2_lesson04.background.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
                String[] extras = msg.getData().getStringArray(ServiceConstants.EXTRA_KEY);
                if (extras != null) {
                    for (String o : extras) {
                        try {
                            InputStream inputStream = (InputStream) new URL(o).getContent();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            Thread.sleep(2000);
                            LocalBroadcastManager.getInstance(LoadPictureCombinedService.this)
                                    .sendBroadcast(new Intent(ServiceConstants.ACTION_UPDATE).putExtra(ServiceConstants.EXTRA_KEY, bitmap));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                LocalBroadcastManager.getInstance(LoadPictureCombinedService.this)
                        .sendBroadcast(new Intent(ServiceConstants.ACTION_FINISH));
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
            bundle.putStringArray(ServiceConstants.EXTRA_KEY, urls);
            LoadPictureCombinedService.this.loadImages(ServiceConstants.BOUND_SERVICE_MESSAGE_ID, bundle);
        }
    }
}
