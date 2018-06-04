package com.example.ama.android2_lesson01.ui.rv;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.Launcher;
import com.example.ama.android2_lesson01.ui.MainActivity;
import com.example.ama.android2_lesson01.ui.rv.adapter.ListOfNotesAdapter;
import com.example.ama.android2_lesson01.ui.rv.adapter.ListOfNotesHolder;
import com.example.ama.android2_lesson01.ui.rv.mvp.ListOfNotesPresenter;
import com.example.ama.android2_lesson01.ui.rv.mvp.base.ListOfNotesView;
import com.example.ama.android2_lesson01.ui.rv.mvp.base.ListPresenter;

import java.util.ArrayList;

/**
 * Class of fragment that contains RecyclerView for displaying notes
 */

public class ListOfNotesFragment extends Fragment implements ListOfNotesView {

    private static final int RV_SPAN_COUNT = 2;

    private ListOfNotesAdapter mAdapter;
    private ListPresenter<ListOfNotesView> mPresenter;

    private RecyclerView mNoteList;
    private TextView mNoNotesMessage;

    public static ListOfNotesFragment newInstance() {
        return new ListOfNotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNoNotesMessage = view.findViewById(R.id.tv_no_notes_message);
        mNoteList = view.findViewById(R.id.rv_list_of_notes);
        mNoteList.setLayoutManager(new GridLayoutManager(getActivity(), RV_SPAN_COUNT));
        mAdapter = new ListOfNotesAdapter(new ListOfNotesHolder.OnNoteClickListener() {
            @Override
            public void onEditNoteClick(Note note) {
                Launcher.runDetailsNoteFragment((MainActivity) getActivity(), true, note);
            }

            @Override
            public void onDeleteNoteClick(Note note) {
                mPresenter.deleteNote(note);
            }
        });
        mNoteList.setAdapter(mAdapter);
        mPresenter = new ListOfNotesPresenter<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
        mPresenter.loadData();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_of_notes_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_add_note) {
            Launcher.runDetailsNoteFragment((MainActivity) getActivity(), true, null);
            return true;
        }
        return false;
    }

    @Override
    public void showNoteList(ArrayList<Note> mData) {
        mAdapter.setmData(mData);
    }

    @Override
    public void showEmptyMessage() {
        mNoNotesMessage.setVisibility(View.VISIBLE);
        mNoteList.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideEmptyMessage() {
        mNoNotesMessage.setVisibility(View.INVISIBLE);
        mNoteList.setVisibility(View.VISIBLE);
    }
}
