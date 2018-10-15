package com.example.ama.android2_lesson06.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Environment;

import com.example.ama.android2_lesson06.R;
import com.example.ama.android2_lesson06.SmsExampleApp;
import com.example.ama.android2_lesson06.repo.converter.JsonMessageConverter;
import com.example.ama.android2_lesson06.utils.ResUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class SmsStorageManager {

    private final File DIRECTORY = new File(
            Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/SmsSaver");
    private final File SMS_FILE = new File(DIRECTORY, "SMS.txt");

    public Single<String> startExport(Cursor cursor) {
        return Single.fromCallable(() -> exportSmsToSdCard(cursor))
                .subscribeOn(Schedulers.io());
    }

    public Single<String> startImport() {
        return Single.fromCallable(this::importSmsFromSdCard)
                .subscribeOn(Schedulers.io());
    }

    private String exportSmsToSdCard(Cursor cursor) {
        String messages = JsonMessageConverter.messagesToJson(cursor);
        if (messages == null) return ResUtils.getString(R.string.message_export_error);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (writeToFile(messages)) return ResUtils.getString(R.string.message_success);
            else return ResUtils.getString(R.string.message_export_error);
        }
        return ResUtils.getString(R.string.message_no_sd_card);
    }

    private String importSmsFromSdCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String json = readFromFile();
            ContentValues[] values = JsonMessageConverter.jsonToContentValues(json);
            if (values != null) {
                if (SmsExampleApp.getInstance().getContentResolver().bulkInsert(SmsConstants.URI, values) == values.length) {
                    return ResUtils.getString(R.string.message_success);
                }
            } else {
                return ResUtils.getString(R.string.message_import_error);
            }
        }
        return ResUtils.getString(R.string.message_no_sd_card);
    }

    private boolean writeToFile(String messages) {
        DIRECTORY.mkdirs();
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(SMS_FILE))) {
            writer.write(messages);
            writer.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
