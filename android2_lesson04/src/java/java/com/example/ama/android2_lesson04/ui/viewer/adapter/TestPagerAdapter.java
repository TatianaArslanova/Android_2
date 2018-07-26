package com.example.ama.android2_lesson04.ui.viewer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ama.android2_lesson04.R;

import java.util.ArrayList;

public class TestPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Bitmap> bitmaps;

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
        notifyDataSetChanged();
    }

    public TestPagerAdapter(Context context, ArrayList<Bitmap> bitmaps) {
        super();
        this.context = context;
        this.bitmaps = bitmaps;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_pager_slide, container, false);
        ((ImageView) view.findViewById(R.id.iv_photo)).setImageBitmap(bitmaps.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
