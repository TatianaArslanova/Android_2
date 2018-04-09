package com.example.ama.android2_lesson01.model;

import android.os.Parcel;
import android.os.Parcelable;

//TODO: create builder

public class Note implements Parcelable {
    private String mTitle;
    private String mText;

    public Note(String mTitle, String mText) {
        this.mTitle = mTitle;
        this.mText = mText;
    }

    protected Note(Parcel in) {
        mTitle = in.readString();
        mText = in.readString();
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
        dest.writeString(mTitle);
        dest.writeString(mText);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getText() {
        return mText;
    }
}
