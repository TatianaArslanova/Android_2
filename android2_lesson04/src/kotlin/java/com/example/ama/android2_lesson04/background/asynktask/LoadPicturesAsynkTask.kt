package com.example.ama.android2_lesson04.background.asynktask

import android.graphics.Bitmap
import android.os.AsyncTask
import com.example.ama.android2_lesson04.background.utils.NetworkUtils
import java.io.IOException

class LoadPicturesAsynkTask(private var onFinishListener: (() -> Unit)?,
                            private var onUpdateListener: ((bitmap: Bitmap) -> Unit)?)
    : AsyncTask<String, Bitmap, ArrayList<Bitmap>>() {

    override fun doInBackground(vararg params: String?): ArrayList<Bitmap> {
        val bitmaps = ArrayList<Bitmap>()
        try {
            for (o in params) {
                if (isCancelled) return bitmaps
                if (o != null) {
                    val bitmap = NetworkUtils.loadBitmapFromUrl(o)
                    if (bitmap != null) {
                        bitmaps.add(bitmap)
                        publishProgress(bitmap)
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

    override fun onProgressUpdate(vararg values: Bitmap) {
        onUpdateListener?.invoke(values[0])
        super.onProgressUpdate(*values)
    }

    override fun onPostExecute(result: ArrayList<Bitmap>?) {
        onFinishListener?.invoke()
        super.onPostExecute(result)
    }

    fun removeListeners() {
        onFinishListener = null
        onUpdateListener = null
    }

    fun setListeners(onFinishListener: (() -> Unit)?, onUpdateListener: ((bitmap: Bitmap) -> Unit)?) {
        this.onFinishListener = onFinishListener
        this.onUpdateListener = onUpdateListener
    }
}