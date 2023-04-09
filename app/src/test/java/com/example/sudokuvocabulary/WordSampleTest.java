package com.example.sudokuvocabulary;

import static org.junit.Assert.*;

import com.example.sudokuvocabulary.models.WordDictionaryModel;
import com.example.sudokuvocabulary.models.WordSampleModel;

import org.junit.Test;


public class WordSampleTest {
    //sample words
    String[] words1 = {"Apples", "Oranges", "Watermelon", "Grapes", "Fruits", "Banana", "Dragon Eyes", "Mango", "Plum"};
    String [] words2 = {"苹果", "橙子", "西瓜", "葡萄", "水果", "香蕉", "龙眼", "芒果", "李子"};

    String[][] testCases = {words1, words2};


    @Test
    public void getWord() {
        for (int i = 0; i < testCases.length; i++) {
            WordSampleModel test = new WordSampleModel(testCases[i][0], testCases[i][1]);
            for (int index = 0; index < testCases.length; index++) {
                assertEquals(testCases[i][0], test.getWord());
            }
        }
    }

    @Test
    public void setWord() {
        for (int i = 0; i < testCases.length; i++) {
            WordSampleModel test = new WordSampleModel();
            test.setWord(testCases[i][0]);
            assertEquals(testCases[i][0], test.getWord());
            }
        }

    @Test
    public void getTranslation() {
        for (int i = 0; i < testCases.length; i++) {
            WordSampleModel test = new WordSampleModel(testCases[i][0], testCases[i][1]);
            for (int index = 0; index < testCases.length; index++) {
                assertEquals(testCases[i][1], test.getTranslation());
            }
        }
    }

    @Test
    public void testToString() {
        for (int i = 0; i < testCases.length; i++) {
            WordSampleModel test = new WordSampleModel(testCases[i][0], testCases[i][1]);
            for (int index = 0; index < testCases.length; index++) {
                assertEquals(testCases[i][0], test.getWord());
            }
        }
    }
}
