package com.example.ama.android2_lesson01.ui.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ama.android2_lesson01.R;
import com.example.ama.android2_lesson01.model.Note;
import com.example.ama.android2_lesson01.ui.Launcher;
import com.example.ama.android2_lesson01.ui.MainActivity;
import com.example.ama.android2_lesson01.ui.details.mvp.DetailsNotePresenter;
import com.example.ama.android2_lesson01.ui.details.mvp.base.DetailsNoteView;
import com.example.ama.android2_lesson01.ui.details.mvp.base.DetailsPresenter;

/**
 * Class of fragment for note editing
 */

public class DetailsNoteFragment extends Fragment
        implements DetailsNoteView {

    public static final String TARGET_NOTE = "target_note";

    private DetailsPresenter<DetailsNoteView> mPresenter;
    private Note mTargetNote;

    private EditText mEtTitle;
    private EditText mEtText;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEtTitle = view.findViewById(R.id.et_note_title);
        mEtText = view.findViewById(R.id.et_note_text);
        if (getArguments() != null) {
            mTargetNote = getArguments().getParcelable(TARGET_NOTE);
            if (mTargetNote != null) {
                mEtTitle.setText(mTargetNote.getTitle());
                mEtText.setText(mTargetNote.getText());
            }
        }
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick();
            }
        });
        mPresenter = new DetailsNotePresenter<>();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_note_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_remove_note) {
            mPresenter.deleteNote(mTargetNote);
            return true;
        }
        return false;
    }

    public void onSaveButtonClick() {
        String title = mEtTitle.getText().toString();
        String text = mEtText.getText().toString();
        if (mTargetNote != null) {
            mPresenter.updateNote(mTargetNote, title, text);
        } else {
            mPresenter.createNote(title, text);
        }
        finishEditing();
    }

    @Override
    public void finishEditing() {
        if (getActivity() != null) {
            Launcher.back((MainActivity) getActivity());
        }
    }
}
