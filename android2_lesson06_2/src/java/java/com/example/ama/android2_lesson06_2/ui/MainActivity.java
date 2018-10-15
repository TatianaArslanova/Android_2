package com.example.ama.android2_lesson06_2.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.ama.android2_lesson06_2.R;
import com.example.ama.android2_lesson06_2.ui.list.DeviceListFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disposable = new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (!granted) {
                        Toast.makeText(this,
                                R.string.message_detecting_disabled,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        if (savedInstanceState == null) Navigator.placeDeviceListFragment(this);
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onDestroy();
    }
}