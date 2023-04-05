package com.example.sudokuvocabulary.activites;

import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.sudokuvocabulary.R;
import com.example.sudokuvocabulary.activites.MenuForAllActivity;

public class TutorialActivity extends MenuForAllActivity {

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (menu.findItem(R.id.action_tutorialBtn) != null) {
            menu.findItem(R.id.action_tutorialBtn).setVisible(false);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView timer = findViewById(R.id.TimerText);
        timer.setVisibility(View.GONE);
    }
}
