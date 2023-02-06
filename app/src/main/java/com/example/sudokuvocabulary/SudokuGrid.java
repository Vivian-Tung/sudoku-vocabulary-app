package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuGrid extends View {
    private final int gridColour;
    private final Paint gridColourPaint = new Paint();
    public SudokuGrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SudokuGrid, 0, 0);

        try {
            gridColour = attributes.getInteger(R.styleable.SudokuGrid_gridColor, 0);
        } finally {
            attributes.recycle();
        }
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(getWidth(), getHeight());

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        gridColourPaint.setStyle(Paint.Style.STROKE);
        gridColourPaint.setColor(gridColour);
        gridColourPaint.setStrokeWidth(14);
        gridColourPaint.setAntiAlias(true);

        canvas.drawRect(0,0, getWidth(), getHeight(), gridColourPaint);
    }
}
