package com.ravtrix.ravinder.mathgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameModeActivity extends CustomActivity implements View.OnClickListener {

    private Toast toast = null;
    private boolean isTimerRunning = false;
    private SoundPlayer soundClick, soundEnd;
    private CountDownTimer countDownTimer;
    private LinearLayout linearLayout;
    private TextView txtFirstNum, txtSecondNum, txtResult, txtTimer, txtScoreUpdate;
    private ImageButton bRight, bWrong;
    private Random rand;
    static int randomAndroidColor;
    private MathGenerator mathGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Typeface tf = Typeface.createFromAsset(getAssets(), "bebas.ttf");
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        txtFirstNum = (TextView) findViewById(R.id.txtFirstNum);
        txtSecondNum = (TextView) findViewById(R.id.txtSecondNum);
        txtResult = (TextView) findViewById(R.id.txtResult);
        bRight = (ImageButton) findViewById(R.id.bRight);
        bWrong = (ImageButton) findViewById(R.id.bWrong);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtScoreUpdate = (TextView) findViewById(R.id.txtScoreUpdate);

        bRight.setOnClickListener(GameModeActivity.this);
        bWrong.setOnClickListener(GameModeActivity.this);

        backgroundGenerator();
        soundClick = new SoundPlayer(this, R.raw.click);
        soundEnd = new SoundPlayer(this, R.raw.end);

        txtFirstNum.setTypeface(tf);
        txtFirstNum.setText(Integer.toString(mathGenerator.getStartingNumber()));
        txtSecondNum.setTypeface(tf);
        txtSecondNum.setText(Integer.toString(mathGenerator.getSecondNumber()));
        txtResult.setTypeface(tf);
        txtResult.setText(Integer.toString(mathGenerator.getResultNum()));
        txtTimer.setTypeface(tf);
        txtScoreUpdate.setTypeface(tf);
    }
    @Override
    protected void onStop () {
        super.onStop();
        if (toast != null) {
            toast.cancel();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bRight:
               
                if (mathGenerator.getIsRight()) {
                    //soundClick.playSound();
                    toPlaySound(soundClick);
                    if (isTimerRunning) {
                        countDownTimer.cancel();
                    }
                    startTimer();
                    isTimerRunning = true;
                    mathGenerator.updateStartingNum();

                    String currentScore = String.valueOf(mathGenerator.getStartingNumber());
                    txtScoreUpdate.setText(currentScore);

                    backgroundGenerator();

                    txtFirstNum.setText(Integer.toString(mathGenerator.getStartingNumber()));
                    txtSecondNum.setText(Integer.toString(mathGenerator.getSecondNumber()));
                    txtResult.setText(Integer.toString(mathGenerator.getResultNum()));
                } else {
                    //soundEnd.playSound();
                    toPlaySound(soundEnd);

                    disableButtons();

                    if (isTimerRunning) {
                        countDownTimer.cancel();
                    }
                    Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                    intent.putExtra("total", mathGenerator.getStartingNumber());
                    startActivity(intent);
                    mathGenerator.setStartingNumber(0);
                }
                break;
            case R.id.bWrong:

                if (mathGenerator.getIsWrong()) {
                    //soundClick.playSound();
                    toPlaySound(soundClick);

                    if (isTimerRunning) {
                        countDownTimer.cancel();
                    }
                    mathGenerator.updateStartingNum();
                    startTimer();
                    isTimerRunning = true;
                    backgroundGenerator();

                    String currentScore = String.valueOf(mathGenerator.getStartingNumber());
                    txtScoreUpdate.setText(currentScore);

                    txtFirstNum.setText(Integer.toString(mathGenerator.getStartingNumber()));
                    txtSecondNum.setText(Integer.toString(mathGenerator.getSecondNumber()));
                    txtResult.setText(Integer.toString(mathGenerator.getResultNum()));
                } else {
                    toPlaySound(soundEnd);

                    //soundEnd.playSound();
                    disableButtons();

                    if (isTimerRunning) {
                        countDownTimer.cancel();
                    }
                    Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
                    intent.putExtra("total", mathGenerator.getStartingNumber());
                    startActivity(intent);
                    mathGenerator.setStartingNumber(0);
                }
                break;
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval){
            super (startTime, interval);
            synchronized(this){
            }
        }

        // When time is up, ends the game and display end game activity
        @Override
        public void onFinish() {
            txtTimer.setText("0");
            //soundEnd.playSound();
            toPlaySound(soundEnd);

            disableButtons();
            Intent intent = new Intent(getApplicationContext(), GameOverActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

            intent.putExtra("total", mathGenerator.getStartingNumber());
            startActivity(intent);
            mathGenerator.setStartingNumber(0);
            toast = Toast.makeText(GameModeActivity.this, "TIMES UP", Toast.LENGTH_LONG);
            toast.show();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }

    // Disable the buttons based to some conditions. See when this is used.
    public void disableButtons() {
        bRight.setEnabled(false);
        bWrong.setEnabled(false);
    }

    // Start the timer in 250 as increment. This controls countdown display.
    private void startTimer() {

        long startTime = 0;
        long interval = 0;

        /*
         * Game level: normal and hard
         */
        switch (GameSetting.gameLevel) {
            case 0:
                startTime = 1000;
                interval = 250;
                break;
            case 1:
                startTime = 700;
                interval = 250;
                break;
        }
        countDownTimer = new MyCountDownTimer (startTime, interval) {
            public void onTick(long millisUntilFinished) {
                long timeLeft = (millisUntilFinished / 250);
                String timeString = String.valueOf(timeLeft);
                txtTimer.setText(timeString);
            }
        }.start();
    }

    private void backgroundGenerator() {
        rand = new Random();
        mathGenerator = new MathGenerator();
        mathGenerator.generateNumbers();
        if (mathGenerator.getStartingNumber() == 0) {
            // Get a random background color
            int[] androidColors = getResources().getIntArray(R.array.androidcolors);
            randomAndroidColor = androidColors[rand.nextInt(androidColors.length)];
            linearLayout.setBackgroundColor(randomAndroidColor);
        } else {
            linearLayout.setBackgroundColor(randomAndroidColor);
        }
    }

    public void toPlaySound(SoundPlayer soundPlayer) {
        if (GameSetting.isSoundToggleChecked) {
            soundPlayer.playSound();
        }
    }
}

