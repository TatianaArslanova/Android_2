package com.example.ama.android2_lesson04.ui.viewer.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.ama.android2_lesson04.R

class TestPagerAdapter private constructor(private val context: Context)
    : PagerAdapter() {

    var bitmaps = ArrayList<Bitmap>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    constructor(context: Context, bitmaps: ArrayList<Bitmap>) : this(context) {
        this.bitmaps = bitmaps
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.view_pager_slide, container, false)
        view.findViewById<ImageView>(R.id.iv_photo).setImageBitmap(bitmaps[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any) = view === `object`

    override fun getCount() = bitmaps.size
}