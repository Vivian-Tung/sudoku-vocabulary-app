package com.example.sudokuvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class ListenModeActivity extends BaseSudokuActivity {

    private String[] mNumbers;
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextToSpeech = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                mTextToSpeech.setLanguage(Locale.CHINESE);
            }
        });
    }

    @Override
    public View.OnClickListener onClick() {
        return view -> {
            mChoicePicked = (String) ((Button) view).getText();
            String toastMessage;

            if (isCorrect()) {
                if (mSudokuModel.getValueAt(mCellRow, mCellColumn) == 0) {
                    mSudokuModel.checkAndFillCellAt(mCellRow, mCellColumn, mCellValue);
                }
                mSudokuView.setWordToDrawAt(mCellRow, mCellColumn, mWords[mCellValue-1]);
                toastMessage = getString(R.string.game_correct_toast_text);
            } else {
                toastMessage = getString(R.string.game_incorrect_toast_text);
            }
            mQuestionCard.setVisibility(View.GONE);
            Toast.makeText(view.getContext(), toastMessage, Toast.LENGTH_SHORT).show();
            if (mSudokuModel.isGridFilled()) {
                timer.stop();
                Intent intent = newIntent(view.getContext(), mWords, mTranslations);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void initializeGrid() {
        mNumbers  = new String[mSudokuModel.getGridLength()];
        int[] numbersToCopy = mSudokuModel.getNumberArray();
        int index = 0;
        for (int number: numbersToCopy) {
            mNumbers[index++] = Integer.toString(number);
        }
        mSudokuView.setInitialGrid(mSudokuModel.getGridAsMatrix(), mNumbers);
    }

    @Override
    protected boolean isCorrect() {
        String[] wordsToCompare = (mSudokuModel.getValueAt(mCellRow, mCellColumn) != 0) ? mWords : mTranslations;
        return wordsToCompare[mCellValue-1].equals(mChoicePicked);
    }

    @Override
    protected void onCellNotEmpty(int cellValue) {
        // Checks if the cell has already been solved
        if (Character.isLetter(mSudokuView.getWordToDrawAt(mCellRow, mCellColumn).charAt(0))) {
            return;
        }
        mQuestionCard.setCard(null, mWords);
        mTextToSpeech.speak(
                mTranslations[cellValue-1],
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
        );
        mQuestionCard.show();
    }

    @Override
    public void onDestroy() {
        mTextToSpeech.shutdown();
        super.onDestroy();
    }
}