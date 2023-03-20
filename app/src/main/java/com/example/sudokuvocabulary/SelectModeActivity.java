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


        Button normalMode = findViewById(R.id.select_mode_normal_button);
        normalMode.setOnClickListener(view -> {
            Intent intent = new Intent(
                    SelectModeActivity.this,
                    SudokuActivity.class
            );
            startActivity(intent);
        });

        Button listenMode = findViewById(R.id.select_mode_listen_button);
        listenMode.setOnClickListener(view -> {
            Intent intent = new Intent(
                    SelectModeActivity.this,
                    SudokuActivity.class
            );
            startActivity(intent);
        });
    }
}