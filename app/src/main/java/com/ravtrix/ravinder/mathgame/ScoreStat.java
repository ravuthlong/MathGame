package com.ravtrix.ravinder.mathgame;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ScoreStat extends AppCompatActivity implements View.OnClickListener {

    private TextView viewNormalScore, viewExpertScore, txtHighS, txtN, txtE;
    private ImageButton buttonX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_stat);

        viewNormalScore = (TextView) findViewById(R.id.viewNormalScore);
        viewExpertScore = (TextView) findViewById(R.id.viewExpertScore);

        buttonX = (ImageButton) findViewById(R.id.buttonX);
        txtHighS = (TextView) findViewById(R.id.txtHighS);
        txtN = (TextView) findViewById(R.id.txtN);
        txtE = (TextView) findViewById(R.id.txtE);

        Typeface jenFont = Typeface.createFromAsset(getAssets(), "setFont.otf");
        viewNormalScore.setTypeface(jenFont, Typeface.BOLD);
        viewExpertScore.setTypeface(jenFont, Typeface.BOLD);
        txtHighS.setTypeface(jenFont, Typeface.BOLD);
        txtE.setTypeface(jenFont, Typeface.BOLD);
        txtN.setTypeface(jenFont, Typeface.BOLD);

        buttonX.setOnClickListener(this);
        changeScreenSize();
        displayHighScore();
    }

    // Change the screen display size of this activity to smaller
    public void changeScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.5), (int) (height * 0.3));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonX:
                finish();
                break;
        }
    }

    // Display high score of game
    public void displayHighScore() {
        MyPreference yourPreference = MyPreference.getInstance(ScoreStat.this);
        int hiScoreN = 0;
        int hiScoreE = 0;
        try {
            hiScoreN = yourPreference.getData("normalScore");
        } catch (ClassCastException e) {
            hiScoreN = 0;
        }
        try {
            hiScoreE = yourPreference.getData("expertScore");
        } catch (ClassCastException e) {
            hiScoreE = 0;
        }
        viewNormalScore.setText(Integer.toString(hiScoreN));
        viewExpertScore.setText(Integer.toString(hiScoreE));
    }
}

