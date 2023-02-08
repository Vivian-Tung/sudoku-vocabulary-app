package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SudokuActivity extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
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
                Log.d(tag, "Num at cell: " + sudokuModel.getValueAt(cellRow-1, cellColumn-1));

                // TODO: Call method from view to draw value at pressed grid cell
                return false;
            }
        });
    }
}