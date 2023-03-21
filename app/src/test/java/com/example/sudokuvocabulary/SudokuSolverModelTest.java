package com.example.sudokuvocabulary;

import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuSolverModelTest {

    @Test
    public void solve() {
        SudokuModel model = new SudokuModel();
        SudokuSolverModel.solve(model);
        assertTrue(model.isGridFilled());
    }
}