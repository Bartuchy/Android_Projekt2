package com.example.android_projekt2;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class WordItemDetailsLookup extends ItemDetailsLookup<Long> {
    private RecyclerView mRecyclerView;

    public WordItemDetailsLookup(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
//ustala szczegóły zaznaczonego elementu (na podstawie x i y)
        View view =
                mRecyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder =
                    mRecyclerView.getChildViewHolder(view);
            if (viewHolder instanceof WordListAdapter.WordViewHolder)
                return ((WordListAdapter.WordViewHolder) viewHolder)
                        .getWordItemDetails();
        }
        return null;
    }
}