package com.example.ama.android2_lesson01.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val LIST_OF_NOTES_FRAGMENT = "list_of_notes_fragment"
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
    }
}
