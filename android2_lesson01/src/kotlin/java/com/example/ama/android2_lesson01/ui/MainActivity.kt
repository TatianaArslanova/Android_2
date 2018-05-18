package com.example.ama.android2_lesson01.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment

/**
 * Class of main activity
 */

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Launcher.runListOfNotesFragment(this)
        Launcher.tryToRestoreDetailsNoteFragment(this)
    }
}
