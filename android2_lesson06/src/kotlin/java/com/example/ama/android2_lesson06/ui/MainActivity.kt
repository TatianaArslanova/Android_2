package com.example.ama.android2_lesson06.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ama.android2_lesson06.R
import com.example.ama.android2_lesson06.repo.SmsStorageManager
import com.example.ama.android2_lesson06.ui.adapter.SmsCursorAdapter
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private var adapter: SmsCursorAdapter? = null
    private val manager = SmsStorageManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        disposable = RxPermissions(this)
                .request(android.Manifest.permission.SEND_SMS,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) {
                        tuneList()
                        setListeners()
                    }
                }
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }

    fun tuneList() {
        adapter = SmsCursorAdapter(this)
        lv_sms_list.adapter = adapter
        adapter?.initLoader()
    }

    fun setListeners() {
        btn_export.setOnClickListener { manager.startExport(adapter?.cursor) }
        btn_import.setOnClickListener { manager.startImport() }
    }
}