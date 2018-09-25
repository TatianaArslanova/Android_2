package com.example.ama.android2_lesson03.utils

import android.location.Address
import java.lang.StringBuilder

object AddressUtils {

    fun buildFullName(address: Address?): String {
        val builder = StringBuilder()
        if (address != null) {
            for (i in 0..address.maxAddressLineIndex) {
                if (builder.isNotEmpty()) builder.append(",")
                builder.append(address.getAddressLine(i))
            }
        }
        return builder.toString()
    }
}