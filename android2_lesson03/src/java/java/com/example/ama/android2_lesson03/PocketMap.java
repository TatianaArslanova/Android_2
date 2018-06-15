package com.example.ama.android2_lesson03;

import android.app.Application;

import com.example.ama.android2_lesson03.repo.SearchQueryManager;
import com.example.ama.android2_lesson03.repo.base.SearchManager;

public class PocketMap extends Application {
    private static PocketMap instance;
    private static SearchManager queryManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        queryManager = new SearchQueryManager();
    }

    public static PocketMap getInstance() {
        return instance;
    }

    public static SearchManager getQueryManager() {
        return queryManager;
    }
}
