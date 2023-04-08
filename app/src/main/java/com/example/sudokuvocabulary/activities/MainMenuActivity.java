package com.example.sudokuvocabulary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.sudokuvocabulary.R;
import com.example.sudokuvocabulary.fragments.OverrideSaveDialogFragment;
import com.example.sudokuvocabulary.utils.PrefUtils;
import com.example.sudokuvocabulary.utils.SaveFileUtil;

public class MainMenuActivity extends MenuForAllActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button playButton = findViewById(R.id.main_menu_play_button);
        playButton.setOnClickListener(v -> {
            if (SaveFileUtil.saveExists(this, getString(R.string.save_game_file))) {
                DialogFragment newFragment = OverrideSaveDialogFragment.newInstance(0);
                newFragment.show(getSupportFragmentManager().beginTransaction(), "dialog");
            } else {
                PrefUtils.saveBoolPreference(this, getString(R.string.save_game_file), false);
                Intent intent = new Intent(this, SelectModeActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView timer = findViewById(R.id.TimerText);
        timer.setVisibility(View.GONE);

        String saveFileName = getString(R.string.save_game_file);
        Button loadButton = findViewById(R.id.load_save_button);
        Log.d("MainMenuActivity", "Checking save file");
        loadButton.setEnabled(SaveFileUtil.saveExists(this, saveFileName));
        loadButton.setOnClickListener(view -> {
            PrefUtils.saveBoolPreference(this, getString(R.string.save_game_key), true);
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
}