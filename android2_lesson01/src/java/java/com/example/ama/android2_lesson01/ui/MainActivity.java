package com.example.ama.android2_lesson01.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment;
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment;

/**
 * Class of list_of_notes_menu activity with the list of notes
 */

public class MainActivity extends AppCompatActivity
        implements ListOfNotesFragment.OnDetailsClickListener,
        DetailsNoteFragment.OnFinishEditClickListener {

    private static final String LIST_OF_NOTES_FRAGMENT = "list_of_notes_fragment";
    private static final String DETAILS_NOTE_FRAGMENT = "details_note_fragment";

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
    }

    private void initFragments() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_container_list_of_notes,
                        mFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT) == null ?
                                ListOfNotesFragment.newInstance() :
                                mFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT),
                        LIST_OF_NOTES_FRAGMENT)
                .commit();
        if (mFragmentManager.findFragmentByTag(DETAILS_NOTE_FRAGMENT) != null) {
            mFragmentManager.beginTransaction()
                    .replace(R.id.fl_container_list_of_notes,
                            mFragmentManager.findFragmentByTag(DETAILS_NOTE_FRAGMENT),
                            DETAILS_NOTE_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void openEditNote(Note note) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fl_container_list_of_notes,
                        DetailsNoteFragment.newInstance(note),
                        DETAILS_NOTE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void closeEditNote() {
        mFragmentManager.popBackStack();
    }
}