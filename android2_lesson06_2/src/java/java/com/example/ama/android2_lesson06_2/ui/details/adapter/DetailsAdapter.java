package com.example.ama.android2_lesson06_2.ui.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ama.android2_lesson06_2.model.DeviceParam;

import java.util.List;

public class DetailsAdapter extends ArrayAdapter<DeviceParam> {
    private List<DeviceParam> params;

    public DetailsAdapter(@NonNull Context context, List<DeviceParam> params) {
        super(context, android.R.layout.simple_list_item_2, params);
        this.params = params;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1))
                .setText(params.get(position).getParamName());
        ((TextView) convertView.findViewById(android.R.id.text2))
                .setText(params.get(position).getParamValue());
        return convertView;
    }
}
