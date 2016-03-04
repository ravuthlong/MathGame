package com.ravtrix.ravinder.mathgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bReplay, bMenu;
    private TextView txtScore, txtHighScore, txtGameOver, txtHS, txtNewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        Typeface gistFont = Typeface.createFromAsset(getAssets(), "gist.otf");
        Typeface appleFont = Typeface.createFromAsset(getAssets(), "appleFont.ttf");
        Typeface jenFont = Typeface.createFromAsset(getAssets(), "jenFont.ttf");

        bReplay = (Button) findViewById(R.id.bReplay);
        bMenu = (Button) findViewById(R.id.bMenu);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtHighScore = (TextView) findViewById(R.id.txtHighScore);
        txtGameOver = (TextView) findViewById(R.id.txtGameOver);
        txtHS = (TextView) findViewById(R.id.txtHS);
        txtNewScore = (TextView) findViewById(R.id.txtNewScore);

        bReplay.setOnClickListener(this);
        bMenu.setOnClickListener(this);

        // Set font for different text fields
        txtGameOver.setTypeface(gistFont);
        txtScore.setTypeface(appleFont);
        txtHighScore.setTypeface(appleFont);
        txtHS.setTypeface(jenFont);
        txtNewScore.setTypeface(jenFont);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout); // Animation between activities
        totalAndHighScore();
        changeScreenSize();
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
        //this.finish();
    }

    @Override
    // Disable back press
    public void onBackPressed() {
        return;
    }

    // Change the screen display size of this activity to smaller
    public void changeScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.7), (int) (height * 0.44));
    }

    // Display total and high score of game
    public void totalAndHighScore() {
        int value = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getInt("total");
        }
        txtScore.setText(Integer.toString(value));


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
        txtHighScore.setText(Integer.toString(hiScore));
    }
}
