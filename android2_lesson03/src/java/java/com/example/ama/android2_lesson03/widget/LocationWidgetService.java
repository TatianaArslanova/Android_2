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
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.example.ama.android2_lesson03.widget.model.WidgetModel;
import com.example.ama.android2_lesson03.widget.model.WidgetModelFactory;
import com.google.android.gms.maps.model.LatLng;

public class LocationWidgetService extends Service {

    public static final int NOTIFICATION_ID = 1;
    public static final String CHANNEL_ID = "channel_01";
    public static final String CHANNEL_NAME = "find_location_channel";

    private LocManager locManager;
    private WidgetModelFactory factory;
    private HandlerThread thread;
    private Handler handler;

    @Override
    public void onCreate() {
        locManager = new LocationManagerAndroid();
        factory = new WidgetModelFactory(getApplicationContext());
        thread = new HandlerThread(this.getClass().getSimpleName(),
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(final Message msg) {
                if (PermissionManager.checkLocationPermission()) {
                    requestLocation(msg.what);
                } else {
                    sendWidgetUpdates(
                            factory.permissionRequiredModel());
                    stopSelf(msg.what);
                }
            }
        };
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification());
        handler.sendMessage(handler.obtainMessage(startId));
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

    private void requestLocation(final int startId) {
        locManager.subscribeOnLocationChanges(new LocManager.OnLocationSearchResultCallback() {
            @Override
            public void onLocationFound(Location location) {
                sendWidgetUpdates(
                        factory.fullModel(location, getLocationName(location)));
                locManager.unsubscribeOfLocationChanges();
                stopSelf(startId);
            }

            @Override
            public void onError(String message) {
                locManager.unsubscribeOfLocationChanges();
                stopSelf(startId);
            }
        });
    }

    private void sendWidgetUpdates(WidgetModel widgetModel) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] widgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(getApplicationContext(),
                        LocationWidgetProvider.class));
        LocationWidgetProvider.updateWidgets(
                getApplicationContext(),
                appWidgetManager,
                widgetIds,
                widgetModel);
    }

    private String getLocationName(Location location) {
        Address address = locManager.findAddressByCoords(new LatLng(location.getLatitude(), location.getLongitude()));
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
