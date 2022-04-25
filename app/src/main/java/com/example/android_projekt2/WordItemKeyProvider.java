package com.example.android_projekt2;

import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Klasa zapewnia mapowanie pomiędzy kluczami Long a pozycją na liście
//na podstawie pozycji ustala klucz a na podstawie klucza ustala pozycję
public class WordItemKeyProvider extends ItemKeyProvider<Long> {
    private Map<Long,Integer> mKeyToPosition;
    private List<Word> mWordList;
    public WordItemKeyProvider() {
        super(SCOPE_MAPPED);
        mWordList=null;
    }
    public void setWords(List<Word> wordList)
    {
        this.mWordList = wordList;
        mKeyToPosition=new HashMap<>(mWordList.size());
        for (int i=0;i<mWordList.size();++i)
            mKeyToPosition.put(mWordList.get(i).getId(),i);
    }
    @Nullable
    @Override
    public Long getKey(int position) {
        return mWordList.get(position).getId();
    }
    @Override
    public int getPosition(Long key) { return mKeyToPosition.get(key); }
}