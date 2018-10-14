package com.example.ama.android2_lesson06.repo

import android.database.Cursor
import android.net.Uri
import android.os.Environment
import com.example.ama.android2_lesson06.R
import com.example.ama.android2_lesson06.SmsExampleApp
import com.example.ama.android2_lesson06.repo.converter.JsonMessageConverter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class SmsStorageManager {

    private val directory = File(
            Environment.getExternalStorageDirectory().absolutePath + "/SmsSaver")
    private val smsFile = File(directory, "SMS.txt")
    private val uri = Uri.parse("content://sms")

    fun startExport(cursor: Cursor?): Single<String> =
            Single.fromCallable { exportSmsToSdCard(cursor) }
                    .subscribeOn(Schedulers.io())

    fun startImport(): Single<String> =
            Single.fromCallable { importSmsFromCdCard() }
                    .subscribeOn(Schedulers.io())

    private fun exportSmsToSdCard(cursor: Cursor?): String {
        if (cursor == null) {
            return SmsExampleApp.instance.resources.getString(R.string.message_export_error)
        }
        val messages = JsonMessageConverter.messagesToJson(cursor)
        messages ?: return SmsExampleApp.instance.resources.getString(R.string.message_export_error)
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            if (writeToFile(messages)) {
                return SmsExampleApp.instance.resources.getString(R.string.message_success)
            }
        }
        return SmsExampleApp.instance.resources.getString(R.string.message_no_sd_card)
    }

    private fun importSmsFromCdCard(): String {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val json = readFromFile()
            val values = JsonMessageConverter.jsonToContentValues(json)
            values
                    ?: return SmsExampleApp.instance.resources.getString(R.string.message_import_error)
            return if (SmsExampleApp.instance.contentResolver.bulkInsert(uri, values) == values.size) {
                SmsExampleApp.instance.resources.getString(R.string.message_success)
            } else {
                SmsExampleApp.instance.resources.getString(R.string.message_import_error)
            }
        }
        return SmsExampleApp.instance.resources.getString(R.string.message_no_sd_card)
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