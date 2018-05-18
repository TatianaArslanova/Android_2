package com.example.ama.android2_lesson01.ui.rv.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.rv.ListOfNotesFragment;

import java.util.ArrayList;

/**
 * Adapter class for RecyclerView of {@link ListOfNotesFragment}
 *
 * @see ListOfNotesFragment
 */

public class ListOfNotesAdapter extends RecyclerView.Adapter<ListOfNotesHolder> {

    private ArrayList<Note> mData = new ArrayList<>();
    private ListOfNotesHolder.OnNoteClickListener mListener;

    public ListOfNotesAdapter(ListOfNotesHolder.OnNoteClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ListOfNotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
        return new ListOfNotesHolder(itemView, this, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfNotesHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Get ArrayList of all displaying {@link Note} objects
     *
     * @return ArrayList of notes
     * @see Note
     */

    public ArrayList<Note> getmData() {
        return mData;
    }

    /**
     * Set ArrayList of all displaying {@link Note} objects and notify adapter about changes
     *
     * @param mData ArrayList of notes
     */

    public void setmData(ArrayList<Note> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }
}
