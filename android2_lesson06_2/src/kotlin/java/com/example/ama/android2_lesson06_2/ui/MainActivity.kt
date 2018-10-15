package com.example.ama.android2_lesson06_2.ui

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.ama.android2_lesson06_2.R
import com.example.ama.android2_lesson06_2.ui.list.DeviceListFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        disposable = RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe { granted ->
                    if (!granted) {
                        Toast.makeText(this,
                                R.string.message_detecting_disabled,
                                Toast.LENGTH_SHORT).show()
                    }
                }
        savedInstanceState ?: supportFragmentManager.beginTransaction()
                .replace(R.id.fl_container,
                        DeviceListFragment.newInstance(),
                        DeviceListFragment::class.java.simpleName)
                .commit()
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}