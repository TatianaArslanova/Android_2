package com.example.ama.android2_lesson03.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.example.ama.android2_lesson03.PocketMap
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.ui.markers.MarkerListFragment
import com.example.ama.android2_lesson03.ui.search.SearchOnTheMapFragment

const val GOOGLE_MAP_PACKAGE_NAME = "com.google.android.apps.maps"

object Launcher {

    fun runSearchOnTheMapFragment(activity: MainActivity, addToBackStack: Boolean = false) {
        runFragment(activity, R.id.fl_container_main, SearchOnTheMapFragment.newInstance(), addToBackStack)
    }

    fun runMarkerListFragment(activity: MainActivity, addToBackStack: Boolean = false) {
        runFragment(activity, R.id.fl_container_main, MarkerListFragment.newInstance(), addToBackStack)
    }

    fun sendGoogleMapsIntent(activity: AppCompatActivity, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.`package` = GOOGLE_MAP_PACKAGE_NAME
        activity.startActivity(intent)
    }

    private fun runFragment(activity: AppCompatActivity, container: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(container, fragment, fragment::class.java.simpleName)
        if (addToBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }
}