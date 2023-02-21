package com.example.sudokuvocabulary;

import java.util.ArrayList;

public class WordDictionary {
    private ArrayList<wordSample> mWords;

    public WordDictionary() {
        mWords = new ArrayList<>();
    }

    public int getLength() {
        return mWords.size();
    }

    public ArrayList<wordSample> getWords() {
        return mWords;
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
