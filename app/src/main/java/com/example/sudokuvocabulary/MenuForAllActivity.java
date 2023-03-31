package com.example.sudokuvocabulary;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class MenuForAllActivity extends AppCompatActivity {

    boolean isNightModeOn;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_toolbar, menu);
        //disable these icons (bc not actual buttons)
        menu.findItem(R.id.sunImg).setEnabled(false);
        menu.findItem(R.id.moonImg).setEnabled(false);
        //menu.findItem(R.id.TimerText).setEnabled(false);

        //handling dark switch action
        MenuItem itemSwitch = menu.findItem(R.id.action_darkSwitch);
        itemSwitch.setActionView(R.layout.switch_item); //switch item layout;
        SwitchCompat darkSwitch = (SwitchCompat) menu.findItem(R.id.action_darkSwitch).getActionView().findViewById(R.id.switchTemplate);

        //TODO: not sure why it starts off with checked even tho i put false in xml
        //check for dark or light mode
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            isNightModeOn = false;
        } else {
            isNightModeOn = true;
        }
        darkSwitch.setChecked(isNightModeOn);

        //listener
        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isNightModeOn) {
                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                displayMessage("dark switch pressed!");
            }
        });


//
//        PrefManager mPrefManager = new PrefManager(this);
//
//        // Key containing dark mode switch boolean value
//        String themeSwitchKey = getString(R.string.theme_value_key);
//
//        //check for dark or light mode
//        boolean themeSwitchState = mPrefManager.loadSavedPreferences(this, themeSwitchKey);
//
//        // Restore the switch value to the previous setting
//        MenuItem itemSwitch = menu.findItem(R.id.action_darkSwitch);
//        itemSwitch.setActionView(R.layout.switch_item); //switch item layout;
//
//        final SwitchCompat darkSwitch = (SwitchCompat) menu.findItem(R.id.action_darkSwitch).getActionView().findViewById(R.id.switchTemplate);
//        darkSwitch.setChecked(themeSwitchState);
//
//        //listener
//        darkSwitch.setOnCheckedChangeListener((compoundButton, themeSwitchState1) -> {
//            if (compoundButton.isPressed()) {
//                displayMessage("Switch is kinda janky");
//                Log.d(TAG,"darkmode jank");
//                mPrefManager.savePreferences(themeSwitchKey, themeSwitchState1);
//                recreate();
//
//
//            }
//        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_tutorialBtn:
                Intent intent = new Intent(this, TutorialActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //for testing out
    private void displayMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}
