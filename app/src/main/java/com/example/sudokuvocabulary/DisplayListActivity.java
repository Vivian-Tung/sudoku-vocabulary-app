package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayListActivity extends MenuForAllActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView timer = findViewById(R.id.TimerText);
        timer.setVisibility(View.GONE);

        String tableName = getIntent().getStringExtra(getString(R.string.new_table_name_key));
        TextView activityTitle = findViewById(R.id.display_list_name_text);
        activityTitle.setText(tableName);

        String[] words = getIntent().getStringArrayExtra(
                getString(R.string.words_key));
        String[] translations = getIntent().getStringArrayExtra(
                getString(R.string.translations_key));

        WordDictionary dictionary = new WordDictionary(words, translations);
        LinearLayout layout = findViewById(R.id.display_list_word_view);

        for (String word: dictionary.getWordsAsArray()) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            textView.setText(word);
            textView.setTextSize(24);
            layout.addView(textView);
        }

        Button backButton = findViewById(R.id.display_list_back_button);
        backButton.setOnClickListener(view -> {
            String categoryKey = getString(R.string.category_key);
            Intent intent = new Intent(DisplayListActivity.this,
                    AddWordsActivity.class);
            intent.putExtra(getString(R.string.new_table_name_key), tableName);
            intent.putExtra(getString(R.string.words_key), words);
            intent.putExtra(getString(R.string.translations_key), translations);
            intent.putExtra(categoryKey, getIntent().getStringExtra(categoryKey));
            startActivity(intent);
        });
    }
}