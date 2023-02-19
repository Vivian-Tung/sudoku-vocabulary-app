package com.example.sudokuvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity2 extends AppCompatActivity {

    Button new_word_list_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);

        new_word_list_button = (Button) findViewById(R.id.create_new_word_list_button);

        new_word_list_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity2.this, wordBankActivity.class);
            startActivity(intent);
        });

    }
}