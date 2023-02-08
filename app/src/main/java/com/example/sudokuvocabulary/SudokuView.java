package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class SudokuView extends View {
    private final int mGridColour;
    private final int mCellFillColour;
    private final int mCellHighlightColour;
    private final int mCellItemFillColour;
    private final Paint mGridColourPaint = new Paint();
    private final Paint mCellFillColourPaint = new Paint();
    private final Paint mCellHighlightColourPaint = new Paint();
    private final Paint mCellItemFillColourPaint = new Paint();
    private int mCellSize;
    private int mGridSideLength = 9;
    private int mSubGridSize = 3;
    private int[][] mCellsToDraw;

    public SudokuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SudokuGrid, 0, 0);

        try {
            mGridColour = attributes.getInteger(R.styleable.SudokuGrid_gridColor, 0);
            mCellFillColour = attributes.getInteger(R.styleable.SudokuGrid_cellFillColour, 0);
            mCellHighlightColour = attributes.getInteger(R.styleable.SudokuGrid_cellHighlightColour, 0);
            mCellItemFillColour = attributes.getInteger(R.styleable.SudokuGrid_cellItemFillColour, 0);
        } finally {
            attributes.recycle();
        }

        mCellsToDraw = new int[mGridSideLength][mGridSideLength];
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

        mCellItemFillColourPaint.setStyle(Paint.Style.FILL);
        mCellItemFillColourPaint.setTextSize(96);
        mCellItemFillColourPaint.setColor(mCellItemFillColour);
        mCellItemFillColourPaint.setAntiAlias(true);

        // Draw grid border
        canvas.drawRect(0,0, getWidth(), getHeight(), mGridColourPaint);

        drawGrid(canvas);

        drawCellNumbers(canvas);
    }

    public void setCellToDraw(int row, int column, int value) {
        mCellsToDraw[row][column] = value;
    }

    public int getCellSize() {
        return mCellSize;
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

    public void drawCellNumbers(Canvas canvas) {
        for (int row = 0; row < mGridSideLength; row++) {
            for (int column = 0; column < mGridSideLength; column++) {
                int cellValue = mCellsToDraw[row][column];
                if (cellValue != 0) {
                    int x_axis = (int) ((column * mCellSize) + (0.25 * mCellSize));
                    int y_axis = (int) ((row * mCellSize) + (0.8 * mCellSize));
                    String itemText = Integer.toString(cellValue);
                    canvas.drawText(itemText, x_axis, y_axis, mCellItemFillColourPaint);
                }
            }
        }
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
