package com.example.ama.android2_lesson06;

import android.app.Application;

public class SmsExampleApp extends Application {
    private static SmsExampleApp instance;

    public static SmsExampleApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
