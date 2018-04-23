package com.example.ama.android2_lesson01.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.ui.details.DetailsNoteActivity;
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment;

public class MainActivity extends AppCompatActivity {

    public static final int NOTE_EDITED_REQUEST = 1;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_add_note) {
            createNote();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NOTE_EDITED_REQUEST && resultCode == RESULT_OK) {
            ((ListOfNotesFragment) getSupportFragmentManager().findFragmentByTag(LIST_OF_NOTES_FRAGMENT)).refresh();
        }
    }

    private void createNote() {
        Intent intent = new Intent(this, DetailsNoteActivity.class);
        startActivityForResult(intent, NOTE_EDITED_REQUEST);
    }
}
