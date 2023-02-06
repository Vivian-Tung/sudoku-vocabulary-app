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
    private int mCellSize;
    private int mGridSideLength = 9;
    private int mSubGridSize = 3;

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

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());

        mCellSize = dimension / mGridSideLength;

        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        gridColourPaint.setStyle(Paint.Style.STROKE);
        gridColourPaint.setColor(gridColour);
        gridColourPaint.setStrokeWidth(14);
        gridColourPaint.setAntiAlias(true);

        // Draw grid border
        canvas.drawRect(0,0, getWidth(), getHeight(), gridColourPaint);

        drawGrid(canvas);
    }

    private void drawThickLine() {
        gridColourPaint.setStyle(Paint.Style.STROKE);
        gridColourPaint.setStrokeWidth(12);
        gridColourPaint.setColor(gridColour);
    }

    private void drawThinLine() {
        gridColourPaint.setStyle(Paint.Style.STROKE);
        gridColourPaint.setStrokeWidth(6);
        gridColourPaint.setColor(gridColour);
    }

    private void drawGrid(Canvas canvas) {

        for (int line = 0; line < mGridSideLength+1; line++) {
            // Check if current line is a major line
            if (line % mSubGridSize == 0) {
                drawThickLine();
            } else {
                drawThinLine();
            }
            // Draw column lines
            canvas.drawLine(mCellSize*line, 0, mCellSize * line, getWidth(), gridColourPaint);
            //Draw row lines
            canvas.drawLine(0, mCellSize*line, getHeight(), mCellSize*line, gridColourPaint);
        }
    }
}
