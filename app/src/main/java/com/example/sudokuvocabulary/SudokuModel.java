package com.example.sudokuvocabulary;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Random;

public class SudokuModel implements Serializable {

    private int mSubGridRows;
    private int mSubGridColumns;
    private int mGridRows;
    private int mGridColumns;

    private int[][] mSudokuGrid;
    private int[] mNumberArray;

    public SudokuModel(int subGridColumns, int subGridRows, int gridColumns, int gridRows) {
        mSubGridRows = subGridRows;
        mSubGridColumns = subGridColumns;
        mGridRows = gridRows;
        mGridColumns = gridColumns;
        mSudokuGrid = new int[gridRows][gridColumns];

        int max = Math.max(gridRows, gridColumns);
        mNumberArray = new int[max];
        for (int i=0;i<max;i++) {
            mNumberArray[i] = i+1;
        }
        newFilledGrid();
    }

    public SudokuModel() {
        this(3, 3, 9, 9);
    }

    public int getGridSize() { return mGridRows; }

    public int[][] getGrid() {
        return mSudokuGrid;
    }

    public int[] getRow(int row) {
        return mSudokuGrid[row];
    }
    
    public int[] getColumn(int column) {
        int[] columnArray = new int[mGridRows];

        for (int i = 0; i < mGridRows; i++) {
            columnArray[i] = mSudokuGrid[i][column];  
        }

        return columnArray;
    }

    public int getValueAt(int row, int column) {
        return mSudokuGrid[row][column];
    }

    public void setValueAt(int row, int column, int value) {
        mSudokuGrid[row][column] = value;
    }

    private boolean rowValid(int grid_row, int value) {
        for (int number : getRow(grid_row)) {
            if (number == value) {
                return false;
            }
        }

        return true;
    }

    private boolean columnValid(int grid_column, int value) {
        for (int number : getColumn(grid_column)) {
            if (number == value) {
                return false;
            }
        }

        return true;
    }

    public boolean subGridValid(int grid_row_start, int grid_column_start, int value) {

        int grid_row_end = grid_row_start + mSubGridRows;
        int grid_column_end = grid_column_start + mSubGridColumns;

        for (int row = grid_row_start; row < grid_row_end; row++) {
            for (int column = grid_column_start; column < grid_column_end; column++) {
                if (getValueAt(row, column) == value) {
                    return false;
                }
            }
        }

        return true;
    }
    
    private int calculateSubGridIndex(int index, int subGridDimension) {
        int ret = index - (index % subGridDimension) ;
        return ret;
    }

    public boolean gridValid(int value_row, int value_column, int number) {

        // Calculate which sub-grid value is in:
        int subGridRowStart = calculateSubGridIndex(value_row, mSubGridRows);
        int subGridColumnStart = calculateSubGridIndex(value_column, mSubGridColumns);

        boolean valid = rowValid(value_row, number) && columnValid(value_column, number);

        return valid && subGridValid(subGridRowStart, subGridColumnStart, number);
    }

    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            int index = random.nextInt(i+1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // Returns an index from 0 <= 81 
    // Corresponding to the first empty cell found
    // Index is counted starting from the top-left corner
    // in row-major order, -1 indicates a fully filled grid
    private int findEmptyCell() {
        int gridSize = mGridRows*mGridColumns;
        for (int i = 0; i < gridSize; i++) {
            int row = i / mGridRows;
            int column = i % mGridColumns;
            if (getValueAt(row, column) == 0) {
                return i;
            }
        }
        return -1;
    }

    //
    public boolean newFilledGrid() {
        return sudokuSolver(0);
    }

    public boolean sudokuSolver() {
        return sudokuSolver(findEmptyCell());
    }

    private boolean sudokuSolver(int index) {
        // Stop if grid has already been filled
        if (index < mGridRows*mGridColumns) {
            int row = (index / mGridColumns);
            int column = (index % mGridColumns);

            // Check if cell is empty
            if (getValueAt(row, column) == 0) {
                
                // Number array is shuffled for better variability
                shuffleArray(mNumberArray);

                for (int number: mNumberArray) {
                    
                    // Check if number is already used
                    if (gridValid(row, column, number)) {
                         
                        // Insert current number at that position
                        setValueAt(row, column, number);

                        // Recursively check next position
                        if (sudokuSolver(index + 1)) {
                            break;
                        } else {
                            // Current grid doesn't have a solution, need to change 
                            // previous values
                            // Reset the value back to zero before try other values
                            setValueAt(row, column, 0);
                        } 
                    }
                }
            }
            return getValueAt(row, column) != 0;
        } else {
            return true;
        }
    }

    public void clearGrid() {
        for (int row = 0; row < mGridRows; row++) {
            for (int column = 0; column < mGridColumns; column++) {
                setValueAt(row, column, 0);
            }
        }
    }
}