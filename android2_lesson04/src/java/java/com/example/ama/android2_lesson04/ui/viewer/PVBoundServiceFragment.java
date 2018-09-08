package com.example.ama.android2_lesson04.ui.viewer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.ama.android2_lesson04.ServiceTestApp;
import com.example.ama.android2_lesson04.background.service.LoadPictureCombinedService;
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment;
import com.example.ama.android2_lesson04.utils.ResourcesUtils;

public class PVBoundServiceFragment extends BaseReceiverFragment {

    private boolean isBound;
    private LoadPictureCombinedService.MyBinder binder;
    private PVServiceConnection serviceConnection;

    public static PVBoundServiceFragment newInstance() {
        return new PVBoundServiceFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        serviceConnection = new PVServiceConnection();
        bindService();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onLoadImagesClick() {
        if (isBound) {
            binder.sendUrls(ResourcesUtils.getUrlsArray());
        }
    }

    private void bindService() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), LoadPictureCombinedService.class);
            ServiceTestApp.getInstance().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onDestroyView() {
        ServiceTestApp.getInstance().unbindService(serviceConnection);
        super.onDestroyView();
    }

    private class PVServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (LoadPictureCombinedService.MyBinder) service;
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binder = null;
            isBound = false;
        }

    }
}
