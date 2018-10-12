package com.example.ama.android2_lesson06.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson06.repo.SmsCursorLoader;

public class SmsCursorAdapter extends SimpleCursorAdapter {
    private final Uri URI = Uri.parse("content://sms");
    private final int LOADER_ID = 1;

    public SmsCursorAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2, null,
                new String[]{"address", "body"},
                new int[]{android.R.id.text1, android.R.id.text2}, 0);
    }

    public void initLoader() {
        LoaderManager.getInstance((AppCompatActivity) mContext)
                .initLoader(LOADER_ID, null, new SmsLoaderCallbacks());
    }

    public class SmsLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
            return new SmsCursorLoader(mContext, URI, null, null, null, null);
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
            swapCursor(cursor);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        }
    }

}
