package com.example.ama.android2_lesson01.ui.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;

public class ListOfNotesHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private TextView mNoteTitle;
    private TextView mNoteText;
    private ListOfNotesAdapter mAdapter;
    private OnNoteClickListener mListener;

    public ListOfNotesHolder(View itemView, ListOfNotesAdapter adapter, OnNoteClickListener listener) {
        super(itemView);
        mAdapter=adapter;
        mListener=listener;
        mNoteTitle = itemView.findViewById(R.id.tv_card_note_title);
        mNoteText = itemView.findViewById(R.id.tv_card_note_text);
    }

    public TextView getNoteTitle() {
        return mNoteTitle;
    }

    public TextView getNoteText() {
        return mNoteText;
    }

    @Override
    public void onClick(View v) {

    }

    public interface OnNoteClickListener {
        void onNoteClick(Note note);

        void onDeleteNoteClick(Note note);
    }
}
