package com.example.sudokuvocabulary;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class MenuForAllActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_toolbar, menu);
        //disable these icons (bc not actual buttons)
        menu.findItem(R.id.sunImg).setEnabled(false);
        menu.findItem(R.id.moonImg).setEnabled(false);
        menu.findItem(R.id.TimerText).setEnabled(false);

        //handling dark switch action
        PrefManager mPrefManager = new PrefManager(this);

        // Key containing dark mode switch boolean value
        String themeSwitchKey = getString(R.string.theme_value_key);

        //check for dark or light mode
        boolean themeSwitchState = mPrefManager.loadSavedPreferences(this, themeSwitchKey);

        // Restore the switch value to the previous setting
        //MenuItem itemSwitch = menu.findItem(R.id.action_darkSwitch);
        //itemSwitch.setActionView(R.layout.switch_item); //switch item layout;

        final Switch darkSwitch = (Switch) menu.findItem(R.id.action_darkSwitch).getActionView().findViewById(R.id.switchTemplate); //removed final
        darkSwitch.setChecked(themeSwitchState);

        //listener
        darkSwitch.setOnCheckedChangeListener((compoundButton, themeSwitchState1) -> {
            if (compoundButton.isPressed()) {
                displayMessage("Switch is kinda janky");
                mPrefManager.savePreferences(themeSwitchKey, themeSwitchState1);
                recreate();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_tutorialBtn:
                displayMessage("tutorial button clicked");
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

    //to set up tutorial button
//    private void setupTutorialButton() {
//        ImageView tutorialBtn = findViewById(R.id.action_tutorialBtn);
//        tutorialBtn.setOnClickListener(view -> {
//
//            Intent intent = new Intent(WordListNameActivity.this, TutorialActivity.class);
//            startActivity(intent);
//        });
//    }
}
