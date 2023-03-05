package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;



import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    DBAdapter db;
    Button button1;
    private Button mPlayButton;
    SwitchCompat mDarkSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        db = new DBAdapter(this);
        db.open();

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

        //check for dark or light mode
        mDarkSwitch = findViewById(R.id.darkSwitch);

        mDarkSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences("darkSwitch_Val", mDarkSwitch.isChecked());
                if (mDarkSwitch.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                    savePreferences("darkSwitch_Val", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));
                    savePreferences("darkSwitch_Val", false);
                }
            }
        });
        loadSavedPreferences();

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

    //save switch state
    private void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mDarkSwitch.setChecked(sharedPreferences.getBoolean("value", false));
        if (mDarkSwitch.isChecked()) {
            mDarkSwitch.setChecked(true);
        } else {
            mDarkSwitch.setChecked(false);
        }
    }

    //save preferences
    private void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

}