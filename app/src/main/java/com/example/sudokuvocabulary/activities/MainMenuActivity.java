package com.example.sudokuvocabulary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sudokuvocabulary.R;
import com.example.sudokuvocabulary.utils.PrefUtils;
import com.example.sudokuvocabulary.utils.SaveFileUtil;

public class MainMenuActivity extends MenuForAllActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button playButton = findViewById(R.id.main_menu_play_button);
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent (MainMenuActivity.this, SelectModeActivity.class);
            startActivity(intent);
        });

        Button loadButton = findViewById(R.id.load_save_button);
        boolean saveExists = PrefUtils.loadBoolPreference(this, getString(R.string.save_game_key));
        loadButton.setEnabled(saveExists);
        loadButton.setOnClickListener(view -> {
            Intent intent;
            if ((Boolean) SaveFileUtil.readFromSave(this, getString(R.string.save_game_file), SaveFileUtil.SaveObjects.GAME_MODE)) {
                intent = new Intent(MainMenuActivity.this, ListenModeActivity.class);
            } else {
                intent = new Intent(MainMenuActivity.this, SudokuActivity.class);
            }
            startActivity(intent);
        });

        Button wordBankButton = findViewById(R.id.main_menu_word_bank_button);
        wordBankButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, WordListsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main_menu);
        TextView timerText = findViewById(R.id.TimerText);
        timerText.setVisibility(View.GONE);
    }
}