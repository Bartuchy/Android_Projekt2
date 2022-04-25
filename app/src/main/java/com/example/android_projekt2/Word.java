package com.example.android_projekt2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word) {
        mWord = word;
    }

    //niektóre gettery i settery są wymagane przez Room
    public long getId() {
        return mId;
    }

    @NonNull
    public String getWord() {
        return mWord;
    }

    public void setId(long mId) {
        this.mId = mId;
    }
}