package com.example.ama.android2_lesson06.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.ama.android2_lesson06.R
import com.example.ama.android2_lesson06.ui.adapter.SmsCursorAdapter
import com.example.ama.android2_lesson06.ui.base.SmsExpampleView
import com.example.ama.android2_lesson06.ui.base.SmsPresenter
import com.example.ama.android2_lesson06.ui.mvp.SmsExamplePresenter
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

const val REQUEST_CODE = 1

class MainActivity : AppCompatActivity(), SmsExpampleView {

    private var disposable: Disposable? = null
    private var adapter: SmsCursorAdapter? = null
    private var presenter: SmsPresenter<SmsExpampleView>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = SmsExamplePresenter()
        disposable = RxPermissions(this)
                .request(android.Manifest.permission.READ_SMS,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) {
                        tuneList()
                        setListeners()
                    }
                }
    }

    override fun onStart() {
        presenter?.attachView(this)
        super.onStart()
    }

    override fun onStop() {
        presenter?.detachView()
        super.onStop()
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (shouldImport()) presenter?.importMessages()
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun tuneList() {
        adapter = SmsCursorAdapter(this)
        lv_sms_list.adapter = adapter
        adapter?.initLoader()
    }

    private fun setListeners() {
        btn_export.setOnClickListener { presenter?.exportMessages(adapter?.cursor) }
        btn_import.setOnClickListener {
            if (shouldImport()) presenter?.importMessages()
            else changeDefaultRequest()
        }
    }

    private fun shouldImport() = Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT ||
            Telephony.Sms.getDefaultSmsPackage(applicationContext) == packageName

    private fun changeDefaultRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val changeDefaultIntent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
            startActivityForResult(changeDefaultIntent, REQUEST_CODE)
        }
    }
}