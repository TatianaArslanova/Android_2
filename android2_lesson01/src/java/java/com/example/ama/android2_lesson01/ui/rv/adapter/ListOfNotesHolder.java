package com.example.ama.android2_lesson01.ui.rv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment;

/**
 * Class of holder for RecyclerView from {@link ListOfNotesFragment}
 *
 * @see ListOfNotesFragment
 */

public class ListOfNotesHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private TextView mNoteTitle;
    private TextView mNoteText;
    private ListOfNotesAdapter mAdapter;
    private OnNoteClickListener mListener;

    /**
     * Constructor
     *
     * @param itemView view for RecyclerView item
     * @param adapter  RecyclerView adapter
     * @param listener listener for tracing clicks on the item
     */

    public ListOfNotesHolder(View itemView, ListOfNotesAdapter adapter, OnNoteClickListener listener) {
        super(itemView);
        mAdapter = adapter;
        mListener = listener;
        mNoteTitle = itemView.findViewById(R.id.tv_card_note_title);
        mNoteText = itemView.findViewById(R.id.tv_card_note_text);
        itemView.findViewById(R.id.ibtn_delete_note).setOnClickListener(this);
        itemView.findViewById(R.id.cv_note).setOnClickListener(this);
    }

    /**
     * Get TextView for note title
     *
     * @return TextView for note title
     */

    public TextView getNoteTitle() {
        return mNoteTitle;
    }

    /**
     * Get TextView for note text
     *
     * @return TextView for note text
     */

    public TextView getNoteText() {
        return mNoteText;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ibtn_delete_note) {
            mListener.onDeleteNoteClick(mAdapter.getmData().get(getAdapterPosition()));
        } else mListener.onEditNoteClick(mAdapter.getmData().get(getAdapterPosition()));
    }

    public interface OnNoteClickListener {
        void onEditNoteClick(Note note);

        void onDeleteNoteClick(Note note);
    }
}
