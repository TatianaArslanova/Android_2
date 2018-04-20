package com.example.ama.android2_lesson01.ui.details;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ama.android2_lesson01.NotesApp;
import com.example.ama.android2_lesson01.R;

public class DetailsNoteFragment extends Fragment implements View.OnClickListener {

    private OnSaveNoteClickListener mListener;

    public static DetailsNoteFragment newInstance() {
        return new DetailsNoteFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSaveNoteClickListener) {
            mListener = (OnSaveNoteClickListener) context;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        View view = getView();
        if (view != null) {
            String title = ((EditText) view.findViewById(R.id.et_note_title)).getText().toString();
            String text = ((EditText) view.findViewById(R.id.et_note_text)).getText().toString();
            NotesApp.getDataManager().createNote(title, text);
            mListener.sandResult();
        }
    }

    public interface OnSaveNoteClickListener {
        void sandResult();
    }
}
