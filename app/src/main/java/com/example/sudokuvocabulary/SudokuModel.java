package com.example.sudokuvocabulary;

import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class SudokuModel implements Serializable {

    private int mSubGridRows;
    private int mSubGridColumns;
    private int mGridLength;
    private int mGridSize;
    private int[][] mSudokuGrid;
    private int[][] mSudokuSolution;
    private int[] mNumberArray;
    private int mNumOfEmptyCells = 0;

    public SudokuModel(int gridLength, int subGridColumns, int subGridRows, int numOfEmptyCells) {
        mSubGridRows = subGridRows;
        mSubGridColumns = subGridColumns;
        mGridLength = gridLength;
        mGridSize = gridLength*gridLength;
        mNumOfEmptyCells = numOfEmptyCells;
        mSudokuGrid = new int[gridLength][gridLength];
        mNumberArray = new int[gridLength];
        for (int i=0;i<gridLength;i++) {
            mNumberArray[i] = i+1;
        }
        newFilledGrid();
        mSudokuSolution = copy(getGridAsMatrix());
        newPuzzle(numOfEmptyCells);
    }

    public SudokuModel() {
        this(9, 3, 3, 1);
    }

    public int getGridSize() { return mGridLength; }

    public int[][] getGridAsMatrix() { return mSudokuGrid; }

    public int[] getGridAsArray() { return flatten(mSudokuGrid); }

    public int[][] getSolutionAsMatrix() { return mSudokuSolution; }

    public int[] getSolutionAsArray() {return flatten(mSudokuSolution); }

    public int getSolutionAt(int row, int column) { return mSudokuSolution[row][column]; }

    public void setGrid(int[][] newGrid) {
        mSudokuGrid = newGrid;
    }

    public void setGridFromArray(int[] newArray) {
        mSudokuGrid = expand(newArray);
    }

    public void setSolutionFromArray(int[] solution) {
        mSudokuSolution = expand(solution);
    }

    public int[] getRow(int row) {
        return mSudokuGrid[row];
    }
    
    public int[] getColumn(int column) {
        int[] columnArray = new int[mGridLength];
        for (int i = 0; i < mGridLength; i++) {
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

    public void setNumberOfEmptyCells(int numberOfEmptyCells) {
        mNumOfEmptyCells = numberOfEmptyCells;
    }

    public int getNumberOfEmptyCells() { return mNumOfEmptyCells; }

    public boolean cellIsEmpty(int row, int column) { return getValueAt(row, column) == 0; }

    public boolean isGridFilled() { return mNumOfEmptyCells == 0; }

    public boolean checkAndFillCellAt(int row, int column, int value) {
        if (value != getSolutionAt(row, column)) { return false; }

        setValueAt(row, column, value);
        setNumberOfEmptyCells(mNumOfEmptyCells-1);
        return true;
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
        return index - (index % subGridDimension) ;
    }

    public boolean gridValid(int value_row, int value_column, int number) {
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

    private void shuffleMatrix(int[][] matrix) {
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            int index = random.nextInt(i+1);
            int[] temp = matrix[index];
            matrix[index] = matrix[i];
            matrix[i] = temp;
        }
    }

    public boolean newFilledGrid() { return solver(0); }

    private boolean solver(int index) {
        if (index >= mGridSize) { return true; }

        int row = (index / mGridLength), column = (index % mGridLength);

        if (!cellIsEmpty(row, column)) { return solver(index + 1); }
        // Number array is shuffled to generate random puzzle
        shuffleArray(mNumberArray);
        for (int number: mNumberArray) {
            if (gridValid(row, column, number)) {
                // Insert current number at that position
                setValueAt(row, column, number);
                // Recursively check next position
                if (solver(index + 1)) {
                    break;
                } else {
                    // Set value back to zero
                    setValueAt(row, column, 0);
                }
            }
        }
        return !cellIsEmpty(row, column);
    }

    private boolean hasUniqueSolution() {
        return countSolutions(0, 0) == 1;
    }

    private int countSolutions(int index, int numOfSolutions) {
        if (numOfSolutions > 1) { return numOfSolutions; }
        if (index >= mGridSize) {
            return numOfSolutions+1;
        }

        int row = (index / mGridLength), column = (index % mGridLength);

        if (cellIsEmpty(row,column) == false) {
            return countSolutions(index+1, numOfSolutions);
        }
        for (int number: mNumberArray) {
            if (gridValid(row, column, number)) {
                // Insert current number in the cell
                setValueAt(row, column, number);
                // Recursively check next cell
                numOfSolutions = countSolutions(index+1, numOfSolutions);
                setValueAt(row, column, 0);
            }
        }
        return numOfSolutions;
    }

    public void newPuzzle(int numOfEmptyCells) {
        int[][] cells = new int[mGridSize][2];
        for (int index=0; index < mGridSize; index++) {
            cells[index][0] = index / mGridLength;
            cells[index][1] = index % mGridLength;
        }
        shuffleMatrix(cells);
        for (int[] cell: cells) {
            if (numOfEmptyCells  <= 0) { break; }
            int row = cell[0], column = cell[1], temp = getValueAt(row, column);
            setValueAt(row, column, 0);
            boolean uniqueSol = hasUniqueSolution();
            if (uniqueSol) {
                numOfEmptyCells--;
            } else {
                setValueAt(row, column, temp);
            }
        }
    }

    public static int[] flatten(int[][] matrix) {
        int[] flattenedArray = new int[matrix.length * matrix.length];
        int index = 0;
        for(int[] row: matrix) {
            for (int value: row) {
                flattenedArray[index++] = value;
            }
        }
        return flattenedArray;
    }

    public static int[][] expand(int[] array) {
        int matrixLength = (int) Math.sqrt(array.length);
        int[][] expandedMatrix = new int[matrixLength][matrixLength];
        int index = 0;
        for (int value: array) {
            expandedMatrix[index/matrixLength][index%matrixLength] = value;
            index++;
        }
        return expandedMatrix;
    }

    public static int[][] copy(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix.length];
        for (int i=0; i<matrix.length* matrix.length; i++) {
            int row = i/matrix.length, column = i% matrix.length;
            copy[row][column] = matrix[row][column];
        }
        return copy;
    }
}