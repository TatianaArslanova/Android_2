package com.example.ama.android2_lesson04.background.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ama.android2_lesson04.R;
import com.example.ama.android2_lesson04.background.utils.NetworkUtils;

import static com.example.ama.android2_lesson04.background.BackgroundConstants.ACTION_FINISH;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.ACTION_UPDATE;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.BOUND_SERVICE_MESSAGE_ID;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.CHANNEL_ID;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.CHANNEL_NAME;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.EXTRA_KEY;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.NOTIFICATION_ID;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.NOTIFICATION_TITLE;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.PROGRESS_BAR_MAX;
import static com.example.ama.android2_lesson04.background.BackgroundConstants.PROGRESS_BAR_START;

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
                String[] extras = msg.getData().getStringArray(EXTRA_KEY);
                if (extras != null && extras.length > 0) {
                    for (String o : extras) {
                        Bitmap bitmap = NetworkUtils.loadBitmapFromUrl(o);
                        if (bitmap != null) {
                            LocalBroadcastManager.getInstance(LoadPictureCombinedService.this)
                                    .sendBroadcast(new Intent(ACTION_UPDATE)
                                            .putExtra(EXTRA_KEY, bitmap));
                        }
                    }
                }
                LocalBroadcastManager.getInstance(LoadPictureCombinedService.this)
                        .sendBroadcast(new Intent(ACTION_FINISH));
                stopSelf(msg.what);
            }
        };
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            createNotificationChannel();
            startForeground(NOTIFICATION_ID, buildNotification());
            loadImages(startId, intent.getExtras());
        }
        return START_NOT_STICKY;
    }

    private void loadImages(int startId, Bundle data) {
        Message msg = handler.obtainMessage(startId);
        msg.setData(data);
        handler.sendMessage(msg);
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(NOTIFICATION_TITLE)
                .setProgress(
                        PROGRESS_BAR_MAX,
                        PROGRESS_BAR_START,
                        true)
                .setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
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
            bundle.putStringArray(EXTRA_KEY, urls);
            LoadPictureCombinedService.this.loadImages(BOUND_SERVICE_MESSAGE_ID, bundle);
        }
    }
}
