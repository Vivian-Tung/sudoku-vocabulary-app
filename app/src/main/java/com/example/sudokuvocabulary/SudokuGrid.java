package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuGrid extends View {
    private final int boardColor;
    public SudokuGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SudokuGrid, 0, 0);

        try {
            boardColor = attributes.getInteger(R.styleable.SudokuGrid_gridColor, 0);
        } finally {
            attributes.recycle();
        }
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(this.getWidth(), this.getHeight());
    }
}
