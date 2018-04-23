package com.example.ama.android2_lesson01.ui.rv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ama.android2_lesson01.NotesApp;
import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

public class ListOfNotesFragment extends Fragment implements ListOfNotesHolder.OnNoteClickListener {
    private ArrayList<Note> mData;
    private ListOfNotesHolder.OnNoteClickListener mListener;
    private ListOfNotesAdapter mAdapter;

    public static ListOfNotesFragment newInstance() {
        return new ListOfNotesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_of_notes, container, false);
        mData = NotesApp.getDataManager().getListOfAllNotes();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener = this;
        RecyclerView rv = view.findViewById(R.id.rv_list_of_notes);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mAdapter = new ListOfNotesAdapter(mData, mListener);
        rv.setAdapter(mAdapter);
    }

    public void refresh() {
        mData = NotesApp.getDataManager().getListOfAllNotes();
        mAdapter.setmData(mData);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteClick(Note note) {

    }

    @Override
    public void onDeleteNoteClick(Note note) {
        NotesApp.getDataManager().removeNote(note);
        refresh();
    }
}
