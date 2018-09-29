package com.example.ama.android2_lesson03.widget.model

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Address
import android.os.Build
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.utils.AddressUtils
import com.example.ama.android2_lesson03.utils.ResourceUtils
import com.example.ama.android2_lesson03.widget.LocationWidgetService
import com.google.android.gms.maps.model.LatLng

class WidgetModelFactory(val context: Context) {

    companion object {
        private const val REQUEST_CODE = 1
    }

    fun fullModel(latLng: LatLng, address: Address?) =
            WidgetModel(
                    ResourceUtils.formatLatLngCoordinates(latLng),
                    AddressUtils.buildFullName(address),
                    buildPendingIntent())

    fun permissionRequiredModel() =
            WidgetModel(
                    context.resources.getString(R.string.message_location_not_found),
                    context.resources.getString(R.string.message_permission_required),
                    buildPendingIntent()
            )

    private fun buildPendingIntent(): PendingIntent {
        val intent = Intent(context, LocationWidgetService::class.java)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PendingIntent.getForegroundService(context, REQUEST_CODE, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            PendingIntent.getService(context, REQUEST_CODE, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
}