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

public class ListOfNotesHolder extends RecyclerView.ViewHolder {

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
    }

    public void bind(Note note) {
        ((TextView) itemView.findViewById(R.id.tv_card_note_title)).setText(note.getTitle());
        ((TextView) itemView.findViewById(R.id.tv_card_note_text)).setText(note.getText());
        setListeners();
    }

    private void setListeners() {
        itemView.findViewById(R.id.ibtn_delete_note).setOnClickListener(
                v -> mListener.onDeleteNoteClick(mAdapter.getmData().get(getAdapterPosition())))
        ;
        itemView.findViewById(R.id.cv_note).setOnClickListener(
                v -> mListener.onEditNoteClick(mAdapter.getmData().get(getAdapterPosition())));
    }

    public interface OnNoteClickListener {
        void onEditNoteClick(Note note);

        void onDeleteNoteClick(Note note);
    }
}
