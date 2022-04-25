package com.example.android_projekt2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase wordRoomDatabase =
                WordRoomDatabase.getDatabase(application);
//repozytorium korzysta z DAO a nie bezpośrednio z bazy
        mWordDao = wordRoomDatabase.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    //operacje muszą być wykonywane w osobnym wątku
    void insert(Word word) {
//Runnable -> lambda
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }

    void deleteAll() {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.deleteAll();
        });
    }

    void deleteWord(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.deleteWord(word);
        });
    }
}
