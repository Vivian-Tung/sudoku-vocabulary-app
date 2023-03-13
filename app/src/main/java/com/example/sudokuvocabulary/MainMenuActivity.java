package com.example.sudokuvocabulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

public class MainMenuActivity extends AppCompatActivity {

    DBAdapter db;
    Button button1;

    TextView TimerText;

    Timer timer;

    TimerTask timerTask;

    Double time = 0.0;
    private Button mPlayButton;

    Handler mHandler;

    boolean timerStarted = false;

    public void playbuttonTapped(View view){
        if(timerStarted == false){
            timerStarted = true;

            startTimer();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mPlayButton = (Button) findViewById(R.id.main_menu_play_button);

        TimerText = (TextView) findViewById(R.id.TimerText);

        timer = new Timer();

        Button playButton = (Button) findViewById(R.id.main_menu_play_button);
        playButton.setOnClickListener(v -> {
            playbuttonTapped(v);
            Intent intent = new Intent (MainMenuActivity.this, SetSudokuSizeActivity.class);
            startActivity(intent);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupTutorialButton();

        button1=findViewById(R.id.main_menu_word_bank_button);

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, WordListsActivity.class);
            startActivity(intent);
        });
    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        //TimerText.setText(getTimerText());
                        mHandler.obtainMessage(1).sendToTarget();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {
                TimerText.setText(getTimerText());
            }
        };
    }
    private String getTimerText(){
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int hours = ((rounded % 86400) / 3600);
        int minutes = ((rounded % 86400) % 3600) / 60;

        return formatTime(seconds,minutes,hours);
    }

    private String formatTime(int seconds, int minutes, int hours) {
        return String.format("%02d",hours) + " : " +
                String.format("%02d",minutes) + " : " +
                String.format("%02d", seconds);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupTutorialButton() {
        ImageView tutorialBtn = findViewById(R.id.tutorialBtn);
        tutorialBtn.setOnClickListener(view -> {

            Intent intent = new Intent(MainMenuActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }

}