package com.example.ama.android2_lesson04.background.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class LoadPictureIntentService extends IntentService {

    public static final String ACTION_UPDATE = "com.example.ama.android2_lesson04.action.UPDATE";
    public static final String ACTION_FINISH = "com.example.ama.android2_lesson04.action.FINISH";
    public static final String EXTRA_KEY = "extra_key";

    public LoadPictureIntentService() {
        super(LoadPictureIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String[] extras = intent.getStringArrayExtra(EXTRA_KEY);
            if (extras != null) {
                for (String o : extras) {
                    try {
                        InputStream inputStream = (InputStream) new URL(o).getContent();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Thread.sleep(2000);
                        LocalBroadcastManager.getInstance(this)
                                .sendBroadcast(new Intent(ACTION_UPDATE).putExtra(EXTRA_KEY, bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(new Intent(ACTION_FINISH));
        }
    }
}
