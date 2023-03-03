package com.example.sudokuvocabulary;

import static org.junit.Assert.*;

import org.junit.Test;

public class SudokuSolverModelTest {

    @Test
    public void solutions() {
        SudokuModel model = new SudokuModel();
        int sol = SudokuSolverModel.solutions(model);
        assertEquals(1, sol);
    }

    @Test
    public void solve() {
        SudokuModel model = new SudokuModel();
        SudokuSolverModel.solve(model);
        assertTrue(model.isGridFilled());
    }
}