package com.example.ama.android2_lesson01.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment
import com.example.ama.android2_lesson01.widget.NotesWidgetProvider

/**
 * Class of main activity
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: Launcher.runListOfNotesFragment(this)
        val note = intent.extras?.getParcelable(DetailsNoteFragment.TARGET_NOTE) as Note?
        if (note != null) {
            Launcher.runDetailsNoteFragment(this, true, note)
        }
    }

    override fun onStop() {
        NotesWidgetProvider.updateWidget(applicationContext)
        super.onStop()
    }
}
