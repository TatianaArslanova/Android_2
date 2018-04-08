package com.example.ama.android2_lesson01;

import android.app.Application;

import com.example.ama.android2_lesson01.db.NotesDataManager;

public class NotesApp extends Application {
    private static NotesDataManager sDataManager;

    public static NotesDataManager getDataManager() {
        return sDataManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sDataManager = new NotesDataManager(getApplicationContext());
    }
}
