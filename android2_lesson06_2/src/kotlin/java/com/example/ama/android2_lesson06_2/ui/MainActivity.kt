package com.example.ama.android2_lesson06_2.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson06_2.R
import com.example.ama.android2_lesson06_2.ui.list.DeviceListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container,
                        DeviceListFragment.newInstance(),
                        DeviceListFragment::class.java.simpleName)
                .commit()
    }
}