package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuView extends View {
    private Bundle mBundle = new Bundle();
    private SudokuModel mSudokuModel;
    private final int mGridColour;
    private final int mCellFillColour;
    private final int mCellHighlightColour;
    private final Paint mGridColourPaint = new Paint();
    private final Paint mCellFillColourPaint = new Paint();
    private final Paint mCellHighlightColourPaint = new Paint();
    private int mCellSize;
    private int mGridSideLength = 9;
    private int mSubGridSize = 3;

    public SudokuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SudokuGrid, 0, 0);

        try {
            mGridColour = attributes.getInteger(R.styleable.SudokuGrid_gridColor, 0);
            mCellFillColour = attributes.getInteger(R.styleable.SudokuGrid_cellFillColour, 0);
            mCellHighlightColour = attributes.getInteger(R.styleable.SudokuGrid_cellHighlightColour, 0);
        } finally {
            attributes.recycle();
        }

        mSudokuModel = (SudokuModel) mBundle.getSerializable("sudoku");
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
        mGridColourPaint.setStyle(Paint.Style.STROKE);
        mGridColourPaint.setColor(mGridColour);
        mGridColourPaint.setStrokeWidth(14);
        mGridColourPaint.setAntiAlias(true);

        mCellFillColourPaint.setStyle(Paint.Style.FILL);
        mCellFillColourPaint.setColor(mCellFillColour);
        mCellFillColourPaint.setAntiAlias(true);

        mCellHighlightColourPaint.setStyle(Paint.Style.FILL);
        mCellHighlightColourPaint.setColor(mCellHighlightColour);
        mCellHighlightColourPaint.setAntiAlias(true);

        // Draw grid border
        canvas.drawRect(0,0, getWidth(), getHeight(), mGridColourPaint);

        drawGrid(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isValid;

        float x_axis = event.getX();
        float y_axis = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            int selectedRow = (int) Math.ceil(y_axis/mCellSize);
            int selectedColumn = (int) Math.ceil(x_axis/mCellSize);
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    private void colourCell(Canvas canvas, int row, int column) {
        if (true) {
            // Highlight Row
            canvas.drawRect((column-1)*mCellSize, 0, column*mCellSize,
                    mCellSize*mGridSideLength, mCellHighlightColourPaint);
            // Highlight Column
            canvas.drawRect(0, (row-1)*mCellSize, mCellSize*mGridSideLength,
                    column*mCellSize, mCellHighlightColourPaint);
            // Highlight touched square
            canvas.drawRect((column-1)*mCellSize, (row-1)*mCellSize, column*mCellSize,
                    row*mCellSize, mCellHighlightColourPaint);
        }
    }

    public void setData(Bundle data) {
        mBundle = data;
    }

    private void drawThickLine() {
        mGridColourPaint.setStyle(Paint.Style.STROKE);
        mGridColourPaint.setStrokeWidth(12);
        mGridColourPaint.setColor(mGridColour);
    }

    private void drawThinLine() {
        mGridColourPaint.setStyle(Paint.Style.STROKE);
        mGridColourPaint.setStrokeWidth(6);
        mGridColourPaint.setColor(mGridColour);
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
            canvas.drawLine(mCellSize*line, 0, mCellSize * line, getWidth(), mGridColourPaint);
            //Draw row lines
            canvas.drawLine(0, mCellSize*line, getHeight(), mCellSize*line, mGridColourPaint);
        }
    }
}
