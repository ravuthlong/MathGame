package com.ravtrix.ravinder.mathgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.bReplay)         protected Button bReplay;
    @BindView(R.id.bMenu)           protected  Button bMenu;
    @BindView(R.id.txtScore)        protected TextView txtScore;
    @BindView(R.id.txtHighScore)    protected TextView txtHighScore;
    @BindView(R.id.txtGameOver)     protected TextView txtGameOver;
    @BindView(R.id.txtHS)           protected TextView txtHS;
    @BindView(R.id.txtNewScore)     protected TextView txtNewScore;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);
        ButterKnife.bind(this);

        setTypeFace();
        setButtonListeners();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout); // Animation between activities
        totalAndHighScore();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bReplay:
                Intent intent = new Intent(GameOverActivity.this, GameModeActivity.class);
                startActivity(intent);
                break;
            case R.id.bMenu:
                clearIntents();
                break;
        }
    }

    // Delete all active activities from stack
    public void clearIntents() {
        Intent intent = new Intent(this, StartingActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
    }

    @Override
    // Disable back press
    public void onBackPressed() {
        return;
    }

    /**
     * Display total and high score of game
     */
    public void totalAndHighScore() {
        this.value = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("total");
        }
        txtScore.setText(String.format(Locale.getDefault(), "%d", value));
        int hiScore = getHighScore();
        txtHighScore.setText(String.format(Locale.getDefault(), "%d", hiScore));
    }

    private void setTypeFace() {
        Typeface font = Typeface.createFromAsset(getAssets(), "date.ttf");

        // Set font for different text fields
        txtGameOver.setTypeface(font);
        txtScore.setTypeface(font);
        txtHighScore.setTypeface(font);
        txtHS.setTypeface(font);
        txtNewScore.setTypeface(font);
    }

    private void setButtonListeners() {
        bReplay.setOnClickListener(this);
        bMenu.setOnClickListener(this);
    }

    /**
     * Get high score
     * If high score > the stored high score, update it
     * If high score < the stored high score, do not update
     * If there is nothing in stored high score, set high score to 0
     * @return      the highscore
     */
    private int getHighScore() {
        int hiScore = 0;
        MyPreference yourPreference = MyPreference.getInstance(GameOverActivity.this);
        switch (GameSetting.gameLevel) {
            case 0:
                try {
                    hiScore = yourPreference.getData("normalScore");
                } catch (ClassCastException e) {
                    hiScore = 0;
                }

                if (value > hiScore) {
                    yourPreference.saveData("normalScore", value);
                    hiScore = yourPreference.getData("normalScore");
                }
                break;
            case 1:
                try {
                    hiScore = yourPreference.getData("expertScore");
                } catch (ClassCastException e) {
                    hiScore = 0;
                }

                if (value > hiScore) {
                    yourPreference.saveData("expertScore", value);
                    hiScore = yourPreference.getData("expertScore");
                }
                break;
        }
        return hiScore;
    }
}
