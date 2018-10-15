package com.example.ama.android2_lesson06_2.ui.details;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.example.ama.android2_lesson06_2.model.DeviceInfoConverter;
import com.example.ama.android2_lesson06_2.model.DeviceParam;
import com.example.ama.android2_lesson06_2.ui.details.adapter.DetailsAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailsListFragment extends ListFragment {

    public static final String DEVICE_ARG = "device";

    private DeviceInfoConverter converter;

    public static DetailsListFragment newInstance(BluetoothDevice device) {
        Bundle args = new Bundle();
        args.putParcelable(DEVICE_ARG, device);
        DetailsListFragment fragment = new DetailsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        converter = new DeviceInfoConverter();
        if (getActivity() != null) {
            DetailsAdapter adapter = new DetailsAdapter(getActivity(), getFromArgs());
            setListAdapter(adapter);
        }
    }

    private List<DeviceParam> getFromArgs() {
        if (getArguments() != null) {
            BluetoothDevice device = getArguments().getParcelable(DEVICE_ARG);
            if (device != null) {
                return converter.getParams(device);
            }
        }
        return new ArrayList<>();
    }
}
