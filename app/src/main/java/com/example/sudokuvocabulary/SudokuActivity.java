package com.example.sudokuvocabulary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
        sudokuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean isValid = false;
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    int cellRow = (int) (Math.ceil(motionEvent.getY() / sudokuView.getCellSize()))-1;
                    int cellColumn = (int) (Math.ceil(motionEvent.getX() / sudokuView.getCellSize()))-1;
                    int cellValue = sudokuModel.getValueAt(cellRow, cellColumn);
                    sudokuView.setCellToDraw(cellRow, cellColumn, cellValue);
                    isValid = true;
                    // TODO: Display pop-up with prompt of vocab questions
                    boolean isCorrect = true;
                    if (isCorrect) {
                        sudokuView.invalidate();
                    }
                }
                return isValid;
            }
        });
    }
}