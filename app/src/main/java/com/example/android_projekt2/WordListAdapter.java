package com.example.android_projekt2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<Word> mWordList;
    private SelectionTracker<Long> mSelectionTracker;
    private WordItemKeyProvider mWordItemKeyProvider;

    public WordListAdapter(Context context,
                           WordItemKeyProvider wordItemKeyProvider) {
        mLayoutInflater = LayoutInflater.from(context);
        mWordItemKeyProvider = wordItemKeyProvider;
        this.mWordList = null;
    }

    public WordListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mWordList = null;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                     parent, int viewType) {
        View rootView = mLayoutInflater
                .inflate(R.layout.recyclerview_item, parent, false);
        WordViewHolder wordViewHolder = new WordViewHolder(rootView);
        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        holder.bindToWordViewHolder(position);
    }

    

    @Override
    public int getItemCount() {
        if (mWordList != null)
            return mWordList.size();
        return 0;
    }

    //...
    public class WordViewHolder extends RecyclerView.ViewHolder {
        TextView wordTextView;
        //każdy wiersz ma teraz swoje szczegóły
        WordItemDetails wordItemDetails;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            wordTextView = itemView.findViewById(R.id.wordTextView);
            wordItemDetails = new WordItemDetails();
        }
        public void bindToWordViewHolder(int position)
        { wordTextView.setText(mWordList.get(position).getWord());
        }
//        public void bindToWordViewHolder(int position,
//                                         boolean isSelected) {
//            wordTextView.setText(mWordList.get(position).getWord());
//            wordItemDetails.id = mWordList.get(position).getId();
//            wordItemDetails.position = position;
////itemView = główny element wiersza (odziedziczony po
////ViewHolder); aktywny == zaznaczony
//            itemView.setActivated(isSelected);
//        }//umożliwiamy odczytanie szczegółów elementu listy (używane

        //wcześniej w WordItemDetailsLookup)
        public WordItemDetails getWordItemDetails() {
            return wordItemDetails;
        }
    }

    public void setWordList(List<Word> wordList) {
        this.mWordList = wordList;
        notifyDataSetChanged();
    }

    public void setSelectionTracker(
            SelectionTracker<Long> mSelectionTracker) {
        this.mSelectionTracker = mSelectionTracker;
    }
}