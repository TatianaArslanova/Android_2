package com.example.ama.android2_lesson01.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment;
import com.example.ama.android2_lesson01.widget.NotesWidgetProvider;

/**
 * Class of main activity
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Launcher.runListOfNotesFragment(this, false);
        }
        if (getIntent().getExtras() != null) {
            Note note = getIntent().getExtras().getParcelable(DetailsNoteFragment.TARGET_NOTE);
            if (note != null) {
                Launcher.runDetailsNoteFragment(this, true, note);
            }
        }
    }

    @Override
    protected void onStop() {
        NotesWidgetProvider.updateWidgets(getApplicationContext());
        super.onStop();
    }
}