package com.example.sudokuvocabulary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class QuestionCardView extends CardView {
    public QuestionCardView(@NonNull Context context) {
        this(context, null);
    }

    public QuestionCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater cardInflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cardInflater.inflate(R.layout.question_card_layout, this, true);

        LinearLayout parentLayout = (LinearLayout) getChildAt(0);
        LinearLayout buttonLayout = (LinearLayout) parentLayout.getChildAt(2);

        for (int buttonNum = 1; buttonNum < 5; buttonNum++) {
            Button button = new Button(context);
            button.setText("Word: ");
            button.generateViewId();
            buttonLayout.addView(button);
        }
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }
}

