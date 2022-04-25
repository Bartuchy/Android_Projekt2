package com.example.android_projekt2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDao {
        //aby zezwolić na wielokrotne dodanie identycznego rekordu
        //zmienić strategię rozwiązywania konfliktów -> IGNORE
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Word word);
    @Query("DELETE FROM word_table")
    void deleteAll();
        //zapytania Room są wykonywane w osobnym wątku
        //LiveData powiadamia obserwatora w głównym wątku
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();
    @Delete
    void deleteWord(Word word);
}