package com.ravtrix.ravinder.mathgame;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreStat extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.viewNormalScore) protected TextView viewNormalScore;
    @BindView(R.id.viewExpertScore) protected TextView viewExpertScore;
    @BindView(R.id.txtHighS) protected TextView txtHighS;
    @BindView(R.id.txtN) protected TextView txtN;
    @BindView(R.id.txtE) protected TextView txtE;
    @BindView(R.id.buttonX) protected ImageButton buttonX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_stat);
        ButterKnife.bind(this);

        setTypeFace();
        setListeners();
        changeScreenSize();
        displayHighScore();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonX:
                finish();
                break;
        }
    }

    /**
     * Get high score from shared preference
     */
    public void displayHighScore() {
        MyPreference yourPreference = MyPreference.getInstance(ScoreStat.this);
        int hiScoreN;
        int hiScoreE;

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
        viewNormalScore.setText(String.format(Locale.getDefault(), "%d", hiScoreN));
        viewExpertScore.setText(String.format(Locale.getDefault(), "%d", hiScoreE));
    }


    /**
     * Reduces the size. Change screen size to pop up.
     */
    public void changeScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.5), (int) (height * 0.3));
    }

    /**
     * Change font to style setFont.otf
     */
    private void setTypeFace() {
        Typeface jenFont = Typeface.createFromAsset(getAssets(), "setFont.otf");
        viewNormalScore.setTypeface(jenFont, Typeface.BOLD);
        viewExpertScore.setTypeface(jenFont, Typeface.BOLD);
        txtHighS.setTypeface(jenFont, Typeface.BOLD);
        txtE.setTypeface(jenFont, Typeface.BOLD);
        txtN.setTypeface(jenFont, Typeface.BOLD);
    }

    private void setListeners() {
        buttonX.setOnClickListener(this);
    }
}

