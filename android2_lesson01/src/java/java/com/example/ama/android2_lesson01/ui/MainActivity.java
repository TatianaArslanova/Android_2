package com.example.ama.android2_lesson01.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson01.NotesApp;
import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.db.NotesDataManager;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String LIST_OF_NOTES_FRAGMENT = "recycler_view_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
    }

    private void initFragments() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_container_list_of_notes,
                        mFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT) == null ?
                                ListOfNotesFragment.newInstance() :
                                mFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT),
                        LIST_OF_NOTES_FRAGMENT)
                .commit();
    }
}
