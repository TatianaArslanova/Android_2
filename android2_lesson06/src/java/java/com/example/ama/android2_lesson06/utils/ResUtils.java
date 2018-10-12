package com.example.ama.android2_lesson06.utils;

import com.example.ama.android2_lesson06.SmsExampleApp;

public class ResUtils {

    public static String getString(int id) {
        return SmsExampleApp.getInstance().getResources().getString(id);
    }

    public static int[] getIntArray(int id) {
        return SmsExampleApp.getInstance().getResources().getIntArray(id);
    }

    public static String[] getStringArray(int id) {
        return SmsExampleApp.getInstance().getResources().getStringArray(id);
    }
}
