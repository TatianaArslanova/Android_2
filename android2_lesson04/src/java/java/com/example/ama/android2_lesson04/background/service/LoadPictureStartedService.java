package com.example.ama.android2_lesson04.background.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class LoadPictureStartedService extends Service {

    private Handler handler;
    private HandlerThread thread;

    @Override
    public void onCreate() {
        thread = new HandlerThread(
                LoadPictureStartedService.class.getSimpleName(),
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
                            LocalBroadcastManager.getInstance(LoadPictureStartedService.this)
                                    .sendBroadcast(new Intent(ServiceConstants.ACTION_UPDATE).putExtra(ServiceConstants.EXTRA_KEY, bitmap));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                LocalBroadcastManager.getInstance(LoadPictureStartedService.this)
                        .sendBroadcast(new Intent(ServiceConstants.ACTION_FINISH));
                stopSelf(msg.what);
            }
        };
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            Message msg = handler.obtainMessage(startId);
            msg.setData(intent.getExtras());
            handler.sendMessage(msg);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        thread.quit();
        super.onDestroy();
    }
}
