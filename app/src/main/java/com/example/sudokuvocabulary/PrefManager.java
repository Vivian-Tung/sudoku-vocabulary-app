package com.example.sudokuvocabulary;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class PrefManager  {
    private Context context;

    public PrefManager (Context context) {
        this.context = context;
    }

    //save preferences

    public void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //load saved preferences
    public boolean loadSavedPreferences(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        boolean darkMode = sharedPreferences.getBoolean(key, false);
        if (darkMode) { //dark mode on
            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
            savePreferences(key, true);
            return true;
        } else {
            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));
            savePreferences(key, false);
            return false;
        }
    }

//    //switch listener
//    public void switchListener() {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("darkSwitch_Val", Context.MODE_PRIVATE);
//        boolean darkMode = sharedPreferences.getBoolean("value", false);
//        if (darkMode) {
//            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
//            savePreferences("darkSwitch_Val", true);
//        } else {
//            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_NO));
//            savePreferences("darkSwitch_Val", false);
//        }
//
//    }


}
