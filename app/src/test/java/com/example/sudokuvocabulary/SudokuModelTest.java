package com.example.sudokuvocabulary;

import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuModelTest {

    @Test
    public void getGridSize() {
    }

    @Test
    public void getGridAsMatrix() {
    }

    @Test
    public void setGrid() {
    }

    @Test
    public void setGridFromArray() {
    }

    @Test
    public void getRow() {
    }

    @Test
    public void getColumn() {
    }

    @Test
    public void getValueAt() {
    }

    @Test
    public void setValueAt() {
    }

    @Test
    public void incrementCellsFilled() {
    }

    @Test
    public void getNumOfCellsFilled() {
    }

    @Test
    public void setNumOfCellsFilled() {
    }

    @Test
    public void isGridFilled() {
    }

    @Test
    public void subGridValid() {
    }

    @Test
    public void gridValid() {
    }

    @Test
    public void newFilledGrid() {
    }

    @Test
    public void sudokuSolver() {
    }

    @Test
    public void clearGrid() {
    }

    @Test
    public void getGridAsArray() {
    }

    @Test
    public void flatten() {
    }

    @Test
    public void expand() {
        int[] array = {1, 2, 3, 4};
        int[][] matrix = SudokuModel.expand(array);
        int[][] expectedMatrix = {
                {1, 2},
                {3, 4}};
        assertArrayEquals(expectedMatrix, matrix);
    }
}
