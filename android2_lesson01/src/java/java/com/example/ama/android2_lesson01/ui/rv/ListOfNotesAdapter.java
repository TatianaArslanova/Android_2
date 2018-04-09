package com.example.ama.android2_lesson01.ui.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

public class ListOfNotesAdapter extends RecyclerView.Adapter<ListOfNotesHolder> {
    private ArrayList<Note> mData;

    public ListOfNotesAdapter(ArrayList<Note> mData){
        this.mData=mData;
    }

    @NonNull
    @Override
    public ListOfNotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
        return new ListOfNotesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfNotesHolder holder, int position) {
        holder.getNoteTitle().setText(mData.get(position).getTitle());
        holder.getNoteText().setText(mData.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
