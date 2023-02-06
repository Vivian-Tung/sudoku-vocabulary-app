package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
    }
}