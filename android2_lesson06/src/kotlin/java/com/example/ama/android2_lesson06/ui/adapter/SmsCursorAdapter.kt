package com.example.ama.android2_lesson06.ui.adapter

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v4.widget.SimpleCursorAdapter
import android.support.v7.app.AppCompatActivity

const val ADDRESS = "address"
const val BODY = "body"
const val LOADER_ID = 1

class SmsCursorAdapter(context: Context?)
    : SimpleCursorAdapter(context,
        android.R.layout.simple_list_item_2, null,
        arrayOf(ADDRESS, BODY),
        intArrayOf(android.R.id.text1, android.R.id.text2), 0) {

    val uri: Uri = Uri.parse("content://sms")

    fun initLoader() {
        LoaderManager.getInstance(mContext as AppCompatActivity)
                .initLoader(LOADER_ID, null, SmsLoaderCallbacks())
    }

    inner class SmsLoaderCallbacks : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(p0: Int, p1: Bundle?) =
                CursorLoader(mContext, uri, null, null, null, null)

        override fun onLoadFinished(p0: Loader<Cursor>, p1: Cursor?) {
            swapCursor(p1)
        }

        override fun onLoaderReset(p0: Loader<Cursor>) {
        }
    }
}