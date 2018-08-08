package com.example.ama.android2_lesson04.ui.viewer;

import android.content.Intent;

import com.example.ama.android2_lesson04.R;
import com.example.ama.android2_lesson04.background.service.LoadPictureIntentService;
import com.example.ama.android2_lesson04.background.service.ServiceConstants;
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment;

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
            intent.putExtra(ServiceConstants.EXTRA_KEY, getResources().getStringArray(R.array.image_urls));
            getActivity().startService(intent);
        }
    }
}
