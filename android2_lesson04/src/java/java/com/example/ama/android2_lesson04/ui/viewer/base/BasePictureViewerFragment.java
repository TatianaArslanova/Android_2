package com.example.ama.android2_lesson04.ui.viewer.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ama.android2_lesson04.R;
import com.example.ama.android2_lesson04.ServiceTestApp;
import com.example.ama.android2_lesson04.background.service.LoadPictureIntentService;
import com.example.ama.android2_lesson04.ui.viewer.adapter.TestPagerAdapter;

import java.util.ArrayList;

public abstract class BasePictureViewerFragment extends Fragment {

    private static final String IS_LOADING = "isLoading";

    private TestPagerAdapter adapter;
    private ProgressBar progressBar;
    private BroadcastReceiver broadcastReceiver;
    private TextView tvCount;
    private Button btnLoadImages;
    private boolean isLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            ServiceTestApp.getData().setBitmaps(new ArrayList<Bitmap>());
        } else {
            isLoading = savedInstanceState.getBoolean(IS_LOADING);
        }
        return inflater.inflate(R.layout.fragment_picture_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.pb_progress);
        tvCount = view.findViewById(R.id.tv_count);
        btnLoadImages = view.findViewById(R.id.btn_load_images);
        ViewPager viewPager = view.findViewById(R.id.vp_main);
        adapter = new TestPagerAdapter(ServiceTestApp.getInstance(), ServiceTestApp.getData().getBitmaps());
        viewPager.setAdapter(adapter);
        setListeners();
        initReceiver();
        setLoadingCount();
        if (isLoading) showLoading();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setListeners() {
        btnLoadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onLoadImagesClick();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(IS_LOADING, isLoading);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        LocalBroadcastManager.getInstance(ServiceTestApp.getInstance()).unregisterReceiver(broadcastReceiver);
        super.onDestroyView();
    }

    private void initReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if (intent.getAction() != null) {
                        switch (intent.getAction()) {
                            case LoadPictureIntentService.ACTION_UPDATE: {
                                Bitmap bitmap = intent.getParcelableExtra(LoadPictureIntentService.EXTRA_KEY);
                                ServiceTestApp.getData().addBitmap(bitmap);
                                showPictures(ServiceTestApp.getData().getBitmaps());
                                break;
                            }
                            case LoadPictureIntentService.ACTION_FINISH: {
                                hideLoading();
                                break;
                            }
                        }
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(LoadPictureIntentService.ACTION_UPDATE);
        filter.addAction(LoadPictureIntentService.ACTION_FINISH);
        LocalBroadcastManager.getInstance(ServiceTestApp.getInstance())
                .registerReceiver(broadcastReceiver, filter);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        btnLoadImages.setEnabled(false);
        isLoading = true;
    }

    private void showPictures(ArrayList<Bitmap> bitmaps) {
        adapter.setBitmaps(bitmaps);
        setLoadingCount();
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        btnLoadImages.setEnabled(true);
        isLoading = false;
    }

    private void setLoadingCount() {
        tvCount.setText(getResources()
                .getString(R.string.fragment_picture_viewer_tv_count, adapter.getCount()));
    }

    protected abstract void onLoadImagesClick();
}
