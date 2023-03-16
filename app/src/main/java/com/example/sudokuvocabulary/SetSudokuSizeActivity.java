package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

public class SetSudokuSizeActivity extends AppCompatActivity implements View.OnClickListener{

    DBAdapter db;
    SwitchCompat mDarkSwitch;
    private PrefManager mPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_sudoku_size);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupTutorialButton();

        mPrefManager = new PrefManager(this);
        // Key containing dark mode switch boolean value
        final String themeSwitchKey = getString(R.string.theme_value_key);


        //check for dark or light mode
        boolean themeSwitchState = mPrefManager.loadSavedPreferences(this, themeSwitchKey);
        mDarkSwitch = findViewById(R.id.darkSwitch);
        // Restore the switch value to the previous setting
        mDarkSwitch.setChecked(themeSwitchState);

        mDarkSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDarkSwitch.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                    mPrefManager.savePreferences(themeSwitchKey, true);
                } else {
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                    mPrefManager.savePreferences(themeSwitchKey, false);
                }
            }
        });

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
                        SetSudokuSizeActivity.this, dictionary);
                startActivity(intent);
    }

}