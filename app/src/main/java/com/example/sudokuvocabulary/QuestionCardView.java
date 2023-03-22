package com.example.sudokuvocabulary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class QuestionCardView extends CardView {
    private Context mContext;
    private TableLayout mQuestionChoices;
    private int mNumberOfChoices = 9;
    private final TextView mQuestionPromptView;
    private Button[] mWordChoiceButtons;
    private int mButtonColumns;

    public QuestionCardView(@NonNull Context context) {
        this(context, null);
    }

    public QuestionCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        LayoutInflater cardInflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cardInflater.inflate(R.layout.question_card_layout, this, true);

        LinearLayout parentLayout = (LinearLayout) getChildAt(0);
        mQuestionPromptView = (TextView) parentLayout.getChildAt(1);
        mQuestionChoices = (TableLayout) parentLayout.getChildAt(2);
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        setMeasuredDimension(width, height);
    }

    public void setCard(String prompt, String[] choices) {
        // Methods must be called in this order
        setWordPrompt(prompt);
        setNumberOfChoices(choices.length);
        if (mWordChoiceButtons == null) {
            mWordChoiceButtons = newButtonArray(mNumberOfChoices);
            mButtonColumns = numberOfColumns(mNumberOfChoices);
            setTable();
            setWordChoiceButtonsText(choices);
        }
        this.invalidate();
    }

    public void show() {
        setVisibility(true);
    }

    public void hide() {
        setVisibility(false);
    }

    public void setVisibility(boolean isVisible) {
        this.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public boolean isVisible() {
        return this.getVisibility() == View.VISIBLE;
    }

    public Button[] getWordChoiceButtons() { return mWordChoiceButtons; }

    public String getWordPrompt(){
        return mQuestionPromptView.getText().toString();
    }

    public void setNumberOfChoices(int numberOfChoices) {
        mNumberOfChoices = numberOfChoices;
    }

    public void setWordPrompt(String prompt) {
        mQuestionPromptView.setText(prompt);
        mQuestionPromptView.setTextSize(36);
    }

    public void setWordChoiceButtonsText(String[] choices) {
        int buttonNum = 0;
        for (Button button: mWordChoiceButtons) {
            button.setText(choices[buttonNum++]);
        }
    }

    private TableRow newTableRow(Context context) {
        TableRow row = new TableRow(context);
        row.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return row;
    }

    private int numberOfColumns(int choices) {
        return (int) Math.ceil((double) Math.sqrt((double) choices));
    }

    private Button[] newButtonArray(int choices) {
        Button[] buttons = new Button[choices];
        for (int index = 0; index < choices; index++) {
            buttons[index] = new Button(mContext);
            buttons[index].setId(generateViewId());
        }
        return buttons;
    }

    private void setTable() {
        TableRow row = newTableRow(mContext);
        int buttonNum = 0;
        for (Button button: mWordChoiceButtons) {
            row.addView(button);
            if ((++buttonNum) % mButtonColumns == 0) {
                mQuestionChoices.addView(row);
                row = newTableRow(mContext);
            }
        }
        if (buttonNum % mButtonColumns != 0) mQuestionChoices.addView(row);
    }
}

