package com.example.sudokuvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WordCategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button animal_category_button;
    String tableName;
    DBAdapter db;
    ViewGroup buttonLayout;
    ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        tableName = getIntent().getStringExtra(getString(R.string.new_table_name_key));

        db = new DBAdapter(this);
        db.open();
        categories = db.getAllCategories();
        buttonLayout = (ViewGroup) findViewById(R.id.gray_box);
        for (String category: categories) {
            addCategoryButton(category);
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(WordCategoryActivity.this,
        AnimalCategoryActivity.class);
        intent.putExtra(getString(R.string.new_table_name_key), tableName);
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
