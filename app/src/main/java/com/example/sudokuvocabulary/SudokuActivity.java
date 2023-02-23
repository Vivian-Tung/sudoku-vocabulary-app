package com.example.sudokuvocabulary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SudokuActivity extends AppCompatActivity implements View.OnClickListener {
    private QuestionCardView mQuestionCard;
    private SudokuView mSudokuView;
    private SudokuModel mSudokuModel;
    private String[] mWords, mTranslations;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupTutorialButton();

        mSudokuModel = new SudokuModel();
        mSudokuView = findViewById(R.id.sudokuGridView);
        mSudokuView.setCellsToDraw(mSudokuModel.getGridAsMatrix());

        mQuestionCard = findViewById(R.id.questionCardView);
        mQuestionCard.setNumberOfChoices(mSudokuModel.getGridLength());
        mQuestionCard.setVisibility(View.GONE);

        mWords = getIntent().getStringArrayExtra(getString(R.string.words_key));
        mTranslations = getIntent().getStringArrayExtra(getString(R.string.translations_key));

        mSudokuView.setWordsToDraw(mSudokuModel.getGridAsMatrix(), mWords);

        Button[] wordChoiceButtons = mQuestionCard.getWordChoiceButtons();
        for (Button choice: wordChoiceButtons) {
            choice.setOnClickListener(this);
        }

        mSudokuView.setOnTouchListener((view, motionEvent) -> {
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
                if (mSudokuModel.cellNotEmpty(mCellRow, mCellColumn)) {
                    Toast.makeText(this, mWords[mCellValue-1], Toast.LENGTH_SHORT).show();
                    return true;
                }

                isValid = true;

                mQuestionCard.setWordPrompt(mWords[
                        mSudokuModel.getSolutionAt(mCellRow, mCellColumn)-1]);
                mQuestionCard.setWordChoiceButtonsText(mTranslations);

                mQuestionCard.invalidate();
                mQuestionCard.setVisibility(View.VISIBLE);
            }
            return isValid;
        });
    }

    @Override
    public void onClick(View view) {
        mChoicePicked = (String) ((Button) view).getText();
        String toastMessage;

        if (isCorrect()) {
            mSudokuModel.checkAndFillCellAt(mCellRow, mCellColumn, mCellValue);
            mSudokuView.setCellToDraw(mCellRow, mCellColumn, mCellValue);
            mSudokuView.setWordToDrawAt(mCellRow, mCellColumn, mTranslations[mCellValue-1]);
            mSudokuView.invalidate();
            toastMessage = getString(R.string.game_correct_toast_text);
        } else {
            toastMessage = getString(R.string.game_incorrect_toast_text);
        }
        mQuestionCard.setVisibility(View.GONE);
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        if (mSudokuModel.isGridFilled()) {
            Intent intent = newIntent(
                    SudokuActivity.this, mWords, mTranslations);
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

        mSudokuView = findViewById(R.id.sudokuGridView);
        mSudokuView.setCellsToDraw(mSudokuModel.getGridAsMatrix());
        mSudokuView.invalidate();

        mQuestionCard = findViewById(R.id.questionCardView);
        mQuestionCard.setVisibility(
                (savedInstanceState.getBoolean(KEY_POPUP_VISIBLE))? View.VISIBLE:View.GONE);
    }

    @NonNull
    public Intent newIntent(Context packageContext, String[] words, String[] translations) {
        Intent intent = new Intent(packageContext, GameCompleteActivity.class);
        intent.putExtra(getString(R.string.words_key), words);
        intent.putExtra(getString(R.string.translations_key), translations);
        return intent;
    }

    private boolean isCorrect() {
        return mChoicePicked.equals(
                mTranslations[mSudokuModel.getSolutionAt(mCellRow, mCellColumn)-1]);
    }

    private void setupTutorialButton() {
        ImageView tutorialBtn = findViewById(R.id.tutorialBtn);
        tutorialBtn.setOnClickListener(view -> {

            Intent intent = new Intent(SudokuActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }
}