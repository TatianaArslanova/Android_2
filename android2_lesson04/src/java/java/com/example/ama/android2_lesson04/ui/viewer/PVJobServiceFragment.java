package com.example.ama.android2_lesson04.ui.viewer;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ama.android2_lesson04.background.BackgroundConstants;
import com.example.ama.android2_lesson04.background.service.LoadPictureJobService;
import com.example.ama.android2_lesson04.ui.viewer.base.BaseReceiverFragment;
import com.example.ama.android2_lesson04.utils.ResourcesUtils;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class PVJobServiceFragment extends BaseReceiverFragment {
    private FirebaseJobDispatcher dispatcher;

    public static PVJobServiceFragment newInstance() {
        return new PVJobServiceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getContext()));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onLoadImagesClick() {
        scheduleLoadingImagesJob();
    }

    private void scheduleLoadingImagesJob() {
        Bundle bundle = new Bundle();
        bundle.putStringArray(BackgroundConstants.EXTRA_KEY, ResourcesUtils.getUrlsArray());
        Job job = dispatcher.newJobBuilder()
                .setService(LoadPictureJobService.class)
                .setExtras(bundle)
                .setTrigger(Trigger.NOW)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setReplaceCurrent(false)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTag(LoadPictureJobService.class.getSimpleName())
                .build();
        dispatcher.schedule(job);
    }

    @Override
    public void onDestroy() {
        dispatcher.cancel(LoadPictureJobService.class.getSimpleName());
        super.onDestroy();
    }
}
