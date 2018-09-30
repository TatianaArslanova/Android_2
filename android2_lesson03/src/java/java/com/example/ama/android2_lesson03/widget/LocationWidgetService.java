package com.example.ama.android2_lesson03.widget;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.repo.data.base.LocManager;
import com.example.ama.android2_lesson03.repo.data.location.LocationManagerAndroid;
import com.example.ama.android2_lesson03.utils.PermissionManager;
import com.example.ama.android2_lesson03.widget.model.WidgetModel;
import com.example.ama.android2_lesson03.widget.model.WidgetModelFactory;
import com.google.android.gms.maps.model.LatLng;

public class LocationWidgetService extends Service {

    public static final long WAIT_LOCATION_UPDATE = 5000;
    public static final int NOTIFICATION_ID = 1;
    public static final int FIRST_START_ID = 1;
    public static final String CHANNEL_ID = "channel_01";
    public static final String CHANNEL_NAME = "find_location_channel";

    private LocManager locManager;
    private LocManager.OnLocationSearchResultCallback listener;
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
            public void handleMessage(Message msg) {
                if (PermissionManager.checkLocationPermission()) {
                    requestLocation();
                } else {
                    sendWidgetUpdates(
                            factory.permissionRequiredModel());
                    stopSelf();
                }
            }
        };
        listener = new LocManager.OnLocationSearchResultCallback() {
            @Override
            public void onLocationFound(Location location) {
                sendWidgetUpdates(
                        factory.fullModel(
                                location,
                                locManager.findAddressByCoords(
                                        new LatLng(
                                                location.getLatitude(),
                                                location.getLongitude()))));
            }

            @Override
            public void onError(String message) {
            }
        };
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LocationWidgetService.class.getSimpleName(),
                "STARTED" + String.valueOf(startId));
        if (startId == FIRST_START_ID) {
            createNotificationChannel();
            startForeground(NOTIFICATION_ID, buildNotification());
            handler.sendEmptyMessage(startId);
            handler.postDelayed(this::stopWork, WAIT_LOCATION_UPDATE);
        } else {
            Log.d(LocationWidgetService.class.getSimpleName(),
                    "NOT HANDLED" + String.valueOf(startId));
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        thread.quit();
        Log.d(LocationWidgetService.class.getSimpleName(), "OnDestroy");
        super.onDestroy();
    }

    private void requestLocation() {
        locManager.findMyLocation(listener);
        locManager.subscribeOnLocationChanges(listener);
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

    private void stopWork() {
        locManager.unsubscribeOfLocationChanges();
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
