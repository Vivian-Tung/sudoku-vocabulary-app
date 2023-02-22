package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/*steps to using DB
1. instantiate DB adapter
2. open DB
3. use get, insert, delete ... to change data
4. close the DB
 */

public class wordBankActivity extends AppCompatActivity {

    //want schema always available
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_category);
        openDB();

        //readWordData();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    //word bank data

    private List<wordSample> wordSamples = new ArrayList<>();


    private void readWordData() { //maybe remove later?
        InputStream is = getResources().openRawResource(R.raw.test_data);
        //read line by line -> buffered reader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        //loop to read lines at once
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                //split by comma
                String[] tokens = line.split(",");

                //read the data
                wordSample sample = new wordSample();
                sample.setWord(tokens[0]);
                sample.setTranslation(tokens[1]);
                wordSamples.add(sample);

                Log.d("WordBankActivity", "Just created: " + sample);
            }
        }  catch (IOException e){
            Log.wtf("WordBankActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }
    }


    //database
    private void openDB() {
        myDb = new DBAdapter(this); //need activity to interact
        myDb.open();
    }
    private void closeDB() {
        myDb.close();
    }

    private void displayText(String message) {
        TextView textView = (TextView) findViewById(R.id.add_word_text_view);
        textView.setText(message);
    }

    //interaction with db -> actually creating the lists bc i am going to assume words already on display
    public void onClick_AddRecord(View v) {
        displayText("Clicked add new record!");
        //but actually i need to make it darkened button

        long newId = myDb.insertRow("Dog","狗" );

        //query for record just added -> use ID
        Cursor cursor = myDb.getRow(newId);
        displayRecordSet(cursor);

    }


    public void onClick_ClearAll(View v) {
        displayText("Clicked clear all!");
        myDb.deleteAll();
    }


    public void onClick_DisplayRecords(View v) {
        displayText("Clicked display record");
        //cursor can step thru record
        Cursor cursor = myDb.getAllRows();
        displayRecordSet(cursor);
    }

    //display entire record set to the screen
    private void displayRecordSet(Cursor cursor) {
        String message = "";
        //populate message from cursor

        //reset cursor to start, checking to see if theres date:
        if (cursor.moveToFirst()) {
            do {
                //process data:
                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String word = cursor.getString(DBAdapter.COL_WORD);
                String translation = cursor.getString(DBAdapter.COL_TRANSLATION);

                //append data to message:
                message += "id=" + id
                        + ", word =" + word
                        + ", translation =" + translation
                        + "\n";
            } while (cursor.moveToNext());
        }
        //close cursor to avoid a resource leak
        cursor.close();

        displayText(message);
    }



}

