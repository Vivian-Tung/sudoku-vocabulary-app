package com.example.sudokuvocabulary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Random;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {
    private QuestionCardView mQuestionCard;
    private SudokuView mSudokuView;
    private SudokuModel mSudokuModel;

    private HashMap<String, String> mWordBank = new HashMap<>();
    private int[] mCellPicked = new int[3];
    private String mChoicePicked;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        mSudokuModel = new SudokuModel();

        mQuestionCard = findViewById(R.id.questionCardView);
        mQuestionCard.setNumberOfChoices(mSudokuModel.getGridSize());
        mQuestionCard.setNumberOfChoices(mSudokuModel.getGridSize());
        mQuestionCard.setVisibility(View.GONE);

        Button[] wordChoiceButtons = mQuestionCard.getWordChoiceButtons();
        for (Button choice: wordChoiceButtons) {
            choice.setOnClickListener(this);
        }

        mSudokuView = findViewById(R.id.sudokuGridView);
        mSudokuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean isValid = false;
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    mCellPicked[0] = (int) (Math.ceil(motionEvent.getY() / mSudokuView.getCellSize()))-1;
                    mCellPicked[1] = (int) (Math.ceil(motionEvent.getX() / mSudokuView.getCellSize()))-1;
                    mCellPicked[2] = mSudokuModel.getValueAt(mCellPicked[0], mCellPicked[1]);

                    isValid = true;

                    // Word bank for testing
                    String[][] words = {
                            {"Apples", "苹果"},
                            {"Oranges", "橙子"},
                            {"Watermelon", "西瓜"},
                            {"Grapes", "葡萄"},
                            {"Fruits", "水果"},
                            {"Banana", "香蕉"},
                            {"Dragon Eyes", "龙眼"},
                            {"Mango", "芒果"},
                            {"Plum", "李子"}
                    };

                    for (String[] wordPair: words) {
                        mWordBank.put(wordPair[0], wordPair[1]);
                    }

                    Random random = new Random();
                    mQuestionCard.setWordPrompt(words[random.nextInt(words.length)][0]);
                    mQuestionCard.setWordChoiceButtonsText(words);

                    mQuestionCard.invalidate();
                    mQuestionCard.setVisibility(View.VISIBLE);
                }
                return isValid;
            }
        });
    }

    @Override
    public void onClick(View view) {
        mChoicePicked = (String) ((Button) view).getText();

        if (isCorrect()) {
            mSudokuView.setCellToDraw(mCellPicked[0], mCellPicked[1], mCellPicked[2]);
            mSudokuView.invalidate();
        }
        mQuestionCard.setVisibility(View.GONE);
    }

    private boolean isCorrect() {
        return mChoicePicked.equals(mWordBank.get(mQuestionCard.getWordPrompt()));
    }
}