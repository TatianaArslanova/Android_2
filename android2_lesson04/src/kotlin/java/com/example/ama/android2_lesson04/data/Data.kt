package com.example.ama.android2_lesson04.data

import android.graphics.Bitmap

class Data {
    var bitmaps = ArrayList<Bitmap>()
    fun addBitmap(bitmap: Bitmap) {
        bitmaps.add(bitmap)
    }
}