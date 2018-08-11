package com.example.ama.android2_lesson04.data;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Data {
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();

    public void setBitmaps(ArrayList<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public void addBitmap(Bitmap bitmap) {
        bitmaps.add(bitmap);
    }

    public void addBitmaps(ArrayList<Bitmap> newBitmaps) {
        bitmaps.addAll(newBitmaps);
    }

    public ArrayList<Bitmap> getBitmaps() {
        return bitmaps;
    }
}
