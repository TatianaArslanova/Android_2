package com.example.ama.android2_lesson01.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment

/**
 * Class of list_of_notes_menu activity with the list of notes
 */

class MainActivity : AppCompatActivity(),
        ListOfNotesFragment.OnDetailsClickListener,
        DetailsNoteFragment.OnFinishEditClickListener {

    companion object {
        const val LIST_OF_NOTES_FRAGMENT = "list_of_notes_fragment"
        const val DETAILS_NOTE_FRAGMENT = "details_note_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragments()
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container_list_of_notes,
                        if (supportFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT) == null)
                            ListOfNotesFragment.newInstance()
                        else supportFragmentManager.findFragmentByTag(LIST_OF_NOTES_FRAGMENT),
                        LIST_OF_NOTES_FRAGMENT)
                .commit()
        if (supportFragmentManager.findFragmentByTag(DETAILS_NOTE_FRAGMENT) != null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_container_list_of_notes,
                            supportFragmentManager.findFragmentByTag(DETAILS_NOTE_FRAGMENT),
                            DETAILS_NOTE_FRAGMENT)
                    .commit()
        }
    }

    override fun openEditNote(note: Note?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container_list_of_notes,
                        DetailsNoteFragment.newInstance(note),
                        DETAILS_NOTE_FRAGMENT)
                .addToBackStack(null)
                .commit()
    }

    override fun closeEditNote() {
        supportFragmentManager.popBackStack()
    }
}
