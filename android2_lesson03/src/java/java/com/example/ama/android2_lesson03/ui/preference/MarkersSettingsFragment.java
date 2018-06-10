package com.example.ama.android2_lesson03.ui.preference;

import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.ama.android2_lesson03.R;

public class MarkersSettingsFragment extends PreferenceFragmentCompat {

    public static MarkersSettingsFragment newInstance() {
        return new MarkersSettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}
