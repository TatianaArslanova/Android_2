package com.example.ama.android2_lesson01;

import android.app.Application;

import com.example.ama.android2_lesson01.db.NotesDataManager;

public class NotesApp extends Application {
    private static NotesDataManager sDataManager;
    private static NotesApp instance;

    public static NotesDataManager getDataManager() {
        return sDataManager;
    }

    public static NotesApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sDataManager = new NotesDataManager(getApplicationContext());
    }
}
