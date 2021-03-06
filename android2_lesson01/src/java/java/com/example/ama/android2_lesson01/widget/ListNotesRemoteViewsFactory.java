package com.example.ama.android2_lesson01.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ama.android2_lesson01.NotesApp;
import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.details.DetailsNoteFragment;

import java.util.ArrayList;

public class ListNotesRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private ArrayList<Note> notes = new ArrayList<>();

    ListNotesRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        NotesApp.getDataManager().loadListOfAllNotes(mData -> {
            notes.clear();
            notes.addAll(mData);
        });
    }

    @Override
    public void onDestroy() {
        notes.clear();
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item_view);
        remoteViews.setTextViewText(R.id.tv_card_note_title, notes.get(position).getTitle());
        remoteViews.setTextViewText(R.id.tv_card_note_text, notes.get(position).getText());

        Intent intent = new Intent();
        intent.putExtra(DetailsNoteFragment.TARGET_NOTE, notes.get(position));
        remoteViews.setOnClickFillInIntent(R.id.ll_widget_item, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
