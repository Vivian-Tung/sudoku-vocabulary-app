package com.example.sudokuvocabulary;

import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuModelTest {
    public int[][] testGrid = {
            {4,5,6,9,7,3,1,8,2},
            {3,7,1,2,8,5,4,6,9},
            {2,8,9,6,4,1,7,3,5},
            {9,6,4,1,2,7,3,5,8},
            {1,2,5,3,6,8,9,7,4},
            {8,3,7,4,5,9,2,1,6},
            {7,9,8,5,3,4,6,2,1},
            {5,1,2,7,9,6,8,4,3},
            {6,4,3,8,1,2,5,9,7},
    };
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
    public void cellIsEmpty() {
    }

    @Test
    public void isGridFilled() {
    }

    @Test
    public void gridValid() {
    }

    @Test
    public void newFilledGrid() {
    }

    @Test
    public void solve() {
    }

    @Test
    public void newPuzzle() {
        SudokuModel model = new SudokuModel();
        model.setGrid(testGrid);
        int numberOfEmptyCells = 9;
        model.newPuzzle(numberOfEmptyCells);
        assertEquals(numberOfEmptyCells, model.getNumberOfEmptyCells());
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
    }
}