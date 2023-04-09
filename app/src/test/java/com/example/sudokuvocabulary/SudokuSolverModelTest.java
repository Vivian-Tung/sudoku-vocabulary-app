package com.example.sudokuvocabulary;

import static org.junit.Assert.*;

import com.example.sudokuvocabulary.models.SudokuModel;
import com.example.sudokuvocabulary.models.SudokuSolverModel;

import org.junit.Test;

public class SudokuSolverModelTest {

    private final int[][] testGrid1 = {
            {0, 5, 6, 0, 7, 3, 1, 8, 2},
            {3, 0, 1, 2, 8, 5, 4, 0, 9},
            {2, 8, 9, 6, 4, 1, 7, 3, 5},
            {9, 6, 4, 1, 2, 7, 3, 5, 8},
            {0, 2, 0, 3, 6, 8, 9, 7, 4},
            {8, 3, 7, 4, 5, 0, 2, 1, 6},
            {0, 9, 8, 0, 3, 4, 6, 2, 1},
            {5, 0, 2, 7, 9, 6, 0, 4, 3},
            {6, 4, 3, 8, 1, 2, 5, 9, 7},
    };

    private final int[][] testGrid2 = {
            {0, 5, 6, 9, 7, 3, 1, 8, 2},
            {3, 7, 1, 2, 8, 5, 4, 6, 9},
            {2, 8, 9, 6, 4, 1, 7, 3, 5},
            {9, 6, 4, 1, 2, 7, 3, 5, 8},
            {1, 2, 5, 3, 6, 8, 9, 7, 4},
            {8, 3, 7, 4, 5, 9, 2, 1, 6},
            {7, 9, 8, 5, 3, 4, 6, 2, 1},
            {5, 1, 2, 7, 9, 6, 8, 4, 3},
            {6, 4, 3, 8, 1, 2, 5, 9, 7},
    };

    private final int[][] testGrid3 = {
            {9, 2, 6, 5, 7, 1, 4, 8, 3},
            {3, 5, 1, 4, 8, 6, 2, 7, 9},
            {8, 7, 4, 9, 2, 3, 5, 1, 6},
            {5, 8, 2, 3, 6, 7, 1, 9, 4},
            {1, 4, 9, 2, 5, 8, 3, 6, 7},
            {7, 6, 3, 1, 0, 0, 8, 2, 5},
            {2, 3, 8, 7, 0, 0, 6, 5, 1},
            {6, 1, 7, 8, 3, 5, 9, 4, 2},
            {4, 9, 5, 6, 1, 2, 7, 3, 8},
    };

    private final int[][] testGridSolution = {
            {4, 5, 6, 9, 7, 3, 1, 8, 2},
            {3, 7, 1, 2, 8, 5, 4, 6, 9},
            {2, 8, 9, 6, 4, 1, 7, 3, 5},
            {9, 6, 4, 1, 2, 7, 3, 5, 8},
            {1, 2, 5, 3, 6, 8, 9, 7, 4},
            {8, 3, 7, 4, 5, 9, 2, 1, 6},
            {7, 9, 8, 5, 3, 4, 6, 2, 1},
            {5, 1, 2, 7, 9, 6, 8, 4, 3},
            {6, 4, 3, 8, 1, 2, 5, 9, 7},
    };

    @Test
    public void solve() {
        SudokuModel model = new SudokuModel();
        SudokuSolverModel.solve(model);
        assertTrue(model.isGridFilled());

        model = new SudokuModel(testGrid1);
        SudokuSolverModel.solve(model);
        assertArrayEquals(testGridSolution, model.getGridAsMatrix());
    }

    @Test
    public void solutions() {
        SudokuModel model = new SudokuModel();
        assertEquals(1, SudokuSolverModel.solutions(model));

        model = new SudokuModel(testGrid1);
        assertEquals(1, SudokuSolverModel.solutions(model));

        model = new SudokuModel(testGrid2);
        assertEquals(1, SudokuSolverModel.solutions(model));

        model = new SudokuModel(testGrid3);
        int sol = SudokuSolverModel.solutions(model);
        assertEquals(2, sol);
    }
}