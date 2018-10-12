package com.example.ama.android2_lesson06.ui.base;

import android.database.Cursor;

public interface SmsPresenter<T extends SmsExampleView> {
    void attachView(T view);
    void detachView();
    void exportMessages(Cursor cursor);
    void importMessages();
}
