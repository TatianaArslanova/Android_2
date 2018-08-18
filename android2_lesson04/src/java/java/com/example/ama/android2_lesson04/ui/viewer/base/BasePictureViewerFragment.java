package com.example.ama.android2_lesson04.ui.viewer.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ama.android2_lesson04.R;
import com.example.ama.android2_lesson04.ServiceTestApp;
import com.example.ama.android2_lesson04.ui.viewer.adapter.TestPagerAdapter;

import java.util.ArrayList;

public abstract class BasePictureViewerFragment extends Fragment {

    private static final String IS_LOADING = "isLoading";
    protected boolean isLoading;

    private TestPagerAdapter adapter;
    private ProgressBar progressBar;
    private TextView tvCount;
    private Button btnLoadImages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUi(view);
        setListeners();
        if (savedInstanceState == null) {
            ServiceTestApp.getData().setBitmaps(new ArrayList<Bitmap>());
        } else {
            setLoading(savedInstanceState.getBoolean(IS_LOADING));
        }
        setLoadingCount();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(IS_LOADING, isLoading);
        super.onSaveInstanceState(outState);
    }

    protected void showPictures(ArrayList<Bitmap> bitmaps) {
        adapter.setBitmaps(bitmaps);
        setLoadingCount();
    }

    protected void onUpdateLoading(Bitmap bitmap) {
        ServiceTestApp.getData().addBitmap(bitmap);
        showPictures(ServiceTestApp.getData().getBitmaps());
    }

    protected void onFinishLoading() {
        setLoading(false);
    }

    private void initUi(View view) {
        progressBar = view.findViewById(R.id.pb_progress);
        tvCount = view.findViewById(R.id.tv_count);
        btnLoadImages = view.findViewById(R.id.btn_load_images);
        ViewPager viewPager = view.findViewById(R.id.vp_main);
        adapter = new TestPagerAdapter(ServiceTestApp.getInstance(), ServiceTestApp.getData().getBitmaps());
        viewPager.setAdapter(adapter);
    }

    private void setListeners() {
        btnLoadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoading(true);
                onLoadImagesClick();
            }
        });
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        btnLoadImages.setEnabled(!isLoading);
    }

    private void setLoadingCount() {
        tvCount.setText(getResources()
                .getString(R.string.fragment_picture_viewer_tv_count, adapter.getCount()));
    }

    protected abstract void onLoadImagesClick();
}
