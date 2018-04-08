package com.example.ama.android2_lesson01.ui.rv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;

import java.util.ArrayList;

public class ListOfNotesFragment extends Fragment {
    private static final String LIST_OF_NOTES_DATA = "recycler_view_data";
    private ArrayList<Note> mData;

    public static ListOfNotesFragment newInstance(ArrayList<Note> mData) {
        ListOfNotesFragment fragment = new ListOfNotesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(LIST_OF_NOTES_DATA, mData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_of_notes, container, false);
        if (getArguments() != null) {
            mData = getArguments().getParcelableArrayList(LIST_OF_NOTES_DATA);
        }
        //TODO: create adapter and holder
        return rootView;
    }
}
