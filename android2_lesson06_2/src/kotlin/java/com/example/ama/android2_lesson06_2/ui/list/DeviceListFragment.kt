package com.example.ama.android2_lesson06_2.ui.list

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ama.android2_lesson06_2.R
import com.example.ama.android2_lesson06_2.ui.MainActivity
import com.example.ama.android2_lesson06_2.ui.Navigator
import com.example.ama.android2_lesson06_2.ui.list.adapter.DeviceListAdapter
import kotlinx.android.synthetic.main.fragment_device_list.*

class DeviceListFragment : Fragment() {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var adapter: DeviceListAdapter? = null

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action != null) {
                when (intent.action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice =
                                intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        adapter?.addDevice(device)
                    }
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED -> pb_progress.visibility = View.VISIBLE
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> pb_progress.visibility = View.GONE
                }
            }
        }
    }

    companion object {
        const val ENABLE_REQUEST_CODE = 1

        fun newInstance(): DeviceListFragment {
            return DeviceListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        retainInstance = true
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_device_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        setListeners()
        initBluetooth()
        initReceiver()
        if (bluetoothAdapter != null && bluetoothAdapter!!.isDiscovering) {
            pb_progress.visibility = View.VISIBLE
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(receiver)
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ENABLE_REQUEST_CODE)
            if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                s_bluetooth.isChecked = false
                btn_find_devices.isEnabled = false
            } else if (resultCode == AppCompatActivity.RESULT_OK) {
                btn_find_devices.isEnabled = true
            }
    }

    private fun initList() {
        if (activity != null) {
            if (adapter == null) {
                adapter = DeviceListAdapter(activity!!)
            }
            lv_devices.adapter = adapter
            lv_devices.setOnItemClickListener { adapterView, view, i, l ->
                if (activity != null) {
                    Navigator.placeDetailsListFragment(activity as MainActivity,
                            adapterView.getItemAtPosition(i) as BluetoothDevice)
                }
            }
        }
    }

    private fun setListeners() {
        btn_find_devices.setOnClickListener { v -> findDevices() }
        s_bluetooth.setOnCheckedChangeListener { _, checked ->
            if (checked) enableBluetooth() else disableBluetooth()
        }
    }

    private fun initBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            s_bluetooth.isEnabled = false
            btn_find_devices.isEnabled = false
        } else if (bluetoothAdapter!!.isEnabled) {
            s_bluetooth.isChecked = true
            btn_find_devices.isEnabled = true
        }
    }

    private fun initReceiver() {
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        activity?.registerReceiver(receiver, filter)
    }

    private fun enableBluetooth() {
        if (!bluetoothAdapter!!.isEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, ENABLE_REQUEST_CODE)
        }
    }

    private fun disableBluetooth() {
        if (bluetoothAdapter!!.isEnabled) {
            bluetoothAdapter?.disable()
            btn_find_devices.isEnabled = false
        }
    }

    private fun findDevices() {
        bluetoothAdapter?.startDiscovery()
        adapter?.clear()
    }
}