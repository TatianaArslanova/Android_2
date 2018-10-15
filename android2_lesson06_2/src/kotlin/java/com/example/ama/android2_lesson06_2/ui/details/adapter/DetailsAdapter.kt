package com.example.ama.android2_lesson06_2.ui.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.ama.android2_lesson06_2.model.DeviceParam

class DetailsAdapter(context: Context, val param: List<DeviceParam>)
    : ArrayAdapter<DeviceParam>(context, android.R.layout.simple_list_item_2, param) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_2, parent, false)
        }
        view!!.findViewById<TextView>(android.R.id.text1).text = param[position].name
        view.findViewById<TextView>(android.R.id.text2).text = param[position].value
        return view
    }
}