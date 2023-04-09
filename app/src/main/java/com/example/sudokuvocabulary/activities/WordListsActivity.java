package com.example.sudokuvocabulary.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.sudokuvocabulary.R;
import com.example.sudokuvocabulary.views.WordListsView;
import com.example.sudokuvocabulary.adapters.DBAdapter;
import com.example.sudokuvocabulary.models.WordDictionaryModel;

public class WordListsActivity extends MenuForAllActivity implements View.OnClickListener {

    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_lists);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        TextView timer = findViewById(R.id.TimerText);
        timer.setVisibility(View.GONE);

        db = new DBAdapter(this);
        db.open();

        Button new_word_list_button = (Button) findViewById(R.id.create_new_word_list_button);

        new_word_list_button.setOnClickListener(view -> {
            Intent intent = new Intent(
                    WordListsActivity.this, WordListNameActivity.class);
            startActivity(intent);
        });

        WordListsView existingWordLists = findViewById(R.id.existing_word_lists);
        existingWordLists.setWordListText(db.getTableNames());
        for (Button button: existingWordLists.getListButtons()) {
            button.setOnClickListener(this);
        }
    }

    @NonNull
    public static Intent newIntent(Context packageContext, WordDictionaryModel words) {
        Intent intent = new Intent(packageContext, SudokuActivity.class);
        intent.putExtra(packageContext.getString(R.string.words_key), words.getWordsAsArray());
        intent.putExtra(packageContext.getString(
                R.string.translations_key), words.getTranslationsAsArray());
        return intent;
    }

    @Override
    public void onClick(View view) {
        String tableName = (String) ((Button) view).getText();
        Cursor cursor  = db.getAllRows(tableName);
        WordDictionaryModel dictionary = new WordDictionaryModel();
        while(cursor.moveToNext()) {
            String word = cursor.getString(
                    cursor.getColumnIndexOrThrow("word"));
            String translation = cursor.getString(
                    cursor.getColumnIndexOrThrow("translation"));
            dictionary.add(word, translation);
        }
        cursor.close();
        Intent intent = newIntent(WordListsActivity.this, dictionary);
        intent.putExtra(getString(R.string.size_key), dictionary.getLength());
        intent.putExtra(getString(R.string.sub_width_key),
            (int) Math.ceil(Math.sqrt(dictionary.getLength()))
        );
        intent.putExtra(getString(R.string.sub_height_key),
                (int) Math.floor(Math.sqrt(dictionary.getLength()))
        );
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

}