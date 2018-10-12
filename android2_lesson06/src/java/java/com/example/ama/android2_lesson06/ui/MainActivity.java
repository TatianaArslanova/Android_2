package com.example.ama.android2_lesson06.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ama.android2_lesson06.R;
import com.example.ama.android2_lesson06.ui.adapter.SmsCursorAdapter;
import com.example.ama.android2_lesson06.ui.base.SmsExampleView;
import com.example.ama.android2_lesson06.ui.base.SmsPresenter;
import com.example.ama.android2_lesson06.ui.mvp.SmsExamplePresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements SmsExampleView {
    private SmsCursorAdapter adapter;
    private SmsPresenter<SmsExampleView> presenter;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new SmsExamplePresenter<>();
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

    @Override
    protected void onStart() {
        presenter.attachView(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
    }

    private void tuneList() {
        adapter = new SmsCursorAdapter(this);
        adapter.initLoader();
        ((ListView) findViewById(R.id.lv_sms_list)).setAdapter(adapter);
    }

    private void setListeners() {
        findViewById(R.id.btn_export).setOnClickListener(view -> presenter.exportMessages(adapter.getCursor()));
        findViewById(R.id.btn_import).setOnClickListener(view -> presenter.importMessages());
    }

    @Override
    protected void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
