package com.example.ama.android2_lesson04.ui.viewer.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.example.ama.android2_lesson04.ServiceTestApp;
import com.example.ama.android2_lesson04.background.service.ServiceConstants;

public abstract class BaseReceiverFragment extends BasePictureViewerFragment {

    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initReceiver();
        super.onViewCreated(view, savedInstanceState);
    }

    private void initReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if (intent.getAction() != null) {
                        switch (intent.getAction()) {
                            case ServiceConstants.ACTION_UPDATE: {
                                Bitmap bitmap = intent.getParcelableExtra(ServiceConstants.EXTRA_KEY);
                                onUpdateLoading(bitmap);
                                break;
                            }
                            case ServiceConstants.ACTION_FINISH: {
                                onFinishLoading();
                                break;
                            }
                        }
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceConstants.ACTION_UPDATE);
        filter.addAction(ServiceConstants.ACTION_FINISH);
        LocalBroadcastManager.getInstance(ServiceTestApp.getInstance())
                .registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onDestroyView() {
        LocalBroadcastManager.getInstance(ServiceTestApp.getInstance()).unregisterReceiver(broadcastReceiver);
        super.onDestroyView();
    }

}
