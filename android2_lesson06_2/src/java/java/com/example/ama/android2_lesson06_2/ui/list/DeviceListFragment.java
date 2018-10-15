package com.example.ama.android2_lesson06_2.ui.list;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.example.ama.android2_lesson06_2.R;
import com.example.ama.android2_lesson06_2.ui.MainActivity;
import com.example.ama.android2_lesson06_2.ui.Navigator;
import com.example.ama.android2_lesson06_2.ui.list.adapter.DeviceListAdapter;

public class DeviceListFragment extends Fragment {
    private final static int ENABLE_REQUEST_CODE = 1;

    private Switch bluetoothSwitch;
    private Button buttonFind;
    private ProgressBar progressBar;

    private BluetoothAdapter bluetoothAdapter;
    private DeviceListAdapter adapter;
    private BroadcastReceiver receiver;

    public static DeviceListFragment newInstance() {
        return new DeviceListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_device_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        initBluetooth();
        initReceiver();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ENABLE_REQUEST_CODE)
            if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                bluetoothSwitch.setChecked(false);
                buttonFind.setEnabled(false);
            } else if (resultCode == AppCompatActivity.RESULT_OK) {
                buttonFind.setEnabled(true);
            }
    }

    private void initUI(View view) {
        progressBar = view.findViewById(R.id.pb_progress);
        buttonFind = view.findViewById(R.id.btn_find_devices);
        buttonFind.setOnClickListener(v -> findDevices());
        bluetoothSwitch = view.findViewById(R.id.s_bluetooth);
        bluetoothSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) enableBluetooth();
            else disableBluetooth();
        });
        if (getActivity() != null) {
            adapter = new DeviceListAdapter(getActivity());
            ListView listView = view.findViewById(R.id.lv_devices);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener((adapterView, view1, i, l) ->
                    Navigator.plaseDetailsListFragment(
                            (MainActivity) getActivity(),
                            (BluetoothDevice) adapterView.getItemAtPosition(i)));
        }
    }

    private void initBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            bluetoothSwitch.setEnabled(false);
            buttonFind.setEnabled(false);
        } else if (bluetoothAdapter.isEnabled()) {
            bluetoothSwitch.setChecked(true);
            buttonFind.setEnabled(true);
        }
    }

    private void enableBluetooth() {
        if (!bluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, ENABLE_REQUEST_CODE);
        }
    }

    private void disableBluetooth() {
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            buttonFind.setEnabled(false);
        }
    }

    private void findDevices() {
        bluetoothAdapter.startDiscovery();
        adapter.clear();
    }

    private void initReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null) {
                    switch (intent.getAction()) {
                        case BluetoothDevice.ACTION_FOUND:
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            adapter.addDevice(device);
                            break;
                        case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                            progressBar.setVisibility(View.GONE);
                            break;
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        if (getActivity() != null) {
            getActivity().registerReceiver(receiver, filter);
        }
    }

    @Override
    public void onDestroy() {
        if (getActivity() != null) {
            getActivity().unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}
