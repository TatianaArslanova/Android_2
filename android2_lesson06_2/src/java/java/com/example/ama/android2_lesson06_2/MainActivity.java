package com.example.ama.android2_lesson06_2;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private final static int ENABLE_REQUEST_CODE = 1;

    private Switch bluetoothSwitch;
    private Button buttonFind;

    private BluetoothAdapter bluetoothAdapter;
    private DeviceListAdapter adapter;
    private BroadcastReceiver receiver;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disposable = new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        initUI();
                        initBluetooth();
                        initReceiver();
                    } else {
                        buttonFind.setEnabled(false);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ENABLE_REQUEST_CODE)
            if (resultCode == RESULT_CANCELED) {
                bluetoothSwitch.setChecked(false);
                buttonFind.setEnabled(false);
            } else if (resultCode == RESULT_OK) {
                buttonFind.setEnabled(true);
            }
    }

    private void initUI() {
        buttonFind = findViewById(R.id.btn_find_devices);
        buttonFind.setOnClickListener(view -> findDevices());
        bluetoothSwitch = findViewById(R.id.s_bluetooth);
        bluetoothSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) enableBluetooth();
            else disableBluetooth();
        });
        adapter = new DeviceListAdapter(this);
        ((ListView) findViewById(R.id.lv_devices)).setAdapter(adapter);
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
                            adapter.addDevice(device.getName());
                            break;
                        case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                            Log.d("ON_RECEIVE", "DISCOVERY_STARTED");
                            break;
                        case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                            Log.d("ON_RECEIVE", "DISCOVERY_FINISHED");
                            break;
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onDestroy();
    }
}