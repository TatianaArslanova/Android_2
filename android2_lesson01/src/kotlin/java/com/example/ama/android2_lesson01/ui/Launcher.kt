package com.example.ama.android2_lesson01.ui

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson01.R
import com.example.ama.android2_lesson01.model.Note
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment

object Launcher {

    /**
     * Find [ListOfNotesFragment] if exists or create new instance if it's not. Replace fragment to
     * container on the given activity. Parameter addToBackStack is false by default.
     *
     * @param activity activity to place the fragment
     * @param addToBackStack add transaction to the back stack if true
     * @see ListOfNotesFragment
     */

    fun runListOfNotesFragment(activity: MainActivity, addToBackStack: Boolean = false) {
        var fragment: Fragment? = activity.supportFragmentManager
                .findFragmentByTag(ListOfNotesFragment::class.java.simpleName)
        if (fragment == null) fragment = ListOfNotesFragment.newInstance()
        runFragment(activity, R.id.fl_main_container, fragment, addToBackStack)
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

    /**
     * Find [DetailsNoteFragment] on the given activity. Replace if exists and do nothing if it's not.
     * Paramener addToBackStack is false by default.
     *
     * @param activity activity to find and place the fragment
     * @param addToBackStack add transaction to the back stack if true
     * @see DetailsNoteFragment
     */

    fun tryToRestoreDetailsNoteFragment(activity: MainActivity, addToBackStack: Boolean = false) {
        val fragment: Fragment? = activity.supportFragmentManager
                .findFragmentByTag(DetailsNoteFragment::class.java.simpleName)
        if (fragment != null) {
            runFragment(activity, R.id.fl_main_container, fragment, addToBackStack)
        }
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