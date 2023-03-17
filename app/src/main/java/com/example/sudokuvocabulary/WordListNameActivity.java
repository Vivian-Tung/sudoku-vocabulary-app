package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WordListNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupTutorialButton();
        TextView timer = findViewById(R.id.TimerText);
        timer.setVisibility(View.GONE);

        EditText text = findViewById(R.id.table_name_input);
        Button confirmButton = findViewById(R.id.confirm_name_button);

        confirmButton.setOnClickListener(view -> {
            String listName = text.getText().toString();

            if (listName.length() == 0) {
                Toast.makeText(this,
                        "List name cannot be empty",
                        Toast.LENGTH_SHORT).show();

            } else if (listName.contains(" ")) {
                Toast.makeText(this,
                        "List name cannot have spaces",
                        Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(
                        WordListNameActivity.this, WordCategoryActivity.class);
                intent.putExtra(getString(R.string.new_table_name_key), listName);
                startActivity(intent);
            }
        });
    }

    private void setupTutorialButton() {
        ImageView tutorialBtn = findViewById(R.id.tutorialBtn);
        tutorialBtn.setOnClickListener(view -> {

            Intent intent = new Intent(WordListNameActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }
}