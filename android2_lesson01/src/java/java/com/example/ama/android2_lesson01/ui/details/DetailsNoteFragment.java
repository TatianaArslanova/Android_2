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
import com.example.ama.android2_lesson01.model.Note;

/**
 * Class of fragment for note editing
 */

public class DetailsNoteFragment extends Fragment implements View.OnClickListener {

    public static final String TARGET_NOTE = "target_note";

    private OnSaveNoteClickListener mListener;
    private Note mTargetNote;

    /**
     * Get new instance of {@link DetailsNoteFragment} with given parameter
     *
     * @param note note for edit and display in fragment.
     *             May be NULL, then a new empty note will be created later.
     * @return new fragment with given note parameter
     */

    public static DetailsNoteFragment newInstance(Note note) {
        DetailsNoteFragment fragment = new DetailsNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(TARGET_NOTE, note);
        fragment.setArguments(args);
        return fragment;
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
        Bundle args = getArguments();
        if (args != null) {
            mTargetNote = args.getParcelable(TARGET_NOTE);
            if (mTargetNote != null) {
                ((EditText) view.findViewById(R.id.et_note_title)).setText(mTargetNote.getTitle());
                ((EditText) view.findViewById(R.id.et_note_text)).setText(mTargetNote.getText());
            }
        }

    }

    @Override
    public void onClick(View v) {
        View view = getView();
        if (view != null) {
            String title = ((EditText) view.findViewById(R.id.et_note_title)).getText().toString();
            String text = ((EditText) view.findViewById(R.id.et_note_text)).getText().toString();
            if (mTargetNote == null) {
                NotesApp.getDataManager().createNote(title, text);
            } else {
                NotesApp.getDataManager().updateNote(mTargetNote, title, text);
            }
            mListener.sendResult();
        }
    }

    public interface OnSaveNoteClickListener {
        void sendResult();
    }
}
