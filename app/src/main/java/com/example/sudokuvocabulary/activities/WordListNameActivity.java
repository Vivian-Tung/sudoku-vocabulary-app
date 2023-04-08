package com.example.sudokuvocabulary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sudokuvocabulary.R;
import com.example.sudokuvocabulary.adapters.DBAdapter;

import java.util.Locale;

public class WordListNameActivity extends MenuForAllActivity {

    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText text = findViewById(R.id.table_name_input);
        Button confirmButton = findViewById(R.id.confirm_name_button);

        confirmButton.setOnClickListener(view -> {
            String listName = text.getText().toString();

            db = new DBAdapter(this);
            db.open();
            if (db.getTableNames().contains(listName.toLowerCase(Locale.ROOT))) {
                Toast.makeText(this,
                        "List name already used",
                        Toast.LENGTH_SHORT).show();
            } else if (listName.matches("[A-Za-z0-9]+")) {
                Intent intent = new Intent(
                        WordListNameActivity.this, WordCategoryActivity.class);
                intent.putExtra(getString(R.string.new_table_name_key), listName);
                startActivity(intent);
            } else if (listName.length() == 0) {
                Toast.makeText(this,
                        "List name cannot be empty",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "List name cannot have spaces or special characters",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setContentView() {
        this.setContentView(R.layout.activity_word_list_name);
        TextView timerText = findViewById(R.id.TimerText);
        timerText.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }


}