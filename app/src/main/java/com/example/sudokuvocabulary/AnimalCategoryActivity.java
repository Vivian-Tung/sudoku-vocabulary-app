package com.example.sudokuvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class AnimalCategoryActivity extends AppCompatActivity {
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 3;
    private WordDictionary words = new WordDictionary();
    private WordDictionary wordsAdded;
    private DBAdapter db;
    private String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_category);

        if (getIntent().getStringArrayExtra(getString(R.string.words_key)) != null) {
            String[] wordsArray = getIntent()
                    .getStringArrayExtra(getString(R.string.words_key));
            String[] translationsArray = getIntent()
                    .getStringArrayExtra(getString(R.string.translations_key));
            wordsAdded = new WordDictionary(wordsArray, translationsArray);
        } else {
            wordsAdded = new WordDictionary();
        }
        tableName = getIntent().getStringExtra(getString(R.string.new_table_name_key));

        readWordData();
        populateButtons();

        // Display List Button
        Button displayListButton = findViewById(R.id.display_list_button);
        displayListButton.setOnClickListener(view -> {
            Intent intent = new Intent(
                    AnimalCategoryActivity.this, DisplayListActivity.class);

            intent.putExtra(getString(R.string.new_table_name_key), tableName);
            intent.putExtra(getString(R.string.words_key),
                    wordsAdded.getWordsAsArray());
            intent.putExtra(getString(R.string.translations_key),
                    wordsAdded.getTranslationsAsArray());

            startActivity(intent);
        });

        Button playButton = findViewById(R.id.animal_category_play_button);
        playButton.setOnClickListener(view -> {

            if (wordsAdded.getLength() != 9) {
                Toast.makeText(this,
                        "Exactly nine words must be selected",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Add the new word list to database
                db = new DBAdapter(this);
                db.open();
                db.newTable(tableName);
                for (WordSample wordSample : wordsAdded.getWords()) {
                    db.insertRow(wordSample.getWord(), wordSample.getTranslation(), tableName);
                }

                // Launch the sudoku game
                Intent intent = new Intent(AnimalCategoryActivity.this,
                        SudokuActivity.class);
                intent.putExtra(getString(R.string.words_key),
                        wordsAdded.getWordsAsArray());
                intent.putExtra(getString(R.string.translations_key),
                        wordsAdded.getTranslationsAsArray());
                startActivity(intent);
            }
        });

        Button prevButton = findViewById(R.id.animal_category_prev_button);
        prevButton.setOnClickListener(view -> {
            Intent intent = new Intent(AnimalCategoryActivity.this,
                    WordCategoryActivity.class);
            intent.putExtra(getString(R.string.new_table_name_key), tableName);
            startActivity(intent);
        });
    }

    private void populateButtons() {
        //define table
        TableLayout table = (TableLayout) findViewById(R.id.table_for_buttons);
        for (int row = 0; row < NUM_ROWS; row++) {

            //add new row for each row
            TableRow tableRow = new TableRow(this);
            //set layout
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f ));//scaling weight
            //add row
            table.addView(tableRow);
            for (int col = 0; col < NUM_COLS; col++) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                //add new button
                ToggleButton button = new ToggleButton(this);

                //set layout
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f ));//scaling weight

                //put text on button -> call read data fcn
                String currentWord = words.getWord(FINAL_COL + FINAL_ROW * NUM_COLS);
                button.setText(currentWord);
                button.setTextOn(currentWord);
                button.setTextOff(currentWord);
                button.setChecked(wordsAdded.contains(currentWord));

                // Generate an ID for the button
                button.setId(View.generateViewId());

                //padding
                button.setPadding(0,0,0,0);


                //action for button clicked
                button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        int index = FINAL_COL + FINAL_ROW * NUM_COLS;
                        if (isChecked) {
                            gridButtonCLicked(words.getWord(index));
                        } else {
                            wordsAdded.remove(words.getWord(index));
                        }
                    }
                });

                tableRow.addView(button);
            }
        }
    }
    private void gridButtonCLicked(String word) {
        Toast.makeText(this, word + " added", Toast.LENGTH_SHORT).show();
        wordsAdded.add(word, words.findTranslation(word));
        if (wordsAdded.getLength() >= 9) {
            Toast.makeText(this, "Nine words have been selected",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void readWordData() {
        //android.util.Log.d("myTag", "testing readWordData function");
        //can change data set depending on what the user pressed in the previous screen
        InputStream is = getResources().openRawResource(R.raw.test_data);
        //read line by line -> buffered reader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        //loop to read lines at once
        String line = "";

        try {
            while ((line = reader.readLine()) != null) {
                //android.util.Log.d("myTag", "line: " + line);

                //split by comma
                String[] tokens = line.split(",");

                //read the data
                words.add(tokens[0], tokens[1]);

                //android.util.Log.d("myTag", "Just created: " + sample);
            }
        }  catch (IOException e){
            android.util.Log.wtf("animalCategoryView", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }

}
