package com.example.ama.android2_lesson03.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.ama.android2_lesson03.PocketMap;
import com.example.ama.android2_lesson03.R;

public class DialogLauncher {

    public static void showEditDialog(Context context,
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
                .setPositiveButton(PocketMap.getInstance().getString(R.string.button_ok_text), (dialogInterface, i) -> {
                    okListener.onPositiveResult(etName.getText().toString());
                    dialogInterface.cancel();
                })
                .setNegativeButton(PocketMap.getInstance().getString(R.string.button_text_cancel), (dialogInterface, i) -> dialogInterface.cancel())
                .create()
                .show();
    }

    public interface OnDialogResult {
        void onPositiveResult(String inputText);
    }
}
