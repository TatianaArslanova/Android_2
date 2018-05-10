package com.example.ama.android2_lesson01.ui.rv;

import android.content.Context;
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
import com.example.ama.android2_lesson01.ui.base.Presenter;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.rv.adapter.ListOfNotesAdapter;
import com.example.ama.android2_lesson01.ui.rv.adapter.ListOfNotesHolder;
import com.example.ama.android2_lesson01.ui.rv.mvp.ListOfNotesPresenter;
import com.example.ama.android2_lesson01.ui.rv.mvp.ListOfNotesView;

import java.util.ArrayList;

/**
 * Class of fragment that contains RecyclerView for displaying notes
 */

public class ListOfNotesFragment extends Fragment
        implements ListOfNotesHolder.OnNoteClickListener,
        ListOfNotesView {

    private static final int RV_SPAN_COUNT = 2;

    private OnDetailsClickListener mListener;
    private ListOfNotesAdapter mAdapter;
    private Presenter<ListOfNotesView> mPresenter;

    private RecyclerView mNoteList;
    private TextView mNoNotesMessage;

    public static ListOfNotesFragment newInstance() {
        return new ListOfNotesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailsClickListener) {
            mListener = (OnDetailsClickListener) context;
        }
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
        mAdapter = new ListOfNotesAdapter(this);
        mNoteList.setAdapter(mAdapter);
        mPresenter = new ListOfNotesPresenter<>();
        mPresenter.attachView(this);
        mPresenter.loadData();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        mPresenter = null;
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_of_notes_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_add_note) {
            mListener.openEditNote(null);
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

    @Override
    public void onEditNoteClick(Note note) {
        mListener.openEditNote(note);
    }

    @Override
    public void onDeleteNoteClick(Note note) {
        mPresenter.deleteNote(note);
    }

    public interface OnDetailsClickListener {
        void openEditNote(Note note);
    }
}
