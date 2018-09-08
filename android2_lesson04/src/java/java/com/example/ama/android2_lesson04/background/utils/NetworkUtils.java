package com.example.ama.android2_lesson04.background.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ama.android2_lesson04.background.BackgroundConstants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    public static Bitmap loadBitmapFromUrl(String url) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(BackgroundConstants.REQUEST_METHOD_GET);
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(BackgroundConstants.HTTP_ERROR_CODE_MESSAGE
                        + connection.getResponseCode());
            }
            inputStream = connection.getInputStream();
            if (inputStream != null) {
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            Thread.sleep(2000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return bitmap;
    }
}
