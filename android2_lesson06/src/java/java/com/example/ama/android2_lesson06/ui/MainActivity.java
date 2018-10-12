package com.example.ama.android2_lesson06.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.ama.android2_lesson06.R;
import com.example.ama.android2_lesson06.repo.SmsStorageManager;
import com.example.ama.android2_lesson06.ui.adapter.SmsCursorAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private SmsCursorAdapter adapter;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disposable = new RxPermissions(this)
                .request(Manifest.permission.READ_SMS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        tuneList();
                        setListeners();
                    }
                });
    }

    private void tuneList() {
        adapter = new SmsCursorAdapter(this);
        adapter.initLoader();
        ((ListView) findViewById(R.id.lv_sms_list)).setAdapter(adapter);
    }

    private void setListeners() {

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
