package com.example.ama.android2_lesson04.background.loader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.content.AsyncTaskLoader
import java.io.IOException
import java.io.InputStream
import java.net.URL

const val LOADER_ARGS = "Loader args"

class PictureLoader(context: Context, args: Bundle?) : AsyncTaskLoader<ArrayList<Bitmap>>(context) {
    val urls = args?.getStringArray(LOADER_ARGS)

    override fun loadInBackground(): ArrayList<Bitmap>? {
        val bitmaps = ArrayList<Bitmap>()
        try {
            if (urls != null) {
                for (o in urls) {
                    val inputStream = URL(o).content as InputStream
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    Thread.sleep(2000)
                    bitmaps.add(bitmap)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return bitmaps
    }

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }
}