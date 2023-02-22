package com.example.sudokuvocabulary;

import java.io.Serializable;
import java.util.ArrayList;

public class WordDictionary {

    private int mId;
    private String mName;
    private ArrayList<wordSample> mWords;

    public WordDictionary(int id, String name) {
        mId = id;
        mName = name;
        mWords = new ArrayList<>();
    }

    public WordDictionary() {
        this(0, null);
    }

    public int getLength() {
        return mWords.size();
    }

    public ArrayList<wordSample> getWords() {
        return mWords;
    }

    public String[] getWordsAsArray() {
        String[] array = new String[getLength()];
        int index = 0;
        for (wordSample sample: mWords) {
            array[index++] = sample.getWord();
        }
        return array;
    }

    public String[] getTranslationsAsArray() {
        String[] array = new String[getLength()];
        int index = 0;
        for (wordSample sample: mWords) {
            array[index++] = sample.getTranslation();
        }
        return array;
    }

    public String getWord(int index) {
        wordSample sample = mWords.get(index);
        return sample.getWord();
    }

    public String getTranslation(int index) {
        wordSample sample = mWords.get(index);
        return sample.getTranslation();
    }

    public void add(wordSample sample) {
        mWords.add(sample);
    }

    public void add(String word, String translation) {
        wordSample sample = new wordSample(word, translation);
        add(sample);
    }

    public void remove(wordSample sample) {
        mWords.remove(sample);
    }

    public void remove(String word) {
        mWords.remove(find(word));
    }

    public wordSample find(String word) {
        for (wordSample sample: mWords) {
            if(sample.getWord().equals(word)) {
                return sample;
            }
        }
        return null;
    }

    public String findTranslation(String word) {
        wordSample sample = find(word);
        if (sample == null) {
            return null;
        }
        return sample.getTranslation();
    }
}
