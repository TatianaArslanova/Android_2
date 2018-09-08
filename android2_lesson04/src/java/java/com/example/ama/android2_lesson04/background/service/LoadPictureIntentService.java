package com.example.ama.android2_lesson04.background.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ama.android2_lesson04.background.BackgroundConstants;
import com.example.ama.android2_lesson04.background.utils.NetworkUtils;

public class LoadPictureIntentService extends IntentService {

    public LoadPictureIntentService() {
        super(LoadPictureIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String[] extras = intent.getStringArrayExtra(BackgroundConstants.EXTRA_KEY);
            if (extras != null && extras.length > 0) {
                for (String o : extras) {
                    Bitmap bitmap = NetworkUtils.loadBitmapFromUrl(o);
                    if (bitmap != null) {
                        LocalBroadcastManager.getInstance(this)
                                .sendBroadcast(new Intent(BackgroundConstants.ACTION_UPDATE).putExtra(BackgroundConstants.EXTRA_KEY, bitmap));
                    }
                }
            }
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(new Intent(BackgroundConstants.ACTION_FINISH));
        }
    }
}
