package com.example.sudokuvocabulary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class PrefUtils {

    private final Context context;

    public PrefUtils(Context context) {
        this.context = context;
    }

    //save preferences
    public void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    //load saved preferences
    public boolean loadSavedPreferences(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        boolean darkMode = sharedPreferences.getBoolean(key, false);
        if (darkMode) { //dark mode on
            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
        } else {
            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));
        }
        return darkMode;
    }
}
