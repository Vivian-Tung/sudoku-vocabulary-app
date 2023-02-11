package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameCompleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complete);

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameCompleteActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

        Button restartButton = (Button) findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameCompleteActivity.this, SudokuActivity.class);
                startActivity(intent);
            }
        });
    }
}