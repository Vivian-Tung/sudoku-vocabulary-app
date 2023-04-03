package com.example.sudokuvocabulary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SudokuActivity extends BaseSudokuActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View.OnClickListener onClick() {
        return view -> {
            mChoicePicked = (String) ((Button) view).getText();
            String toastMessage;

            if (isCorrect()) {
                mSudokuModel.checkAndFillCellAt(mCellRow, mCellColumn, mCellValue);
                mSudokuView.setWordToDrawAt(mCellRow, mCellColumn, mTranslations[mCellValue-1]);
                mSudokuView.invalidate();
                toastMessage = getString(R.string.game_correct_toast_text);
            } else {
                toastMessage = getString(R.string.game_incorrect_toast_text);
            }
            mQuestionCard.setVisibility(View.GONE);
            Toast.makeText(view.getContext(), toastMessage, Toast.LENGTH_SHORT).show();
            if (mSudokuModel.isGridFilled()) {
                timer.stop();
                Intent intent = newIntent(view.getContext(), mWords, mTranslations);
                intent.putExtra(getString(R.string.mode_key), false);
                startActivity(intent);
            }
        };
    }

    @Override
    protected void initializeGrid() {
        mSudokuView.setInitialGrid(mSudokuModel.getGridAsMatrix(), mWords);
    }

    @Override
    protected boolean isCorrect() {
        return mChoicePicked.equals(
                mTranslations[mSudokuModel.getSolutionAt(mCellRow, mCellColumn)-1]);
    }

    @Override
    protected void onCellNotEmpty(int cellValue) {
        mQuestionCard.setCard(mWordPrompt, mWords);
        Toast.makeText(this, mWords[cellValue-1], Toast.LENGTH_SHORT).show();
    }
}