package com.example.ama.android2_lesson01.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment;
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment;

/**
 * Class for navigation
 */

public class Launcher {

    /**
     * Replace {@link ListOfNotesFragment} fragment to container on the given activity.
     *
     * @param activity       activity to place the fragment
     * @param addToBackStack add transaction to the back stack if true
     * @see ListOfNotesFragment
     */

    public static void runListOfNotesFragment(MainActivity activity, boolean addToBackStack) {
        runFragment(activity, R.id.fl_main_container, ListOfNotesFragment.newInstance(), addToBackStack);
    }

    /**
     * Add new instance of {@link DetailsNoteFragment} to the given activity with {@link Note}
     * argument by replacing on the main container.
     *
     * @param activity       activity to place the fragment
     * @param addToBackStack add transaction to the back stack if true
     * @param note           the argument for {@link DetailsNoteFragment}
     * @see DetailsNoteFragment
     * @see Note
     */

    public static void runDetailsNoteFragment(MainActivity activity, boolean addToBackStack, Note note) {
        runFragment(activity, R.id.fl_main_container, DetailsNoteFragment.newInstance(note), addToBackStack);
    }

    public static void back(AppCompatActivity activity) {
        activity.getSupportFragmentManager().popBackStack();
    }

    private static void runFragment(AppCompatActivity activity, int containerID, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction()
                .replace(containerID, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }
}