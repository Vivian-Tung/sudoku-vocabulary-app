package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SudokuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        Bundle bundle = new Bundle();
        SudokuModel sudokuModel = new SudokuModel();
        bundle.putSerializable("sudoku", sudokuModel);

        SudokuView sudokuView = findViewById(R.id.sudokuGridView);
        sudokuView.setData(bundle);
        sudokuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String tag = "SudokuActivity";
                int cellRow = (int) Math.ceil(motionEvent.getY()/sudokuView.getCellSize());
                int cellColumn = (int) Math.ceil(motionEvent.getX()/sudokuView.getCellSize());
                Log.d(tag, "Cell Row: " + cellRow);
                Log.d(tag, "Cell Column: " + cellColumn);
                return false;
            }
        });
    }
}