package com.example.sudokuvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

public class MainMenuActivity extends MenuForAllActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button playButton = findViewById(R.id.main_menu_play_button);
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent (MainMenuActivity.this, SelectModeActivity.class);
            startActivity(intent);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView timer = findViewById(R.id.TimerText);
        timer.setVisibility(View.GONE);

        Button button1 = findViewById(R.id.main_menu_word_bank_button);

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, WordListsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}