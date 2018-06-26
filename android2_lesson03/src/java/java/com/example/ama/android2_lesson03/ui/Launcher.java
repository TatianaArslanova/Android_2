package com.example.ama.android2_lesson03.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;
import com.example.ama.android2_lesson03.ui.markers.MarkerListFragment;
import com.example.ama.android2_lesson03.ui.search.SearchOnTheMapFragment;

/**
 * Class for navigation
 */
public class Launcher {

    private static final String GOOGLE_MAP_PACKAGE_NAME = "com.google.android.apps.maps";

    public static void runSearchFragment(MainActivity activity, boolean addToBackStack) {
        runFragment(activity, R.id.fl_container_main, SearchOnTheMapFragment.newInstance(), addToBackStack);
    }

    public static void runMarkerListFragment(MainActivity activity, boolean addToBackStack) {
        runFragment(activity, R.id.fl_container_main, MarkerListFragment.newInstance(), addToBackStack);
    }

    private static void runFragment(AppCompatActivity activity, int container, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public static void sendGoogleMapsIntent(AppCompatActivity activity, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage(GOOGLE_MAP_PACKAGE_NAME);
        activity.startActivity(intent);
    }
}
