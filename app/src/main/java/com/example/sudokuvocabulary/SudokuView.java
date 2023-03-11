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
    private final int mCellItemFillColour;
    private final Paint mGridColourPaint = new Paint();
    private final Paint mCellItemFillColourPaint = new Paint();
    private int mCellHeight;
    private int mCellWidth;
    private int mGridLength = 9;
    private int mSubGridLength = 3;
    private int mSubGridHeight = 3; // To be used for drawing various sized grids
    private String[][] mWordsToDraw;

    public SudokuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setSaveEnabled(true);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SudokuGrid, 0, 0);
        try {
            mGridColour = attributes.getInteger(R.styleable.SudokuGrid_gridColor, 0);
            mCellItemFillColour = attributes.getInteger(R.styleable.SudokuGrid_cellItemFillColour, 0);
        } finally {
            attributes.recycle();
        }
        mWordsToDraw = new String[mGridLength][mGridLength];
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        int min = Math.min(getMeasuredWidth(), getMeasuredHeight());
        // Calculate the size for each individual cell
        mCellHeight = min / mGridLength;
        mCellWidth = getMeasuredWidth() / mGridLength;
        setMeasuredDimension(getMeasuredWidth(), min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mGridColourPaint.setStyle(Paint.Style.STROKE);
        mGridColourPaint.setColor(mGridColour);
        mGridColourPaint.setStrokeWidth(14);
        mGridColourPaint.setAntiAlias(true);

        mCellItemFillColourPaint.setStyle(Paint.Style.FILL);
        mCellItemFillColourPaint.setTextSize(getCellSize()/2);
        mCellItemFillColourPaint.setColor(mCellItemFillColour);
        mCellItemFillColourPaint.setAntiAlias(true);
        mCellItemFillColourPaint.setTextAlign(Paint.Align.CENTER);

        // Draw grid border
        canvas.drawRect(0,0, getWidth(), getHeight(), mGridColourPaint);
        // Draw the Sudoku grid and fill each cell with the correct word
        drawGrid(canvas);
        drawCellItems(canvas);
    }

    public void setInitialGrid(int[][] grid, String[] words, String[] translations) {
        for (int i = 0; i < mGridLength* mGridLength; i++) {
            int row = i / mGridLength, column = i % mGridLength;
            // Set each cell to the english word specified by grid
            if (grid[row][column] != 0) {
                mWordsToDraw[row][column] = words[grid[row][column] - 1];
            }
        }
        // Call invalidate to redraw the grid
        this.invalidate();
    }

    public int getCellSize() {
        return mCellHeight;
    }

    public String[][] getWordsToDraw() {
        return mWordsToDraw;
    }

    public String getWordToDrawAt(int row, int column) {
        return mWordsToDraw[row][column];
    }

    public void setWordToDrawAt(int row, int column, String word) {
        mWordsToDraw[row][column] = word;
    }

    public void setWordsToDraw(String[][] wordsToDraw) {
        mWordsToDraw = wordsToDraw;
        this.invalidate();
    }

    public void drawCellItems(Canvas canvas) {
        for (int i = 0; i < mGridLength*mGridLength; i++) {
            int row = i / mGridLength, column = i % mGridLength;
            String word = mWordsToDraw[row][column];
            if (word != null) {
                int x_axis = (int) ((column * mCellHeight) + (0.5 * mCellHeight));
                int y_axis = (int) ((row * mCellHeight) + (0.8 * mCellHeight));
                String itemText = "" + Character.toUpperCase(word.charAt(0));
                if (word.length() > 1) {
                    itemText += Character.toUpperCase(getWordToDrawAt(row, column).charAt(1));
                }
                canvas.drawText(itemText, x_axis, y_axis, mCellItemFillColourPaint);
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

        for (int line = 0; line < mGridLength +1; line++) {
            // Check if current line is a major line, draw a thicker line if so
            if (line % mSubGridLength == 0) {
                drawThickLine();
            } else {
                drawThinLine();
            }
            // Draw column lines
            canvas.drawLine(mCellHeight *line, 0, mCellHeight * line, getWidth(), mGridColourPaint);
            //Draw row lines
            canvas.drawLine(0, mCellHeight *line, getHeight(), mCellHeight *line, mGridColourPaint);
        }
    }
}
