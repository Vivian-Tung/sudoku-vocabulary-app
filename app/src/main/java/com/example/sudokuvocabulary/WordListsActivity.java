package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WordListsActivity extends AppCompatActivity implements View.OnClickListener {

    Button new_word_list_button;

    private String[] categories = {"Animals", "Fruits"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_lists);

        new_word_list_button = (Button) findViewById(R.id.create_new_word_list_button);

        new_word_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordListsActivity.this, wordBankActivity.class);
                startActivity(intent);
            }
        });

        WordListView existingWordLists = findViewById(R.id.existing_word_lists);
        existingWordLists.setWordListText(categories);
        for (Button button: existingWordLists.getListButtons()) {
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {

    }
}