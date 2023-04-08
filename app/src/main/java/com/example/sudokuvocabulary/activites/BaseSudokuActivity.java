package com.example.sudokuvocabulary.activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sudokuvocabulary.fragments.ExitGameDialogFragment;
import com.example.sudokuvocabulary.views.QuestionCardView;
import com.example.sudokuvocabulary.R;
import com.example.sudokuvocabulary.models.SudokuModel;
import com.example.sudokuvocabulary.views.SudokuView;
import com.example.sudokuvocabulary.models.TimerModel;

public abstract class BaseSudokuActivity extends MenuForAllActivity implements View.OnTouchListener {
    protected QuestionCardView mQuestionCard;
    protected SudokuView mSudokuView;
    protected SudokuModel mSudokuModel;
    protected String[] mWords, mTranslations;
    protected int mCellRow=0, mCellColumn=0, mCellValue=0;
    protected String mWordPrompt, mChoicePicked;
    protected TextView TimerText;
    protected TimerModel timer;
    protected double startTime = 0;
    protected int mStackLevel = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_sudoku);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TimerText = findViewById(R.id.TimerText);
        if (savedInstanceState != null) {
            startTime = savedInstanceState.getDouble(getString(R.string.time_key));
        }
        timer = new TimerModel(startTime, new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@Nullable Message msg) {
                super.handleMessage(msg);
                TimerText.setText(timer.getTimerText());
            }
        });

        mWords = getIntent().getStringArrayExtra(getString(R.string.words_key));
        mTranslations = getIntent().getStringArrayExtra(getString(R.string.translations_key));

        mSudokuModel = new SudokuModel();
        int subWidth = getIntent().getIntExtra(getString(R.string.sub_width_key), 3);
        int subHeight = getIntent().getIntExtra(getString(R.string.sub_height_key), 3);

        mSudokuModel = new SudokuModel(mWords.length, subWidth, subHeight, (mWords.length*mWords.length)/2);
        mSudokuView = findViewById(R.id.sudokuGridView);

        // Set the words to draw on the grid and the dimensions of the grid
        initializeGrid();
        mSudokuView.setSubGridDimensions(subWidth, subHeight);

        mQuestionCard = findViewById(R.id.questionCardView);
        mQuestionCard.hide();

        mSudokuView.setOnTouchListener(this);
        timer.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != MotionEvent.ACTION_DOWN || mQuestionCard.isVisible()) {
            return false;
        }

        mCellRow = (int) (Math.ceil(motionEvent.getY() / mSudokuView.getCellHeight())) - 1;
        mCellColumn = (int) ((Math.ceil(motionEvent.getX()) / mSudokuView.getCellWidth()));
        mCellValue = mSudokuModel.getSolutionAt(mCellRow, mCellColumn);

        if (mSudokuModel.cellNotEmpty(mCellRow, mCellColumn)) {
            onCellNotEmpty(mCellValue);
        } else {
            mWordPrompt = mWords[mSudokuModel.getSolutionAt(mCellRow, mCellColumn) - 1];
            mQuestionCard.setCard(mTranslations);
            mQuestionCard.show();
        }
        setButtonListeners(mQuestionCard.getWordChoiceButtons());
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putIntArray(getString(R.string.current_grid_key), mSudokuModel.getGridAsArray());
        savedInstanceState.putIntArray(getString(R.string.solution_grid_key), mSudokuModel.getSolutionAsArray());
        savedInstanceState.putInt(getString(R.string.cells_empty_key), mSudokuModel.getNumberOfEmptyCells());
        savedInstanceState.putInt(getString(R.string.cell_row_key), mCellRow);
        savedInstanceState.putInt(getString(R.string.cell_column_key), mCellColumn);
        savedInstanceState.putInt(getString(R.string.cell_value_key), mCellValue);
        savedInstanceState.putDouble(getString(R.string.time_key), timer.getTime());
        savedInstanceState.putBoolean(getString(R.string.popup_visibility_key), (mQuestionCard.getVisibility() == View.VISIBLE));
        savedInstanceState.putStringArray(getString(R.string.words_key), mWords);
        savedInstanceState.putStringArray(getString(R.string.translations_key), mTranslations);
        savedInstanceState.putString(getString(R.string.word_prompt_key), mWordPrompt);
        savedInstanceState.putStringArray(getString(R.string.word_grid_key),
                SudokuModel.flatten(mSudokuView.getWordsToDraw()));
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mSudokuModel = new SudokuModel();
        mSudokuModel.setGridFromArray(savedInstanceState.getIntArray(getString(R.string.current_grid_key)));
        mSudokuModel.setSolutionFromArray(savedInstanceState.getIntArray(getString(R.string.solution_grid_key)));
        mSudokuModel.setNumberOfEmptyCells(savedInstanceState.getInt(getString(R.string.cells_empty_key)));

        mWords = savedInstanceState.getStringArray(getString(R.string.words_key));
        mTranslations = savedInstanceState.getStringArray(getString(R.string.translations_key));
        mWordPrompt = savedInstanceState.getString(getString(R.string.word_prompt_key));

        mSudokuView = findViewById(R.id.sudokuGridView);
        String[][] wordsToDraw = SudokuModel.expand(
                savedInstanceState.getStringArray(getString(R.string.word_grid_key)));
        mSudokuView.setWordsToDraw(wordsToDraw);

        mCellRow = savedInstanceState.getInt(getString(R.string.cell_row_key));
        mCellColumn = savedInstanceState.getInt(getString(R.string.cell_column_key));
        mCellValue = savedInstanceState.getInt(getString(R.string.cell_value_key));

        onCardRestore();

        setButtonListeners(mQuestionCard.getWordChoiceButtons());
        mQuestionCard.setVisibility(savedInstanceState.getBoolean(getString(R.string.popup_visibility_key)));
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    @NonNull
    public Intent newIntent(Context packageContext, String[] words, String[] translations) {
        Intent intent = new Intent(packageContext, GameCompleteActivity.class);
        intent.putExtra(getString(R.string.words_key), words);
        intent.putExtra(getString(R.string.translations_key), translations);
        intent.putExtra(getString(R.string.time_key), timer.getTime());
        return intent;
    }

    public abstract View.OnClickListener onClick();

    protected abstract void initializeGrid();

    protected abstract boolean isCorrect();

    protected abstract void onCellNotEmpty(int cellValue);

    protected abstract void onCardRestore();

    private void setButtonListeners(Button[] buttons) {
        for (Button button: buttons) {
            button.setOnClickListener(onClick());
        }
    }

    private void showExitDialog() {
        mStackLevel++;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = ExitGameDialogFragment.newInstance(mStackLevel);
        newFragment.show(ft, "dialog");

    }
}