package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class WordListNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list_name);

        EditText text = findViewById(R.id.table_name_input);

        Button confirmButton = findViewById(R.id.confirm_name_button);
        confirmButton.setOnClickListener(view -> {
            String listName = text.getText().toString();
            Intent intent = new Intent(
                    WordListNameActivity.this, WordCategoryActivity.class);
            intent.putExtra(getString(R.string.new_table_name_key), listName);
            startActivity(intent);
        });
    }
}