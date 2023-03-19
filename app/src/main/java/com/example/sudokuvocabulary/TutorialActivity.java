package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupTutorialButton();
        TextView timer = findViewById(R.id.TimerText);
        timer.setVisibility(View.GONE);

        PrefManager mPrefManager = new PrefManager(this);

        // Key containing dark mode switch boolean value
        String themeSwitchKey = getString(R.string.theme_value_key);

        //check for dark or light mode
        boolean themeSwitchState = mPrefManager.loadSavedPreferences(this, themeSwitchKey);

        // Restore the switch value to the previous setting
        SwitchCompat mDarkSwitch = findViewById(R.id.darkSwitch);
        mDarkSwitch.setChecked(themeSwitchState);

        mDarkSwitch.setOnCheckedChangeListener((compoundButton, themeSwitchState1) -> {
            if (compoundButton.isPressed()) {
                mPrefManager.savePreferences(themeSwitchKey, themeSwitchState1);
                recreate();
            }
        });
    }

    private void setupTutorialButton() {
        ImageView tutorialBtn = findViewById(R.id.tutorialBtn);
        tutorialBtn.setOnClickListener(view -> {

            Intent intent = new Intent(TutorialActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }

}
