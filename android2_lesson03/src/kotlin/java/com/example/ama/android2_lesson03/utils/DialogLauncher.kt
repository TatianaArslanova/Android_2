package com.example.ama.android2_lesson03.utils

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import com.example.ama.android2_lesson03.R

object DialogLauncher {

    fun showEditDialog(context: Context, dialogTitle: String, dialogMessage: String, etText: String,
                       okListener: (newName: String) -> Unit) {
        val view = LayoutInflater.from(context).inflate(R.layout.edit_marker_dialog, null)
        val etName = view.findViewById<EditText>(R.id.et_marker_name)
        etName.setText(etText)
        etName.selectAll()
        AlertDialog.Builder(context)
                .setView(view)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton(R.string.button_ok_text, { dialogInterface, i -> okListener.invoke(etName.text.toString()) })
                .setNegativeButton(R.string.button_text_cancel, { dialogInterface, i -> dialogInterface.cancel() })
                .create()
                .show()
    }
}