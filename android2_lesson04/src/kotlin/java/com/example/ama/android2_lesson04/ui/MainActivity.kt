package com.example.ama.android2_lesson04.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson04.R
import com.example.ama.android2_lesson04.ui.start.StartFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: runFragment(StartFragment.newInstance(), false)
    }

    fun runFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_container_main, fragment, fragment::class.java.simpleName)
        if (addToBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }
}