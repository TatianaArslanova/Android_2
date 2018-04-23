package com.example.ama.android2_lesson01.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson01.R;

public class DetailsNoteActivity extends AppCompatActivity implements DetailsNoteFragment.OnSaveNoteClickListener {
    public static final String DETAILS_NOTE_FRAGMENT = "details_note_fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_note);
        initFragments();
    }

    private void initFragments() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_details_note_fragment,
                        mFragmentManager.findFragmentByTag(DETAILS_NOTE_FRAGMENT) == null ?
                                DetailsNoteFragment.newInstance() :
                                mFragmentManager.findFragmentByTag(DETAILS_NOTE_FRAGMENT),
                        DETAILS_NOTE_FRAGMENT)
                .commit();
    }

    @Override
    public void sendResult() {
        setResult(RESULT_OK);
        finish();
    }
}
