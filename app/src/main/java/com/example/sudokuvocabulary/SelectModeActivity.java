package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SelectModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode);

        // Key to store mode selected,
        // false = normal, true = listening
        final String modeKey = getString(R.string.mode_key);

        Button normalMode = findViewById(R.id.select_mode_normal_button);
        normalMode.setOnClickListener(view -> {
            Intent intent = new Intent(
                    SelectModeActivity.this,
                    SetSudokuSizeActivity.class
            );
            intent.putExtra(modeKey, false);
            startActivity(intent);
        });

        Button listenMode = findViewById(R.id.select_mode_listen_button);
        listenMode.setOnClickListener(view -> {
            Intent intent = new Intent(
                    SelectModeActivity.this,
                    SetSudokuSizeActivity.class
            );
            intent.putExtra(modeKey, true);
            startActivity(intent);
        });

        Button backButton = findViewById(R.id.select_mode_back_button);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(
                    SelectModeActivity.this,
                    MainMenuActivity.class
            );
            intent.putExtra(modeKey, true);
            startActivity(intent);
        });
    }
}