package com.example.ama.android2_lesson04.ui.start;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ama.android2_lesson04.R;
import com.example.ama.android2_lesson04.ui.MainActivity;
import com.example.ama.android2_lesson04.ui.viewer.PVAsynkTaskFragment;
import com.example.ama.android2_lesson04.ui.viewer.PVBoundServiceFragment;
import com.example.ama.android2_lesson04.ui.viewer.PVIntentServiceFragment;
import com.example.ama.android2_lesson04.ui.viewer.PVLoaderFragment;
import com.example.ama.android2_lesson04.ui.viewer.PVRxJavaFragment;
import com.example.ama.android2_lesson04.ui.viewer.PVStartedServiceFragment;

public class StartFragment extends Fragment {

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        addListeners(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void addListeners(View view) {
        view.findViewById(R.id.btn_intent_service)
                .setOnClickListener(v -> startFragment(PVIntentServiceFragment.newInstance()));
        view.findViewById(R.id.btn_start_service)
                .setOnClickListener(v -> startFragment(PVStartedServiceFragment.newInstance()));
        view.findViewById(R.id.btn_asynk_task)
                .setOnClickListener(v -> startFragment(PVAsynkTaskFragment.newInstance()));
        view.findViewById(R.id.btn_loader)
                .setOnClickListener(v -> startFragment(PVLoaderFragment.newInstance()));
        view.findViewById(R.id.btn_bind_service)
                .setOnClickListener(v -> startFragment(PVBoundServiceFragment.newInstance()));
        view.findViewById(R.id.btn_rx_java)
                .setOnClickListener(v -> startFragment(PVRxJavaFragment.newInstance()));
    }

    private void startFragment(Fragment fragment) {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).replaceFragment(fragment, true);
        }
    }
}
