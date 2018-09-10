package com.example.ama.android2_lesson04.background.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.ama.android2_lesson04.ServiceTestApp
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtils {

    fun loadBitmapFromUrl(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        if (isNetworkConnected()) {
            var connection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            try {
                connection = URL(url).openConnection() as HttpURLConnection
                connection.doInput = true
                connection.requestMethod = REQUEST_METHOD_GET
                connection.connect()
                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    throw IOException(HTTP_ERROR_CODE_MESSAGE+connection.responseCode)
                }
                inputStream = connection.inputStream
                if (inputStream != null) {
                    bitmap = BitmapFactory.decodeStream(inputStream)
                }
                Thread.sleep(2000)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                connection?.disconnect()
            }
        }
        return bitmap
    }

    private fun isNetworkConnected(): Boolean {
        val manager: ConnectivityManager? = ServiceTestApp.instance
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info: NetworkInfo? = manager?.activeNetworkInfo
        return info!=null && info.isConnected
    }
}