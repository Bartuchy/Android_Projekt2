package com.example.android_projekt2;

import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

public class WordItemDetails extends
        ItemDetailsLookup.ItemDetails<Long> {
    int position;
    long id;
    @Override
    public int getPosition() { return position; }
    @Nullable
    @Override
    public Long getSelectionKey() { return id; }
    @Override
    public boolean inSelectionHotspot(@NonNull MotionEvent e) {
        return false;
    }
    @Override
    public boolean inDragRegion(@NonNull MotionEvent e) {
        return true;
    }}
