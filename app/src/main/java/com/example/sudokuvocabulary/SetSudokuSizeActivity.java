package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

public class SetSudokuSizeActivity extends AppCompatActivity implements View.OnClickListener{

    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_sudoku_size);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupTutorialButton();

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

    private void setupTutorialButton() {
        ImageView tutorialBtn = findViewById(R.id.tutorialBtn);
        tutorialBtn.setOnClickListener(view -> {

            Intent intent = new Intent(SetSudokuSizeActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
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
        WordDictionary dictionary = new WordDictionary();
        while(cursor.moveToNext() && size-- > 0) {
            String word = cursor.getString(
                    cursor.getColumnIndexOrThrow("word"));
            String translation = cursor.getString(
                    cursor.getColumnIndexOrThrow("translation"));
            dictionary.add(word, translation);
        }
        cursor.close();
        Intent intent = WordListsActivity.newIntent(
                SetSudokuSizeActivity.this, dictionary);
        intent.putExtra(getString(R.string.sub_width_key), subWidth);
        intent.putExtra(getString(R.string.sub_height_key), subHeight);
        startActivity(intent);
    }

}