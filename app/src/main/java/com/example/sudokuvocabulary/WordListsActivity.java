package com.example.sudokuvocabulary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class WordListsActivity extends AppCompatActivity implements View.OnClickListener {

    private DBAdapter db;

    private Button new_word_list_button;

    private ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_lists);

        db = new DBAdapter(this);
        db.open();

        categories = db.getTableNames();

        new_word_list_button = (Button) findViewById(R.id.create_new_word_list_button);

        new_word_list_button.setOnClickListener(view -> {
            Intent intent = new Intent(WordListsActivity.this, WordBankActivity.class);
            startActivity(intent);
        });

        WordListsView existingWordLists = findViewById(R.id.existing_word_lists);
        existingWordLists.setWordListText(categories);
        for (Button button: existingWordLists.getListButtons()) {
            button.setOnClickListener(this);
        }
    }

    @NonNull
    public static Intent newIntent(Context packageContext, WordDictionary words) {
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
        WordDictionary dictionary = new WordDictionary();
        while(!cursor.isAfterLast()) {
            String word = cursor.getString(
                    cursor.getColumnIndexOrThrow("word"));
            String translation = cursor.getString(
                    cursor.getColumnIndexOrThrow("translation"));
            dictionary.add(word, translation);
            cursor.moveToNext();
        }
        cursor.close();
        Intent intent = newIntent(WordListsActivity.this, dictionary);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
}