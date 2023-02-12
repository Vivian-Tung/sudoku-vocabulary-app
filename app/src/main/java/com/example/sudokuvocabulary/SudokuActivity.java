package com.example.sudokuvocabulary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

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
        mSudokuView = findViewById(R.id.sudokuGridView);

        mQuestionCard = findViewById(R.id.questionCardView);
        mQuestionCard.setNumberOfChoices(mSudokuModel.getGridSize());
        mQuestionCard.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            mSudokuModel.setGrid(SudokuModel.expand(savedInstanceState.getIntArray("GridAsArray")));
            mSudokuModel.setNumOfCellsFilled(savedInstanceState.getInt("NumOfCellsFilled"));
            mSudokuView.setCellsToDraw(SudokuModel.expand(savedInstanceState.getIntArray("CellsToDraw")));
            mQuestionCard.setVisibility((savedInstanceState.getBoolean("PopupVisible"))? View.VISIBLE:View.GONE);
            mSudokuView.invalidate();
        }

        Button[] wordChoiceButtons = mQuestionCard.getWordChoiceButtons();
        for (Button choice: wordChoiceButtons) {
            choice.setOnClickListener(this);
        }

        mSudokuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean isValid = false;
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN
                        && mQuestionCard.getVisibility() == View.GONE) {
                    int orientation = getResources().getConfiguration().orientation;

                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        mCellPicked[0] = (int) (Math.ceil(motionEvent.getY() / mSudokuView.getCellSize())) - 1;
                        mCellPicked[1] = (int) (Math.ceil(motionEvent.getX() / mSudokuView.getCellSize())) - 1;
                    } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        mCellPicked[0] = (int) (Math.ceil(motionEvent.getY() / mSudokuView.getCellSize())) - 1;
                        mCellPicked[1] = (int) ((Math.ceil(motionEvent.getX()) / mSudokuView.getCellSize()));
                    }
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

                    mQuestionCard.setWordPrompt(words[mSudokuModel.getValueAt(mCellPicked[0],
                            mCellPicked[1])-1][0]);
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
        String toastMessage;

        if (isCorrect()) {
            mSudokuModel.incrementCellsFilled();
            mSudokuView.setCellToDraw(mCellPicked[0], mCellPicked[1], mCellPicked[2]);
            mSudokuView.invalidate();
            toastMessage = getString(R.string.game_correct_toast_text);
        } else {
            toastMessage = getString(R.string.game_incorrect_toast_text);
        }
        mQuestionCard.setVisibility(View.GONE);
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        if (mSudokuModel.isGridFilled()) {
            Intent intent = new Intent(SudokuActivity.this, GameCompleteActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putIntArray("GridAsArray", mSudokuModel.getGridAsArray());
        saveInstanceState.putInt("NumOfCellsFilled", mSudokuModel.getNumOfCellsFilled());
        saveInstanceState.putIntArray("CellsToDraw", SudokuModel.flatten(mSudokuView.getCellsToDraw()));
        saveInstanceState.putBoolean("PopupVisible", (mQuestionCard.getVisibility() == View.VISIBLE));
    }

    private boolean isCorrect() {
        return mChoicePicked.equals(mWordBank.get(mQuestionCard.getWordPrompt()));
    }
}