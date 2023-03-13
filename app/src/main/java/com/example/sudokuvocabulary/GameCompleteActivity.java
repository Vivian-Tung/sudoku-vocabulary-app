package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class GameCompleteActivity extends AppCompatActivity {

    private String[] mWords, mTranslations;
    private long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complete);

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
        long startTime = getIntent().getLongExtra("startTime", 0);

        long duration = System.currentTimeMillis() - startTime;

        TextView durationTextView = findViewById(R.id.duration_textview);

        long minutes = (long)((duration / (1000*60)) % 60);

       // long durationINminutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        durationTextView.setText("Time taken to complete: " + minutes + " seconds ");
    }
    @NonNull
    public Intent newIntent(Context packageContext, String[] words, String[] translations) {
        Intent intent = new Intent(packageContext, SudokuActivity.class);
        intent.putExtra(getString(R.string.words_key), words);
        intent.putExtra(getString(R.string.translations_key), translations);
        return intent;
    }
}