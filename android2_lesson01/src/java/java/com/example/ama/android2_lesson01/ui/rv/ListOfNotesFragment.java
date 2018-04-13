package com.example.ama.android2_lesson01.ui.rv;

import android.content.Context;
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

public class ListOfNotesFragment extends Fragment {
    private ArrayList<Note> mData;
    private ListOfNotesHolder.OnNoteClickListener mListener;

    public static ListOfNotesFragment newInstance() {
        return new ListOfNotesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListOfNotesHolder.OnNoteClickListener){
            mListener=(ListOfNotesHolder.OnNoteClickListener) context;
        }
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
        RecyclerView rv = view.findViewById(R.id.rv_list_of_notes);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv.setAdapter(new ListOfNotesAdapter(mData, mListener));
    }
}
