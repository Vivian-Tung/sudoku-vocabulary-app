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
    private Button mPlayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        db = new DBAdapter(this);
        db.open();

        // Initialize default word list into database if it does not exist already
        ArrayList<String> tables = db.getTableNames();
        if (tables.contains("animals") && db.getAllRows("animals").getCount() == 0) {
            db.insertRow("dog", "狗");
            db.insertRow("cat", "猫");
            db.insertRow("sheep", "羊");
            db.insertRow("frog", "青蛙");
            db.insertRow("pig", "猪");
            db.insertRow("fish", "鱼");
            db.insertRow("bird", "鸟");
            db.insertRow("bear", "熊");
            db.insertRow("wolf", "狼");
        }

        mPlayButton = (Button) findViewById(R.id.main_menu_play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor  = db.getAllRows("animals");
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
                Intent intent = WordListsActivity.newIntent(
                        MainMenuActivity.this, dictionary);
                startActivity(intent);
            }
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
        db.close();
    }

    private void setupTutorialButton() {
        ImageView tutorialBtn = findViewById(R.id.tutorialBtn);
        tutorialBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainMenuActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }

}