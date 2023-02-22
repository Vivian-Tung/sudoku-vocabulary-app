package com.example.sudokuvocabulary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class animalCategoryView extends AppCompatActivity {
    private static final int NUM_ROWS = 8;
    private static final int NUM_COLS = 3;
    public List<wordSample> wordSamples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_category);

        readWordData();
        populateButtons();

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
                Button button = new Button(this);

                //set layout
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f ));//scaling weight

                //put text on button -> call read data fcn
                button.setText("" + wordSamples.get(FINAL_COL  + FINAL_ROW * NUM_COLS).getWord());


                //padding
                button.setPadding(0,0,0,0);


                //action for button clicked
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridButtonCLicked(wordSamples.get(FINAL_COL  + FINAL_ROW * NUM_COLS).getWord());
                    }


                });
                tableRow.addView(button);

                }
        }
    }
    private void gridButtonCLicked(String word) {
        Toast.makeText(this, word + " added", Toast.LENGTH_SHORT).show();
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
                wordSample sample = new wordSample();
                sample.setWord(tokens[0]);
                sample.setTranslation(tokens[1]);
                wordSamples.add(sample);

                //android.util.Log.d("myTag", "Just created: " + sample);
            }
        }  catch (IOException e){
            android.util.Log.wtf("animalCategoryView", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }

}
