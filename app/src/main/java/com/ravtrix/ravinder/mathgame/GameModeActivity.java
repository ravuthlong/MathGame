package com.ravtrix.ravinder.mathgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameModeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.linearLayout)    protected LinearLayout linearLayout;
    @BindView(R.id.txtFirstNum)     protected TextView txtFirstNum;
    @BindView(R.id.txtSecondNum)    protected TextView txtSecondNum;
    @BindView(R.id.txtResult)       protected TextView txtResult;
    @BindView(R.id.txtTimer)        protected TextView txtTimer;
    @BindView(R.id.txtScoreUpdate)  protected TextView txtScoreUpdate;
    @BindView(R.id.bRight)          protected ImageButton bRight;
    @BindView(R.id.bWrong)          protected ImageButton bWrong;
    private Toast toast = null;
    private boolean isTimerRunning = false;
    private SoundPlayer soundClick, soundEnd;
    private CountDownTimer countDownTimer;
    static int randomAndroidColor;
    private MathGenerator mathGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setFullScreen();
        setButtonListeners();
        setSounds();
        backgroundGenerator();
        setTypeFace();
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
                stopTimer();

                if (mathGenerator.getIsRight()) {
                    startNewProblem();
                } else {
                    endGame();
                }
                break;
            case R.id.bWrong:
                stopTimer();

                if (mathGenerator.getIsWrong()) {
                    startNewProblem();
                } else {
                    endGame();
                }
                break;
        }
    }

    /**
     * Count down class. OnFinish() will be invoked upon out of time
     */
    public class MyCountDownTimer extends CountDownTimer {
        MyCountDownTimer(long startTime, long interval){
            super (startTime, interval);
            synchronized(this){
            }
        }

        // When time is up, ends the game and display end game activity
        @Override
        public void onFinish() {
            txtTimer.setText("0");
            endGame();
            toast = Toast.makeText(GameModeActivity.this, "TIME'S UP", Toast.LENGTH_LONG);
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

    /**
     * Disable buttons when game ended
     */
    public void disableButtons() {
        bRight.setEnabled(false);
        bWrong.setEnabled(false);
    }

    /**
     * Start the timer in 250 as increment. This controls countdown display.
     */
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
        startCountDownListener(startTime, interval);
    }

    /**
     * Generate a random background color
     */
    private void backgroundGenerator() {
        Random rand = new Random();
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

    /**
     * Play the sound
     * @param soundPlayer       the sound player to play
     */
    public void toPlaySound(SoundPlayer soundPlayer) {
        if (GameSetting.isSoundToggleChecked) {
            soundPlayer.playSound();
        }
    }

    private void setButtonListeners() {
        this.bRight.setOnClickListener(this);
        this.bWrong.setOnClickListener(this);
    }

    /**
     * Set numbers from random number generator
     */
    private void setNumbers() {
        txtFirstNum.setText(String.format(Locale.getDefault(), "%d", mathGenerator.getStartingNumber()));
        txtSecondNum.setText(String.format(Locale.getDefault(), "%d", mathGenerator.getSecondNumber()));
        txtResult.setText(String.format(Locale.getDefault(), "%d", mathGenerator.getResultNum()));
    }

    /**
     * Change font to style setFont.otf
     */
    private void setTypeFace() {
        Typeface tf = Typeface.createFromAsset(getAssets(), "bebas.ttf");

        this.txtFirstNum.setTypeface(tf);
        this.txtFirstNum.setText(String.format(Locale.getDefault(), "%d", mathGenerator.getStartingNumber()));
        txtSecondNum.setTypeface(tf);
        txtSecondNum.setText(String.format(Locale.getDefault(), "%d", mathGenerator.getSecondNumber()));
        txtResult.setTypeface(tf);
        txtResult.setText(String.format(Locale.getDefault(), "%d", mathGenerator.getResultNum()));
        txtTimer.setTypeface(tf);
        txtScoreUpdate.setTypeface(tf);
    }

    /**
     * Set the two different clicking sounds
     */
    private void setSounds() {
        soundClick = new SoundPlayer(this, R.raw.click);
        soundEnd = new SoundPlayer(this, R.raw.end);
    }

    /**
     * Set the screen size to full screen mode
     */
    private void setFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * Stop the countdown timer
     */
    private void stopTimer() {
        if (isTimerRunning) {
            countDownTimer.cancel();
        }
    }

    /**
     * The user answered correctly, set a new problem
     */
    private void startNewProblem() {
        toPlaySound(soundClick);
        startTimer();
        isTimerRunning = true;
        mathGenerator.updateStartingNum();
        String currentScore = String.valueOf(mathGenerator.getStartingNumber());
        txtScoreUpdate.setText(currentScore);
        backgroundGenerator();
        setNumbers();
    }

    /**
     * The user answered incorrectly, game over
     */
    private void endGame() {
        toPlaySound(soundEnd);
        disableButtons();
        Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
        intent.putExtra("total", mathGenerator.getStartingNumber());
        startActivity(intent);
        mathGenerator.setStartingNumber(0);
    }

    /**
     * Start the countdown
     * @param startTime         the start time
     * @param interval          the interval of count
     */
    private void startCountDownListener(long startTime, long interval) {
        countDownTimer = new MyCountDownTimer (startTime, interval) {
            public void onTick(long millisUntilFinished) {
                long timeLeft = (millisUntilFinished / 250);
                String timeString = String.valueOf(timeLeft);
                txtTimer.setText(timeString);
            }
        }.start();
    }
}

