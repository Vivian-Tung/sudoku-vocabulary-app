package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import java.util.concurrent.TimeUnit;

public class GameCompleteActivity extends MenuForAllActivity {

    private String[] mWords, mTranslations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complete);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWords = getIntent().getStringArrayExtra(getString(R.string.words_key));
        mTranslations = getIntent().getStringArrayExtra(getString(R.string.translations_key));

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(GameCompleteActivity.this, MainMenuActivity.class);
            startActivity(intent);
        });

        Button restartButton = (Button) findViewById(R.id.restart_button);
        restartButton.setOnClickListener(view -> {
            Intent intent = newIntent(
                    GameCompleteActivity.this, mWords, mTranslations);
            startActivity(intent);
        });

        long duration = (long) getIntent().getDoubleExtra(
                getString(R.string.time_key), 0);

        TextView durationTextView = findViewById(R.id.duration_textview);

        int minutes = (int)(duration / 60);
        int seconds = (int) (duration % 60);

        String timeText = "Time taken to complete: ";
        if (minutes > 0) timeText += minutes + " minutes ";
        timeText += seconds + " seconds";

        durationTextView.setText(timeText);
    }
    @NonNull
    public Intent newIntent(Context packageContext, String[] words, String[] translations) {
        Intent intent = new Intent(packageContext, SudokuActivity.class);
        intent.putExtra(getString(R.string.words_key), words);
        intent.putExtra(getString(R.string.translations_key), translations);
        return intent;
    }

}