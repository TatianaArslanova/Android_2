package com.example.ama.android2_lesson01.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackNoteService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackNoteServiceFactory(getApplicationContext());
    }
}
