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

    public static void showToast(String message) {
        Toast.makeText(PocketMap.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showDialog(Context context,
                                  String title,
                                  String message,
                                  String markerName,
                                  final OnDialogResult okListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.edit_marker_dialog, null);
        final EditText etName = view.findViewById(R.id.et_marker_name);
        etName.setText(markerName);
        etName.selectAll();
        new AlertDialog.Builder(context)
                .setView(view)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(PocketMap.getInstance().getString(R.string.button_ok_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        okListener.onPositiveResult(etName.getText().toString());
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton(PocketMap.getInstance().getString(R.string.button_text_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .create()
                .show();
    }

    public interface OnDialogResult {
        void onPositiveResult(String inputText);
    }
}
