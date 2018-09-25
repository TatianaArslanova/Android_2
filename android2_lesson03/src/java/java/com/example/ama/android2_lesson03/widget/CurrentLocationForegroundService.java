package com.example.ama.android2_lesson03.widget;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.data.base.LocManager;
import com.example.ama.android2_lesson03.repo.data.location.LocationManagerAndroid;
import com.google.android.gms.maps.model.LatLng;

public class CurrentLocationForegroundService extends Service {
    public static final int NOTIFICATION_ID = 1;
    public static final String CHANNEL_ID = "channel_01";
    public static final String CHANNEL_NAME = "find_location_channel";

    private LocManager locManager;
    private HandlerThread thread;
    private Handler handler;

    @Override
    public void onCreate() {
        locManager = new LocationManagerAndroid();
        thread = new HandlerThread(this.getClass().getSimpleName(),
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(final Message msg) {
                locManager.subscribeOnLocationChanges(new LocManager.OnLocationSearchResultCallback() {
                    @Override
                    public void onLocationFound(Location location) {
                        sendWidgetUpdates(location);
                        locManager.unsubscribeOfLocationChanges();
                        stopSelf(msg.what);
                    }

                    @Override
                    public void onError(String message) {
                        stopSelf(msg.what);
                    }
                });
            }
        };
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification());
        requestLocation(startId);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        thread.quit();
        super.onDestroy();
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_message_detecting_location))
                .build();
    }

    private void requestLocation(int startId) {
        handler.sendMessage(handler.obtainMessage(startId));
    }

    private void sendWidgetUpdates(Location location) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(getApplicationContext(),
                        CurrentLocationWidgetProvider.class));
        CurrentLocationWidgetProvider.updateWidgets(
                getApplicationContext(),
                appWidgetManager,
                widgetIds,
                latLng,
                buildFullName(locManager.findAddressByCoords(latLng)));
    }

    private String buildFullName(Address address) {
        StringBuilder fullLocationName = new StringBuilder();
        if (address != null) {
            int index = address.getMaxAddressLineIndex();
            for (int i = 0; i <= index; i++) {
                if (fullLocationName.length() != 0) fullLocationName.append(", ");
                fullLocationName.append(address.getAddressLine(i));
            }
        }
        return fullLocationName.toString();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
