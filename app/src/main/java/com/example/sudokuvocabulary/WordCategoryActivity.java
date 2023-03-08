package com.example.sudokuvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WordCategoryActivity extends AppCompatActivity implements View.OnClickListener {
    String tableName;
    DBAdapter db;
    ViewGroup buttonLayout;
    ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_category);
        tableName = getIntent().getStringExtra(getString(R.string.new_table_name_key));

        db = new DBAdapter(this);
        db.open();
        categories = db.getAllCategories();
        buttonLayout = (ViewGroup) findViewById(R.id.gray_box);
        for (String category: categories) {
            addCategoryButton(category);
        }

        Button backButton = findViewById(R.id.word_category_back_button);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(findViewById(R.id.word_category_back_button))) {
            onBackButtonClick();
        } else {
            onCategoryButtonClick(view);
        }
    }

    private void onBackButtonClick() {
        Intent intent = new Intent(WordCategoryActivity.this, WordListNameActivity.class);
        startActivity(intent);
    }

    // Passes the category clicked to the word selection activity
    private void onCategoryButtonClick(View view) {
        Button button = (Button) view;
        Intent intent = new Intent(WordCategoryActivity.this,
                AddWordsActivity.class);
        intent.putExtra(getString(R.string.new_table_name_key), tableName);
        intent.putExtra(getString(R.string.category_key), button.getText().toString());
        startActivity(intent);
    }

    // Adds a new button to the view with the given category name
    private void addCategoryButton(String categoryName) {
        Button categoryButton = new Button(this);
        categoryButton.setText(categoryName);
        categoryButton.setOnClickListener(this);
        buttonLayout.addView(categoryButton);
    }
}
