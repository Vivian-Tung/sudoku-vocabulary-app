package com.example.sudokuvocabulary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

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
    private static final int NUM_ROWS = 7;
    private static final int NUM_COLS = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_category);

        populateButtons();
        readWordData();
    }

    //public List<wordSample> wordSamples = new ArrayList<>();

    //test printing
    public class Arrlist {

        public void main(String[] args) {

            ArrayList arrlist=new ArrayList();
            arrlist.add("Sunday");
            arrlist.add("Monday");
            arrlist.add("Tuesday");
            arrlist.add("Wednesday");
            arrlist.add("Thursday");
            arrlist.add("Friday");
            arrlist.add("Saturday");

            //using for loop
            System.out.println("Using For Loop\n ");
            for (int i = 0; i < arrlist.size();i++)
            {
                Log.wtf("test", (arrlist.get(i)).toString());
            }
        }
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
                //add new button
                Button button = new Button(this);

                //set layout
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f ));//scaling weight

                //put text on button -> call read data from wordbankactivity class
                //button.setText((CharSequence) wordSamples.get(NUM_ROWS*NUMS_COLS));
                button.setText("hello pls help");

                //padding
                button.setPadding(0,0,0,0);


                //action for button clicked
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridButtonCLicked();
                    }


                });
                tableRow.addView(button);

                }
        }
    }
    private void gridButtonCLicked() {
        Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show();
    }

    private void readWordData() {
        Log.d("myTag", "testing readWordData function");
//        InputStream is = getResources().openRawResource(R.raw.test_data);
//        //read line by line -> buffered reader
//        BufferedReader reader = new BufferedReader(
//                new InputStreamReader(is, Charset.forName("UTF-8"))
//        );
//        //loop to read lines at once
//        String line = "";
//        try {
//            while ((line = reader.readLine()) != null) {
//                //split by comma
//                String[] tokens = line.split(",");
//
//                //read the data
//                wordSample sample = new wordSample();
//                sample.setWord(tokens[0]);
//                //sample.setTranslation(tokens[1]);
//                wordSamples.add(sample);
//
//                Log.d("animalCategoryView", "Just created: " + sample);
//            }
//        }  catch (IOException e){
//            Log.wtf("animalCategoryView", "Error reading data file on line" + line, e);
//            e.printStackTrace();
//        }
    }

}
