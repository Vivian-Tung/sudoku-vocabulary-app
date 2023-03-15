package com.example.sudokuvocabulary;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DBAdapterTest {

    private DBAdapter db;

    @Before
    public void createDB() {
        Context context = ApplicationProvider.getApplicationContext();
        db = new DBAdapter(context);
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void isOpen() {
        assertTrue(db.isOpen());
    }

    @Test
    public void newTable() {
    }

    @Test
    public void insertRow() {
    }

    @Test
    public void testInsertRow() {
    }

    @Test
    public void testInsertRow1() {
    }

    @Test
    public void deleteRow() {
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void getTableNames() {
    }

    @Test
    public void getAllRows() {
    }

    @Test
    public void testGetAllRows() {
    }

    @Test
    public void getAllCategories() {
    }

    @Test
    public void getRow() {
    }

    @Test
    public void getWordsFromCategory() {
    }

    @Test
    public void updateRow() {
    }
}