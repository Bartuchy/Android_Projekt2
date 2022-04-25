package com.example.android_projekt2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.Selection;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private WordViewModel mWordViewModel;
    private WordListAdapter mAdapter;
    private FloatingActionButton mMainFab;
    private SelectionTracker<Long> mSelectionTracker;
    private FloatingActionButton mDeleteFab;
    private boolean mIsMainFabAdd = true;
    private WordItemKeyProvider mWordItemKeyProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new WordListAdapter(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        //Observer::onChanged -> lambda
        mWordViewModel.getAllWords().observe(this, words -> {
            mAdapter.setWordList(words);
        });

        mWordItemKeyProvider = new WordItemKeyProvider();
        mAdapter = new WordListAdapter(this, mWordItemKeyProvider);
        recyclerView.setAdapter(mAdapter);
        mSelectionTracker = new SelectionTracker.Builder<>(
                "word-selection",
                recyclerView,
                mWordItemKeyProvider,
//odczytuje szczegóły wybranego elementu
                new WordItemDetailsLookup(recyclerView),
//magazyn na klucze
                StorageStrategy.createLongStorage()).build();
        mAdapter.setSelectionTracker(mSelectionTracker);
//...
//obsługa przycisku FAB kasowania zaznaczonych elementów
        mDeleteFab = findViewById(R.id.fabDelete);
        mDeleteFab.setOnClickListener(view -> deleteSelection());
//selection tracker będzie informował o zmianach zaznaczenia
        mSelectionTracker.addObserver(
                new SelectionTracker.SelectionObserver<Long>() {
                    @Override
                    public void onSelectionChanged() {
//wyświetlamy/chowamy przycisk kasowania
                        updateFabs();
                        super.onSelectionChanged();
                    }

                    @Override
                    public void onSelectionRestored() {
//wyświetlamy/chowamy przycisk kasowania
                        updateFabs();
                        super.onSelectionRestored();
                    }

                    @Override
                    public void onItemStateChanged(@NonNull Long key,
                                                   boolean selected) {
                        super.onItemStateChanged(key, selected);
                    }
                });


        //...


        //w zależności od sytuacji główny przycisk jest „+” lub „x”
//dodatkowo pokazujemy lub ukrywamy przycisk kasowania


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.clear_data) {
//            Toast.makeText(this, "Clearing the data...",
//                    Toast.LENGTH_SHORT).show();
//            mWordViewModel.deleteAll();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Word word = new Word(result.getData()
                                .getStringExtra(
                                        NewWordActivity.EXTRA_REPLY));
                        mWordViewModel.insert(word);
                    }
                });
        mMainFab = findViewById(R.id.fabMain);
        mMainFab.setOnClickListener(view -> mainFabClicked());
    }

    ActivityResultLauncher<Intent> mActivityResultLauncher;


    private void mainFabClicked() {
//główny przycisk może być „+” (dodaj) lub „x” (anuluj)
        Toast.makeText(MainActivity.this, "your message" , Toast.LENGTH_LONG).show();


        if (mIsMainFabAdd) {
            Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
            mActivityResultLauncher.launch(intent);
        } else {
            mSelectionTracker.clearSelection();
        }
    }



    @Override
    protected void onRestoreInstanceState(
            @NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//stan zaznaczenia trzeba przywrócić
        mSelectionTracker.onRestoreInstanceState(savedInstanceState);


        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Word word = new Word(result.getData()
                                .getStringExtra(
                                        NewWordActivity.EXTRA_REPLY));
                        mWordViewModel.insert(word);
                    }
                });
        mMainFab = findViewById(R.id.fabMain);
        mMainFab.setOnClickListener(view -> mainFabClicked());
    }





    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//stan zaznaczenia trzeba zachować
        mSelectionTracker.onSaveInstanceState(outState);
    }


    private void deleteSelection() {
        Selection<Long> selection = mSelectionTracker.getSelection();
        int wordPosition = -1;
        List<Word> wordList = mWordViewModel.getAllWords().getValue();
//przeglądamy identyfikatory z zaznaczenia i kasujemy elementy
        for (long wordId : selection) {
            wordPosition = mWordItemKeyProvider.getPosition(wordId);
            mWordViewModel.deleteWord(wordList.get(wordPosition));
        }
    }

    private void updateFabs() {
        if (mSelectionTracker.hasSelection()) {
            mDeleteFab.setVisibility(View.VISIBLE);
            mMainFab.setImageDrawable(
                    getDrawable(R.drawable.ic_baseline_cancel_24));
            mIsMainFabAdd = false;
        } else {
            mDeleteFab.setVisibility(View.GONE);
            mMainFab.setImageDrawable(
                    getDrawable(R.drawable.ic_baseline_add_24));
            mIsMainFabAdd = true;
        }
    }
}