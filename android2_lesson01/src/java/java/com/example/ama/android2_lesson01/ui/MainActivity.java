package com.example.ama.android2_lesson01.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.details.DetailsNoteActivity;
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment;
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment;
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesHolder;

public class MainActivity extends AppCompatActivity
        implements ListOfNotesHolder.OnNoteClickListener {

    public static final int NOTE_EDITED_REQUEST = 1;
    private static final String LIST_OF_NOTES_FRAGMENT = "recycler_view_fragment";

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_add_note) {
            openEditNote(null);
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NOTE_EDITED_REQUEST && resultCode == RESULT_OK) {
            ((ListOfNotesFragment) mFragmentManager
                    .findFragmentByTag(LIST_OF_NOTES_FRAGMENT)).refresh();
        }
    }

    @Override
    public void onNoteClick(Note note) {
        openEditNote(note);
    }

    @Override
    public void onDeleteNoteClick(Note note) {
        if (note != null) {
            ((ListOfNotesFragment) mFragmentManager
                    .findFragmentByTag(LIST_OF_NOTES_FRAGMENT)).deleteNote(note);
        }
    }

    private void openEditNote(Note note) {
        Intent intent = new Intent(this, DetailsNoteActivity.class);
        intent.putExtra(DetailsNoteFragment.TARGET_NOTE, note);
        startActivityForResult(intent, NOTE_EDITED_REQUEST);
    }
}
