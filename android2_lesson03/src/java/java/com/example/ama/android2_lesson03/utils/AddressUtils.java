package com.example.ama.android2_lesson03.utils;

import android.location.Address;

public class AddressUtils {
    private final static String SEPARATOR = ", ";

    public static String buildFullName(Address address) {
        StringBuilder fullLocationName = new StringBuilder();
        if (address != null) {
            int index = address.getMaxAddressLineIndex();
            for (int i = 0; i <= index; i++) {
                if (fullLocationName.length() != 0) fullLocationName.append(SEPARATOR);
                fullLocationName.append(address.getAddressLine(i));
            }
        }
        return fullLocationName.toString();
    }
}
