package com.example.ama.android2_lesson04.ui.viewer;

import android.content.Intent;

import com.example.ama.android2_lesson04.ServiceTestApp;
import com.example.ama.android2_lesson04.background.service.LoadPictureCombinedService;
import com.example.ama.android2_lesson04.background.service.ServiceConstants;
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment;
import com.example.ama.android2_lesson04.utils.ResourcesUtils;

public class PVStartedServiceFragment extends BaseReceiverFragment {

    public static PVStartedServiceFragment newInstance() {
        return new PVStartedServiceFragment();
    }

    @Override
    protected void onLoadImagesClick() {
        startMyService();
    }

    private void startMyService() {
        if (getActivity() != null) {
            Intent intent = new Intent(ServiceTestApp.getInstance(), LoadPictureCombinedService.class);
            intent.putExtra(ServiceConstants.EXTRA_KEY, ResourcesUtils.getUrlsArray());
            getActivity().startService(intent);
        }
    }
}
