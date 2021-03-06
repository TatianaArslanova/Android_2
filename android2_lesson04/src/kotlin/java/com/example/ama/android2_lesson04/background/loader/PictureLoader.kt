package com.example.ama.android2_lesson04.background.loader

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.AsyncTaskLoader
import com.example.ama.android2_lesson04.background.utils.LOADER_ARGS
import com.example.ama.android2_lesson04.background.utils.NetworkUtils
import java.io.IOException

class PictureLoader(context: Context, args: Bundle?) : AsyncTaskLoader<ArrayList<Bitmap>>(context) {
    private val urls: Array<String>? = args?.getStringArray(LOADER_ARGS)

    override fun loadInBackground(): ArrayList<Bitmap>? {
        val bitmaps = ArrayList<Bitmap>()
        try {
            if (urls != null) {
                for (o in urls) {
                    val bitmap = NetworkUtils.loadBitmapFromUrl(o)
                    if (bitmap != null) {
                        bitmaps.add(bitmap)
                    }
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