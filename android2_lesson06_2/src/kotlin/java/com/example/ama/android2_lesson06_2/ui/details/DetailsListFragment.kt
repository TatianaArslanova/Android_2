package com.example.ama.android2_lesson06_2.ui.details

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.support.v4.app.ListFragment
import com.example.ama.android2_lesson06_2.model.DeviceInfoConverter
import com.example.ama.android2_lesson06_2.model.DeviceParam
import com.example.ama.android2_lesson06_2.ui.details.adapter.DetailsAdapter

class DetailsListFragment : ListFragment() {

    var adapter: DetailsAdapter? = null

    companion object {
        const val DEVICE_ARG = "device"

        fun newInstance(arg: BluetoothDevice): DetailsListFragment {
            val args = Bundle()
            args.putParcelable(DEVICE_ARG, arg)
            val fragment = DetailsListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            adapter = DetailsAdapter(activity!!, getFromArgs())
            listAdapter = adapter
        }
    }

    private fun getFromArgs(): List<DeviceParam> {
        if (arguments != null) {
            val device: BluetoothDevice? = arguments!!.getParcelable(DEVICE_ARG)
            if (device != null) return DeviceInfoConverter.getParams(device)
        }
        return ArrayList()
    }
}