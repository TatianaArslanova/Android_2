package com.example.ama.android2_lesson06.repo.converter;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.ama.android2_lesson06.R;
import com.example.ama.android2_lesson06.SmsExampleApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonMessageConverter {
    private static final String[] COL_NAMES = SmsExampleApp.getInstance().getResources()
            .getStringArray(R.array.SmsProviderColumnNames);
    private static final int[] COL_TYPES = SmsExampleApp.getInstance().getResources()
            .getIntArray(R.array.SmsProviderColumnTypes);

    public static String messagesToJson(Cursor cursor) {
        if (cursor.moveToFirst()) {
            int[] indexes = new int[COL_NAMES.length];
            for (int i = 0; i < COL_NAMES.length; i++) {
                indexes[i] = cursor.getColumnIndex(COL_NAMES[i]);
            }
            JSONArray jsonArray = new JSONArray();
            do {
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < COL_NAMES.length; i++) {
                    try {
                        switch (COL_TYPES[i]) {
                            case Cursor.FIELD_TYPE_STRING: {
                                jsonObject.put(COL_NAMES[i], cursor.getString(indexes[i]));
                                break;
                            }
                            case Cursor.FIELD_TYPE_INTEGER: {
                                jsonObject.put(COL_NAMES[i], cursor.getInt(indexes[i]));
                                break;
                            }
                        }
                        jsonObject.put(COL_NAMES[i], cursor.getString(indexes[i]));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArray.put(jsonObject);
            } while (cursor.moveToNext());
            return jsonArray.toString();
        }
        return null;
    }

    public static ContentValues[] jsonToContentValues(String json) {
        ContentValues[] values = null;
        try {
            JSONArray jsonArray = new JSONArray(json);
            values = new ContentValues[jsonArray.length()];
            for (int i = 0; i < values.length; i++) {
                values[i] = new ContentValues();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                for (int j = 0; j < COL_NAMES.length; j++) {
                    switch (COL_TYPES[j]) {
                        case Cursor.FIELD_TYPE_INTEGER: {
                            values[i].put(COL_NAMES[j], jsonObject.getInt(COL_NAMES[j]));
                            break;
                        }
                        case Cursor.FIELD_TYPE_STRING: {
                            values[i].put(COL_NAMES[j], jsonObject.getString(COL_NAMES[j]));
                            break;
                        }
                    }
                    values[i].put(COL_NAMES[j], jsonObject.getString(COL_NAMES[j]));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return values;
    }
}
