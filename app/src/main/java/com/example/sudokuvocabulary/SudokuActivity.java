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
    private int mCellRow=0, mCellColumn=0, mCellValue=0;
    private String mChoicePicked;

    private static final String KEY_GRID_AS_ARRAY = "gridAsArray";
    private static final String KEY_SOLUTION_AS_ARRAY = "solutionArray";
    private static final String KEY_NUM_OF_EMPTY_CELLS = "numOfCellsFilled";
    private static final String KEY_POPUP_VISIBLE = "popupVisible";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        mSudokuModel = new SudokuModel();
        mSudokuView = findViewById(R.id.sudokuGridView);
        mSudokuView.setCellsToDraw(mSudokuModel.getGridAsMatrix());

        mQuestionCard = findViewById(R.id.questionCardView);
        mQuestionCard.setNumberOfChoices(mSudokuModel.getGridSize());
        mQuestionCard.setVisibility(View.GONE);

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
                        mCellRow = (int) (Math.ceil(motionEvent.getY() / mSudokuView.getCellSize())) - 1;
                        mCellColumn = (int) (Math.ceil(motionEvent.getX() / mSudokuView.getCellSize())) - 1;
                    } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        mCellRow = (int) (Math.ceil(motionEvent.getY() / mSudokuView.getCellSize())) - 1;
                        mCellColumn = (int) ((Math.ceil(motionEvent.getX()) / mSudokuView.getCellSize()));
                    }
                    mCellValue = mSudokuModel.getSolutionAt(mCellRow, mCellColumn);
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

                    mQuestionCard.setWordPrompt(words[mSudokuModel.getSolutionAt(mCellRow,
                            mCellColumn)-1][0]);
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
            mSudokuModel.checkAndFillCellAt(mCellRow, mCellColumn, mCellValue);
            mSudokuView.setCellToDraw(mCellRow, mCellColumn, mCellValue);
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
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntArray(KEY_GRID_AS_ARRAY, mSudokuModel.getGridAsArray());
        savedInstanceState.putIntArray(KEY_SOLUTION_AS_ARRAY, mSudokuModel.getSolutionAsArray());
        savedInstanceState.putInt(KEY_NUM_OF_EMPTY_CELLS, mSudokuModel.getNumberOfEmptyCells());
        savedInstanceState.putBoolean(KEY_POPUP_VISIBLE, (mQuestionCard.getVisibility() == View.VISIBLE));
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mSudokuModel = new SudokuModel();
        mSudokuModel.setGridFromArray(savedInstanceState.getIntArray(KEY_GRID_AS_ARRAY));
        mSudokuModel.setSolutionFromArray(savedInstanceState.getIntArray(KEY_SOLUTION_AS_ARRAY));
        mSudokuModel.setNumberOfEmptyCells(savedInstanceState.getInt(KEY_NUM_OF_EMPTY_CELLS));

        mSudokuView = (SudokuView) findViewById(R.id.sudokuGridView);
        mSudokuView.setCellsToDraw(mSudokuModel.getGridAsMatrix());
        mSudokuView.invalidate();

        mQuestionCard = (QuestionCardView) findViewById(R.id.questionCardView);
        mQuestionCard.setVisibility((savedInstanceState.getBoolean(KEY_POPUP_VISIBLE))? View.VISIBLE:View.GONE);
    }

    private boolean isCorrect() {
        return mChoicePicked.equals(mWordBank.get(mQuestionCard.getWordPrompt()));
    }
}