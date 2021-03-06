package com.example.ama.android2_lesson01.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ama.android2_lesson01.NotesApp;
import com.example.ama.android2_lesson01.R;

/**
 * Class describing notes
 */

public class Note implements Parcelable {
    private long mId;
    private String mTitle;
    private String mText;

    /**
     * Constructor
     *
     * @param mId    note id from the database
     * @param mTitle note title
     * @param mText  note text
     */

    public Note(long mId, String mTitle, String mText) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mText = mText;
    }

    protected Note(Parcel in) {
        mId = in.readLong();
        mTitle = in.readString();
        mText = in.readString();
    }

    /**
     * Get new instance on NoteBuilder for Note creation
     *
     * @return new NoteBuilder instance
     */

    public static NoteBuilder builder() {
        return new NoteBuilder();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mTitle);
        dest.writeString(mText);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }

    public long getmId() {
        return mId;
    }

    /**
     * Class for Note building
     */

    public static class NoteBuilder {
        private long mId = 0;
        private String mTitle = NotesApp.getInstance().getString(R.string.default_note_title);
        private String mText = NotesApp.getInstance().getString(R.string.default_note_text);

        public NoteBuilder id(long mId) {
            this.mId = mId;
            return this;
        }

        public NoteBuilder title(String mTitle) {
            if (mTitle != null && !mTitle.equals("")) {
                this.mTitle = mTitle;
            }
            return this;
        }

        public NoteBuilder text(String mText) {
            if (mText != null && !mText.equals("")) {
                this.mText = mText;
            }
            return this;
        }

        public Note build() {
            return new Note(mId, mTitle, mText);
        }
    }
}
