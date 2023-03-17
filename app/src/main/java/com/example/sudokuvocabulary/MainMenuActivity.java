package com.example.sudokuvocabulary;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

public class MainMenuActivity extends AppCompatActivity {

    private PrefManager mPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mPrefManager = new PrefManager(this);
        // Key containing dark mode switch boolean value
        final String themeSwitchKey = getString(R.string.theme_value_key);


        Button playButton = (Button) findViewById(R.id.main_menu_play_button);
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent (MainMenuActivity.this, SetSudokuSizeActivity.class);
            startActivity(intent);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SwitchCompat mDarkSwitch = findViewById(R.id.darkSwitch);

        //check for dark or light mode
        boolean themeSwitchState = mPrefManager.loadSavedPreferences(this, themeSwitchKey);
        // Restore the switch value to the previous setting
        mDarkSwitch.setChecked(themeSwitchState);

        //trying to change the listener
        mDarkSwitch.setOnCheckedChangeListener((compoundButton, themeSwitchState1) -> {
            if (compoundButton.isPressed()) {
                mPrefManager.savePreferences(themeSwitchKey, themeSwitchState1);
                recreate();
            }
        });

        setupTutorialButton();

        Button button1 = findViewById(R.id.main_menu_word_bank_button);

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