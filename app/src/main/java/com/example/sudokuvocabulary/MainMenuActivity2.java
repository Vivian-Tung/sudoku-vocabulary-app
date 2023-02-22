package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity2 extends AppCompatActivity {

    Button new_word_list_button;

    private String[] categories = {"Food", "Transportation", "Shops", "Animals"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu2);

        new_word_list_button = (Button) findViewById(R.id.create_new_word_list_button);

        new_word_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity2.this, wordBankActivity.class);
                startActivity(intent);
            }
        });

        WordListView existingWordLists = findViewById(R.id.existing_word_lists);
        existingWordLists.setWordListText(categories);

    }
}