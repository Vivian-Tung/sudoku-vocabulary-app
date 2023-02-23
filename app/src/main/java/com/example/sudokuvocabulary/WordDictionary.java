package com.example.sudokuvocabulary;

import java.util.ArrayList;

public class WordDictionary {

    private int mId;
    private String mName;
    private ArrayList<WordSample> mWords;

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

    public ArrayList<WordSample> getWords() {
        return mWords;
    }

    public String[] getWordsAsArray() {
        String[] array = new String[getLength()];
        int index = 0;
        for (WordSample sample: mWords) {
            array[index++] = sample.getWord();
        }
        return array;
    }

    public String[] getTranslationsAsArray() {
        String[] array = new String[getLength()];
        int index = 0;
        for (WordSample sample: mWords) {
            array[index++] = sample.getTranslation();
        }
        return array;
    }

    public String getWord(int index) {
        WordSample sample = mWords.get(index);
        return sample.getWord();
    }

    public String getTranslation(int index) {
        WordSample sample = mWords.get(index);
        return sample.getTranslation();
    }

    public void add(WordSample sample) {
        mWords.add(sample);
    }

    public void add(String word, String translation) {
        WordSample sample = new WordSample(word, translation);
        add(sample);
    }

    public void remove(WordSample sample) {
        mWords.remove(sample);
    }

    public void remove(String word) {
        mWords.remove(find(word));
    }

    public WordSample find(String word) {
        for (WordSample sample: mWords) {
            if(sample.getWord().equals(word)) {
                return sample;
            }
        }
        return null;
    }

    public String findTranslation(String word) {
        WordSample sample = find(word);
        if (sample == null) {
            return null;
        }
        return sample.getTranslation();
    }
}
