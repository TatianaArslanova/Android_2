package com.example.ama.android2_lesson06.repo.converter

import android.content.ContentValues
import android.database.Cursor
import com.example.ama.android2_lesson06.R
import com.example.ama.android2_lesson06.SmsExampleApp
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object JsonMessageConverter {
    private val colNames: Array<String> = SmsExampleApp.instance.resources
            .getStringArray(R.array.SmsProviderColumnNames)
    private val colTypes: IntArray = SmsExampleApp.instance.resources
            .getIntArray(R.array.SmsProviderColumnTypes)

    fun messagesToJson(cursor: Cursor): String? {
        if (cursor.moveToFirst()) {
            val indexes = Array(colNames.size) { i -> cursor.getColumnIndex(colNames[i]) }
            val jsonArray = JSONArray()
            do {
                val jsonObject = JSONObject()
                for ((i, name) in colNames.withIndex()) {
                    try {
                        jsonObject.put(name,
                                when (colTypes[i]) {
                                    Cursor.FIELD_TYPE_STRING -> cursor.getString(indexes[i])
                                    else -> cursor.getInt(indexes[i])
                                }
                        )
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                jsonArray.put(jsonObject)
            } while (cursor.moveToNext())
            return jsonArray.toString()
        }
        return null
    }

    fun jsonToContentValues(json: String): Array<ContentValues>? {
        var values: Array<ContentValues>? = null
        try {
            val jsonArray = JSONArray(json)
            values = Array(jsonArray.length()) { index ->
                val jsonObject = jsonArray.getJSONObject(index)
                val contentValues = ContentValues()
                for ((i, name) in colNames.withIndex()) {
                    when (colTypes[i]) {
                        Cursor.FIELD_TYPE_STRING ->
                            contentValues.put(name, jsonObject.getString(name))
                        else -> contentValues.put(name, jsonObject.getInt(name))
                    }
                }
                contentValues
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return values
    }
}