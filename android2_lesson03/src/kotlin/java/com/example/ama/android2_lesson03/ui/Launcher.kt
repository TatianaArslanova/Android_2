package com.example.ama.android2_lesson03.ui

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson03.R
import com.example.ama.android2_lesson03.ui.search.SearchOnTheMapFragment

object Launcher {

    fun runSearchOnTheMapFragment(activity: MainActivity, addToBackStack: Boolean = false) {
        runFragment(activity, R.id.fl_container_main, SearchOnTheMapFragment.newInstance(), addToBackStack)
    }

    private fun runFragment(activity: AppCompatActivity, container: Int, fragment: Fragment, addToBackStack: Boolean) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(container, fragment, fragment::class.java.simpleName)
        if (addToBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }
}