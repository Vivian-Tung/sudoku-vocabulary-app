package com.example.sudokuvocabulary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WordListView extends ScrollView {

    private Button[] mListButtons;

    private LinearLayout mButtonLayout;

    public WordListView(@NonNull Context context) { this(context, null); }

    public WordListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater scrollInflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        scrollInflater.inflate(R.layout.view_word_lists, this, true);

        LinearLayout parentLayout = (LinearLayout) getChildAt(0);
        ScrollView scrollView = (ScrollView) parentLayout.getChildAt(0);
        mButtonLayout = (LinearLayout) scrollView.getChildAt(0);
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
    }

    public Button[] getListButtons() {
        return mListButtons;
    }

    public void setWordListText(String[] categories) {
        mListButtons = new Button[categories.length];
        for (int wordListNumber=0; wordListNumber < categories.length; wordListNumber++) {
            mListButtons[wordListNumber] = new Button(this.getContext());
            mListButtons[wordListNumber].setId(generateViewId());
            mListButtons[wordListNumber].setText(categories[wordListNumber]);
            mButtonLayout.addView(mListButtons[wordListNumber]);
        }
        invalidate();
    }
}
