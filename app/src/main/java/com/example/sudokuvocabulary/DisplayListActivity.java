package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        String tableName = getIntent().getStringExtra(getString(R.string.new_table_name_key));
        TextView activityTitle = findViewById(R.id.display_list_name_text);
        activityTitle.setText(tableName);

        String[] words = getIntent().getStringArrayExtra(
                getString(R.string.words_key));
        String[] translations = getIntent().getStringArrayExtra(
                getString(R.string.translations_key));

        WordDictionary dictionary = new WordDictionary(words, translations);
        LinearLayout layout = findViewById(R.id.display_list_layout);

        for (String word: dictionary.getWordsAsArray()) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            textView.setText(word);
            layout.addView(textView);
        }

        Button backButton = new Button(this);
        backButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        backButton.setText(getString(R.string.activity_word_list_back_button));
        layout.addView(backButton);
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayListActivity.this,
                    AnimalCategoryActivity.class);
            intent.putExtra(getString(R.string.new_table_name_key), tableName);
            intent.putExtra(getString(R.string.words_key), words);
            intent.putExtra(getString(R.string.translations_key), translations);
            startActivity(intent);
        });
    }
}