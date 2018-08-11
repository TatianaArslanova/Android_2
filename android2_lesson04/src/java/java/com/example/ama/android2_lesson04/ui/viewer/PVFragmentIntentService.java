package com.example.ama.android2_lesson04.ui.viewer;

import android.content.Intent;

import com.example.ama.android2_lesson04.background.service.LoadPictureIntentService;
import com.example.ama.android2_lesson04.background.service.ServiceConstants;
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment;
import com.example.ama.android2_lesson04.utils.ResourcesUtils;

public class PVFragmentIntentService extends BaseReceiverFragment {

    public static PVFragmentIntentService newInstance() {
        return new PVFragmentIntentService();
    }

    @Override
    protected void onLoadImagesClick() {
        startIntentService();
    }

    private void startIntentService() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), LoadPictureIntentService.class);
            intent.putExtra(ServiceConstants.EXTRA_KEY, ResourcesUtils.getUrlsArray());
            getActivity().startService(intent);
        }
    }
}
