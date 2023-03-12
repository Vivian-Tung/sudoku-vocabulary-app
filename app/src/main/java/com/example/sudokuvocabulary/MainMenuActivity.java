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

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    DBAdapter db;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

//        db = new DBAdapter(this);
//        db.open();
//
//        mPlayButton = (Button) findViewById(R.id.main_menu_play_button);
//        mPlayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Cursor cursor  = db.getAllRows("animals");
//                WordDictionary dictionary = new WordDictionary();
//                while(!cursor.isAfterLast()) {
//                    String word = cursor.getString(
//                            cursor.getColumnIndexOrThrow("word"));
//                    String translation = cursor.getString(
//                            cursor.getColumnIndexOrThrow("translation"));
//                    dictionary.add(word, translation);
//                    cursor.moveToNext();
//                }
//                cursor.close();
//                Intent intent = WordListsActivity.newIntent(
//                        MainMenuActivity.this, dictionary);
//                startActivity(intent);
//            }
//        });

        Button playButton = (Button) findViewById(R.id.main_menu_play_button);
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent (MainMenuActivity.this, SetSudokuSizeActivity.class);
            startActivity(intent);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupTutorialButton();

        button1=findViewById(R.id.main_menu_word_bank_button);

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, WordListsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupTutorialButton() {
        ImageView tutorialBtn = findViewById(R.id.tutorialBtn);
        tutorialBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainMenuActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }

}