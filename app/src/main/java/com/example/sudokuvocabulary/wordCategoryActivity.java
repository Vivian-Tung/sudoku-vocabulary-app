package com.example.sudokuvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class wordCategoryActivity extends AppCompatActivity {

    Button animal_category_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        animal_category_button = (Button) findViewById(R.id.animal_list_button);

        animal_category_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(wordCategoryActivity.this, animalCategoryView.class);
                startActivity(intent);
            }
        });
    }
}
