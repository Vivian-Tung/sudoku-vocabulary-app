package com.example.sudokuvocabulary;

import static org.junit.Assert.*;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordDictionaryTest {

    String[][] words1 = {{"lamb", "beef", "chicken"}, {"羊肉", "牛肉", "鸡肉"}};
    String[][] words2 = {
            {"Apples", "Oranges", "Watermelon", "Grapes", "Fruits", "Banana",
                    "Dragon Eyes", "Mango", "Plum"},
            {"苹果", "橙子", "西瓜", "葡萄", "水果", "香蕉", "龙眼", "芒果", "李子"}
    };

    String[][][] testCases = {words1, words2};

    @Test
    public void getLength() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            assertEquals(testCase[0].length, test.getLength());
        }
        for (String[][] testCase : testCases) {
            WordDictionary test1 = new WordDictionary(testCase[1], testCase[0]);
            assertEquals(testCase[1].length, test1.getLength());
        }
    }

    @Test
    public void getWords() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            ArrayList<WordSample> samples = test.getWords();
            for (int index = 0; index < testCase[0].length; index++) {
                assertEquals(testCase[0][index], samples.get(index).getWord());
                assertEquals(testCase[1][index], samples.get(index).getTranslation());
                assertNotEquals(testCase[0][index], samples.get(index).getTranslation());
                assertNotEquals(testCase[1][index], samples.get(index).getWord());
            }
        }
        for (String[][] testcase : testCases) {
            WordDictionary test1 = new WordDictionary(testcase[0], testcase[1]);
            ArrayList<WordSample> samples1 = test1.getWords();
            for (int i = 0; i < testcase[1].length; i++) {
                assertEquals(testcase[0][i], samples1.get(i).getWord());
                assertEquals(testcase[1][i], samples1.get(i).getTranslation());
                assertNotEquals(testcase[0][i], samples1.get(i).getTranslation());
                assertNotEquals(testcase[1][i], samples1.get(i).getWord());
            }
        }
    }

    @Test
    public void setFromArrays() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary();
            test.setFromArrays(testCase[0], testCase[1]);
            assertArrayEquals(testCase[0], test.getWordsAsArray());
            assertArrayEquals(testCase[1], test.getTranslationsAsArray());
        }
    }

    @Test
    public void getWordsAsArray() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            assertArrayEquals(testCase[0], test.getWordsAsArray());
        }
        for (String[][] testcase1 : testCases) {
            WordDictionary test1 = new WordDictionary(testcase1[1], testcase1[0]);
            assertArrayEquals(testcase1[1], test1.getWordsAsArray());
        }
    }

    @Test
    public void getTranslationsAsArray() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            assertArrayEquals(testCase[1], test.getTranslationsAsArray());
            assertNotEquals(testCase[0], test.getTranslationsAsArray());
        }
        for (String[][] testcase1 : testCases) {
            WordDictionary test1 = new WordDictionary(testcase1[1], testcase1[0]);
            assertArrayEquals(testcase1[0], test1.getTranslationsAsArray());
            assertNotEquals(testcase1[1], test1.getTranslationsAsArray());
        }
    }

    @Test
    public void getWord() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            for (int index = 0; index < testCase[0].length; index++) {
                assertEquals(testCase[0][index], test.getWord(index));
                assertNotEquals(testCase[1][index], test.getWord(index));
            }
        }
        for (String[][] testCase1 : testCases) {
            WordDictionary test = new WordDictionary(testCase1[1], testCase1[0]);
            for (int index = 0; index < testCase1[1].length; index++) {
                assertEquals(testCase1[1][index], test.getWord(index));
                assertNotEquals(testCase1[0][index], test.getWord(index));
            }
        }
    }

    @Test
    public void getTranslation() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            for (int index = 0; index < testCase[1].length; index++) {
                assertEquals(testCase[1][index], test.getTranslation(index));
                assertNotEquals(testCase[0], test.getTranslationsAsArray());
            }
        }
        for (String[][] testcase1 : testCases) {
            WordDictionary test1 = new WordDictionary(testcase1[1], testcase1[0]);
            assertEquals(testcase1[0], test1.getTranslationsAsArray());
            assertNotEquals(testcase1[1], test1.getTranslationsAsArray());
        }
    }

    @Test
    public void add() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary();
            for (int index = 0; index < testCase[0].length; index++) {
                test.add(testCase[0][index], testCase[1][index]);
            }
            assertArrayEquals(testCase[0], test.getWordsAsArray());
            assertArrayEquals(testCase[1], test.getTranslationsAsArray());
        }

    }

    @Test
    public void remove() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            for (int index = 0; index < testCase[0].length; index++) {
                test.remove(testCase[0][index]);
                assertFalse(test.contains(testCase[0][index]));
            }
        }
    }

    @Test
    public void findSample() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            for (int index = 0; index < testCase[0].length; index++) {
                WordSample sample = test.findSample(testCase[0][index]);
                assertEquals(testCase[0][index], sample.getWord());
                assertEquals(testCase[1][index], sample.getTranslation());
            }
        }
    }

    @Test
    public void findTranslation() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            for (int index = 0; index < testCase[0].length; index++) {
                String translation = test.findTranslation(testCase[0][index]);
                assertEquals(testCase[1][index], translation);
                assertNotEquals(testCase[0][index],translation);
            }
        }
        for (String[][] testCase1 : testCases) {
            WordDictionary test = new WordDictionary(testCase1[1], testCase1[0]);
            for (int index = 0; index < testCase1[1].length; index++) {
                String translation = test.findTranslation(testCase1[1][index]);
                assertNotEquals(testCase1[1][index], translation);
                assertEquals(testCase1[0][index],translation);
            }
        }
    }

    @Test
    public void contains() {
        for (String[][] testCase : testCases) {
            WordDictionary test = new WordDictionary(testCase[0], testCase[1]);
            for (String word : testCase[0]) {
                assertTrue(test.contains(word));
            }
            String notInTest = "bacon";
            assertFalse(test.contains(notInTest));
            String notInTest1 = "turkey";
            assertFalse(test.contains(notInTest1));
            // how to test if it does contain word?
            //assertArrayEquals(testCase[0],test.contains(notInTest));
            //test.contains(String.valueOf(testCase[0]));
            //assertEquals(testCase[0],String.valueOf(testCase[0]) );

        }
    }
}