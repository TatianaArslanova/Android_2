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

    public LoadPictureIntentService() {
        super(LoadPictureIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String[] extras = intent.getStringArrayExtra(ServiceConstants.EXTRA_KEY);
            if (extras != null) {
                for (String o : extras) {
                    try {
                        InputStream inputStream = (InputStream) new URL(o).getContent();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Thread.sleep(2000);
                        LocalBroadcastManager.getInstance(this)
                                .sendBroadcast(new Intent(ServiceConstants.ACTION_UPDATE).putExtra(ServiceConstants.EXTRA_KEY, bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(new Intent(ServiceConstants.ACTION_FINISH));
        }
    }
}
