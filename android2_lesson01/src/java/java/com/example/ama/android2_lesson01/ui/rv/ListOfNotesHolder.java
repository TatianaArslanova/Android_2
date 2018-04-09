package com.example.ama.android2_lesson01.ui.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ama.android2_lesson01.R;

public class ListOfNotesHolder extends RecyclerView.ViewHolder {
    private TextView mNoteTitle;
    private TextView mNoteText;

    public ListOfNotesHolder(View itemView) {
        super(itemView);
        mNoteTitle=itemView.findViewById(R.id.tv_card_note_title);
        mNoteText=itemView.findViewById(R.id.tv_card_note_text);
    }

    public TextView getNoteTitle() {
        return mNoteTitle;
    }

    public TextView getNoteText() {
        return mNoteText;
    }
}
