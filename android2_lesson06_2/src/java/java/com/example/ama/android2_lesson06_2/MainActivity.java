package com.example.ama.android2_lesson06_2;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private final static int ENABLE_REQUEST_CODE = 1;
    private Switch bluetoothSwitch;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initBluetooth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ENABLE_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            bluetoothSwitch.setChecked(false);
        }
    }


    private void initUI(){
        bluetoothSwitch = findViewById(R.id.s_bluetooth);
        bluetoothSwitch.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (checked) enableBluetooth();
            else disableBluetooth();
        });
    }

    private void initBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            bluetoothSwitch.setEnabled(false);
        } else if (bluetoothAdapter.isEnabled()) {
            bluetoothSwitch.setChecked(true);
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
        }
    }
}