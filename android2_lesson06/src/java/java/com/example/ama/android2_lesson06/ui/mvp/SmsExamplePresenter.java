package com.example.ama.android2_lesson06.ui.mvp;

import android.database.Cursor;

import com.example.ama.android2_lesson06.ui.base.BaseSmsPresenter;
import com.example.ama.android2_lesson06.ui.base.SmsExampleView;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class SmsExamplePresenter<T extends SmsExampleView> extends BaseSmsPresenter<T> {

    @Override
    public void exportMessages(Cursor cursor) {
        disposable.add(manager.startExport(cursor)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (view != null) view.showMessage(s);
                }));
    }

    @Override
    public void importMessages() {
        disposable.add(manager.startImport()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (view != null) view.showMessage(s);
                }));
    }
}
