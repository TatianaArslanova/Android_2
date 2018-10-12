package com.example.ama.android2_lesson06.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.ama.android2_lesson06.SmsExampleApp;
import com.example.ama.android2_lesson06.repo.converter.JsonMessageConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SmsStorageManager {

    private final File DIRECTORY = new File(
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/SmsSaver");
    private final File SMS_FILE = new File(DIRECTORY, "SMS.txt");
    private final Uri URI = Uri.parse("content://sms");

    public void exportSmsToSdCard(Cursor cursor) {
        String messages = JsonMessageConverter.messagesToJson(cursor);
        if (messages == null) return;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            writeToFile(messages);
        } else {
            Log.d("ERROR", "storage not available");
        }
    }

    public void importSmsFromSdCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String json = readFromFile();
            ContentValues[] values = JsonMessageConverter.jsonToContentValues(json);
            if (values != null) {
                SmsExampleApp.getInstance().getContentResolver().bulkInsert(URI, values);
            }
        } else {
            Log.d("ERROR", "storage not available");
        }
    }

    private void writeToFile(String messages) {
        DIRECTORY.mkdirs();
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(SMS_FILE))) {
            writer.write(messages);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile() {
        StringBuilder stringBuilder = new StringBuilder();
        if (SMS_FILE.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(SMS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
