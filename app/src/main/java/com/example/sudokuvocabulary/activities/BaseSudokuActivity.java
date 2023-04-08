package com.example.sudokuvocabulary.activities;

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

import com.example.sudokuvocabulary.R;
import com.example.sudokuvocabulary.fragments.ExitGameDialogFragment;
import com.example.sudokuvocabulary.models.SudokuModel;
import com.example.sudokuvocabulary.models.TimerModel;
import com.example.sudokuvocabulary.utils.PrefUtils;
import com.example.sudokuvocabulary.utils.SaveFileUtil;
import com.example.sudokuvocabulary.views.QuestionCardView;
import com.example.sudokuvocabulary.views.SudokuView;

import java.io.Serializable;

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
    protected String saveFileName;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_sudoku);

        // Initialize toolbar and add back arrow button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        saveFileName = getString(R.string.save_game_file);

        mSudokuView = findViewById(R.id.sudokuGridView);
        int subWidth, subHeight;

        // Load existing save file if load button was pressed from main menu
        if (PrefUtils.loadBoolPreference(this, getString(R.string.save_game_key))) {
            Object[] savedObjects = SaveFileUtil.readAllFromSave(this, saveFileName);
            mSudokuModel = (SudokuModel) savedObjects[SaveFileUtil.SaveObjects.SUDOKU_MODEL.ordinal()];
            mWords = (String[]) savedObjects[SaveFileUtil.SaveObjects.WORDS.ordinal()];
            mTranslations = (String[]) savedObjects[SaveFileUtil.SaveObjects.TRANSLATIONS.ordinal()];
            mSudokuView.setView((String[][]) savedObjects[SaveFileUtil.SaveObjects.SUDOKU_GRID.ordinal()]);
            startTime = (double) savedObjects[SaveFileUtil.SaveObjects.TIME.ordinal()];

            PrefUtils.saveBoolPreference(this, getString(R.string.save_game_key), false);
        } else {
            subWidth = getIntent().getIntExtra(getString(R.string.sub_width_key), 3);
            subHeight = getIntent().getIntExtra(getString(R.string.sub_height_key), 3);
            mWords = getIntent().getStringArrayExtra(getString(R.string.words_key));
            mTranslations = getIntent().getStringArrayExtra(getString(R.string.translations_key));
            mSudokuModel = new SudokuModel(mWords.length, subWidth, subHeight, (mWords.length*mWords.length)/2);

            // Set the words to draw on the grid and the dimensions of the grid
            initializeGrid();
            mSudokuView.setSubGridDimensions(subWidth, subHeight);

            if (savedInstanceState != null) {
                startTime = savedInstanceState.getDouble(getString(R.string.time_key));
            }
        }

        // Hide the input prompt
        mQuestionCard = findViewById(R.id.questionCardView);
        mQuestionCard.hide();

        // Setup and start the timer
        mSudokuView.setOnTouchListener(this);

        TimerText = findViewById(R.id.TimerText);
        timer = new TimerModel(startTime, new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@Nullable Message msg) {
                super.handleMessage(msg);
                TimerText.setText(timer.getTimerText()); // Update timer view
            }
        });
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
        if (PrefUtils.loadBoolPreference(this, getString(R.string.save_game_key))) {
            saveGame();
        }
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
        timer.stop();
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

    public void resumeTimer() {
        timer.start();
    }

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

    private void saveGame() {
        Serializable[] objectsToSave = {
                this.getClass() == ListenModeActivity.class, // Current game mode
                mSudokuModel,
                mSudokuView.getWordsToDraw(),
                timer.getTime(),
                mWords,
                mTranslations,
        };
        SaveFileUtil.writeToSave(this, objectsToSave, saveFileName);
    }
}