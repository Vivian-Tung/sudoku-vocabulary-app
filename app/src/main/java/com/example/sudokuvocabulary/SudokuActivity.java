package com.example.sudokuvocabulary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        SudokuModel sudokuModel = new SudokuModel();

        QuestionCardView questionCard = findViewById(R.id.questionCardView);
        questionCard.setNumberOfChoices(sudokuModel.getGridSize());
        questionCard.setNumberOfChoices(sudokuModel.getGridSize());
        questionCard.setVisibility(View.GONE);
        Button[] wordChoiceButtons = questionCard.getWordChoiceButtons();

        SudokuView sudokuView = findViewById(R.id.sudokuGridView);
        sudokuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean isValid = false;
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    int cellRow = (int) (Math.ceil(motionEvent.getY() / sudokuView.getCellSize()))-1;
                    int cellColumn = (int) (Math.ceil(motionEvent.getX() / sudokuView.getCellSize()))-1;
                    int cellValue = sudokuModel.getValueAt(cellRow, cellColumn);

                    isValid = true;

                    // Word bank for testing
                    String[][] wordBank = {
                            {"Apples", "ping guo"},
                            {"Oranges", "cheng zi"},
                            {"Watermelon", "ci gua"},
                            {"Grapes", "pu tao"},
                            {"Fruits", "shui guo"},
                            {"Banana", "xiang jiao"},
                            {"Dragon Eyes", "long nan"},
                            {"Mango", "mang guo"},
                            {"Plum", "li zi"}
                    };

                    Random random = new Random();
                    questionCard.setWordPrompt(wordBank[random.nextInt(wordBank.length)][0]);
                    questionCard.setWordChoiceButtonsText(wordBank);

                    questionCard.invalidate();
                    questionCard.setVisibility(View.VISIBLE);
                    boolean isCorrect = true; // Set to true for now
                    if (isCorrect) {
                        sudokuView.setCellToDraw(cellRow, cellColumn, cellValue);
                        sudokuView.invalidate();
                    }
                }
                return isValid;
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}