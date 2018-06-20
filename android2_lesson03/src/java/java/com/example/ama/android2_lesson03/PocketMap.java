package com.example.ama.android2_lesson03;

import android.app.Application;

public class PocketMap extends Application {
    private static PocketMap instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static PocketMap getInstance() {
        return instance;
    }
}
