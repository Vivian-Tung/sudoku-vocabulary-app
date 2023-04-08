package com.example.sudokuvocabulary.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sudokuvocabulary.R;
import com.example.sudokuvocabulary.adapters.DBAdapter;
import com.example.sudokuvocabulary.models.WordDictionaryModel;
import com.example.sudokuvocabulary.utils.PrefUtils;

public class SetSudokuSizeActivity extends MenuForAllActivity implements View.OnClickListener{

    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBAdapter(this);
        db.open();

        Button btn_4x4 = findViewById(R.id.btn_4x4);
        btn_4x4.setOnClickListener(this);

        Button btn_6x6 = findViewById(R.id.btn_6x6);
        btn_6x6.setOnClickListener(this);

        Button btn_9x9 = findViewById(R.id.btn_9x9);
        btn_9x9.setOnClickListener(this);

        Button btn_12x12 = findViewById(R.id.btn_12x12);
        btn_12x12.setOnClickListener(this);

    }

    @Override
    protected void setContentView() {
        this.setContentView(R.layout.activity_set_sudoku_size);
        TextView timerText = findViewById(R.id.TimerText);
        timerText.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int size = 0;
        switch (v.getId()) {
            case R.id.btn_4x4:
                size = 4;
                break;
            case R.id.btn_6x6:
                size = 6;
                break;
            case R.id.btn_9x9:
                size = 9;
                break;
            case R.id.btn_12x12:
                size = 12;
                break;
        }
        int subWidth = (int) Math.ceil(Math.sqrt(size));
        int subHeight = (int) Math.floor(Math.sqrt(size));
        Cursor cursor  = db.getAllRows("words");
        WordDictionaryModel dictionary = new WordDictionaryModel();
        while(cursor.moveToNext() && size-- > 0) {
            String word = cursor.getString(
                    cursor.getColumnIndexOrThrow("word"));
            String translation = cursor.getString(
                    cursor.getColumnIndexOrThrow("translation"));
            dictionary.add(word, translation);
        }
        cursor.close();
        Intent intent;
        if (getIntent().getBooleanExtra(getString(R.string.mode_key), false)) {
            intent = new Intent(this, ListenModeActivity.class);
            intent.putExtra(getString(R.string.words_key), dictionary.getWordsAsArray());
            intent.putExtra(getString(R.string.translations_key), dictionary.getTranslationsAsArray());
        } else {
            intent = WordListsActivity.newIntent(
                    SetSudokuSizeActivity.this, dictionary);
        }
        intent.putExtra(getString(R.string.sub_width_key), subWidth);
        intent.putExtra(getString(R.string.sub_height_key), subHeight);
        PrefUtils.saveBoolPreference(this, getString(R.string.save_game_key), false);
        startActivity(intent);
    }


}