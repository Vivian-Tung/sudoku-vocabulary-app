package com.example.sudokuvocabulary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class QuestionCardView extends CardView {
    private int mNumberOfChoices = 9;
    private String mWordPrompt;
    private final TextView mQuestionPromptView;
    private final Button[] mWordChoiceButtons = new Button[mNumberOfChoices];
    public QuestionCardView(@NonNull Context context) {
        this(context, null);
    }

    public QuestionCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater cardInflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cardInflater.inflate(R.layout.question_card_layout, this, true);

        LinearLayout parentLayout = (LinearLayout) getChildAt(0);
        mQuestionPromptView = (TextView) parentLayout.getChildAt(1);
        LinearLayout questionChoiceLayout = (LinearLayout) parentLayout.getChildAt(2);

        for (int buttonNum = 0; buttonNum < mNumberOfChoices; buttonNum++) {
            mWordChoiceButtons[buttonNum] = new Button(context);
            mWordChoiceButtons[buttonNum].setId(generateViewId());
            questionChoiceLayout.addView(mWordChoiceButtons[buttonNum]);
        }
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }

    public Button[] getWordChoiceButtons() { return mWordChoiceButtons; }

    public String getWordPrompt() { return mWordPrompt; }

    public void setNumberOfChoices(int numberOfChoices) {
        mNumberOfChoices = numberOfChoices;
    }

    public void setWordPrompt(String prompt) {
        mWordPrompt = prompt;
        mQuestionPromptView.setText(prompt);
        mQuestionPromptView.setTextSize(32);
    }

    public void setWordChoiceButtonsText(String[][] choices) {
        int buttonNum = 0;
        for (String[] choice: choices) {
            mWordChoiceButtons[buttonNum++].setText(choice[1]);
        }
    }
}

