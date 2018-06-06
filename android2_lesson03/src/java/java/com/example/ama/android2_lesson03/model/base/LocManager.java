package com.example.ama.android2_lesson03.model.base;

import android.content.Context;
import android.location.Address;

public interface LocManager {
    Address findAddressByQuery(Context context, String query);

}
