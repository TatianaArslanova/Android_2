package com.example.ama.android2_lesson04.utils;

import com.example.ama.android2_lesson04.R;
import com.example.ama.android2_lesson04.ServiceTestApp;

public class ResourcesUtils {

    public static String[] getUrlsArray() {
        return ServiceTestApp.getInstance().getResources().getStringArray(R.array.image_urls);
    }
}
