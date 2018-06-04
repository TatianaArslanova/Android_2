package com.example.ama.android2_lesson01.ui

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment

object Launcher {

    /**
     * Replace [ListOfNotesFragment] fragment to container on the given activity.
     * Parameter addToBackStack is false by default.
     *
     * @param activity activity to place the fragment
     * @param addToBackStack add transaction to the back stack if true
     * @see ListOfNotesFragment
     */

    fun runListOfNotesFragment(activity: MainActivity, addToBackStack: Boolean = false) {
        runFragment(activity, R.id.fl_main_container, ListOfNotesFragment.newInstance(), addToBackStack)
    }

    /**
     * Add new instance of [DetailsNoteFragment] to the given activity with [Note] argument by
     * replacing on the main container. Parameter addToBackStack is false by default.
     *
     * @param activity activity to place the fragment
     * @param addToBackStack add transaction to the back stack if true
     * @param note the argument for [DetailsNoteFragment]
     * @see DetailsNoteFragment
     * @see Note
     */

    fun runDetailsNoteFragment(activity: MainActivity, addToBackStack: Boolean = false, note: Note?) {
        runFragment(activity, R.id.fl_main_container, DetailsNoteFragment.newInstance(note), addToBackStack)
    }

    fun back(activity: AppCompatActivity) {
        activity.supportFragmentManager.popBackStack()
    }

    private fun runFragment(activity: AppCompatActivity, containerID: Int, fragment: Fragment, addToBackStack: Boolean) {
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
                .replace(containerID, fragment, fragment::class.java.simpleName)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment::class.java.simpleName)
        }
        fragmentTransaction.commit()
    }
}