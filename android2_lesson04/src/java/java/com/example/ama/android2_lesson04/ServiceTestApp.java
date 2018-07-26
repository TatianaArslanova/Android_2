package com.example.ama.android2_lesson04;

import android.app.Application;

import com.example.ama.android2_lesson04.data.Data;

public class ServiceTestApp extends Application {
    private static ServiceTestApp instance;
    private static Data data;

    public static ServiceTestApp getInstance() {
        return instance;
    }

    public static Data getData() {
        return data;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        data = new Data();
    }
}
