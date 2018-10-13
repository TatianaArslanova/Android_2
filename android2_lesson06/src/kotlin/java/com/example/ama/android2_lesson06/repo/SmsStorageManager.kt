package com.example.ama.android2_lesson06.repo

import android.database.Cursor
import android.net.Uri
import android.os.Environment
import com.example.ama.android2_lesson06.SmsExampleApp
import com.example.ama.android2_lesson06.repo.converter.JsonMessageConverter
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class SmsStorageManager {

    private val directory = File(
            Environment.getExternalStorageDirectory().absolutePath + "/SmsSaver")
    private val smsFile = File(directory, "SMS.txt")
    private val uri = Uri.parse("content://sms")

    fun startExport(cursor: Cursor?) {
        if (cursor == null) return
        val messages = JsonMessageConverter.messagesToJson(cursor)
        messages ?: return
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            writeToFile(messages)
        }
    }

    fun startImport() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val json = readFromFile()
            val values = JsonMessageConverter.jsonToContentValues(json)
            values ?: return
            SmsExampleApp.instance.contentResolver.bulkInsert(uri, values)
        }
    }

    private fun writeToFile(messages: String): Boolean {
        directory.mkdirs()
        try {
            val writer = smsFile.printWriter()
            writer.use { out -> out.print(messages) }
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    private fun readFromFile(): String {
        val stringBuilder = StringBuilder()
        try {
            val reader = FileInputStream(smsFile).bufferedReader()
            reader.useLines { sequence ->
                sequence.forEach { it -> stringBuilder.append(it) }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}