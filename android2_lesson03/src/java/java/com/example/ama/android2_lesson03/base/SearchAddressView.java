package com.example.ama.android2_lesson03.base;

import android.net.Uri;

public interface SearchAddressView {
    void showOnGMap(Uri uri);

    void showAddressText(String address);
}
