package com.ravtrix.ravinder.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

// Starting activity screen with play and high score buttons

public class StartingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton bStart, bRank;
    private ImageButton bSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_starting);

        bStart = (ImageButton) findViewById(R.id.bStart);
        bSetting = (ImageButton) findViewById(R.id.bSetting);
        bRank = (ImageButton) findViewById(R.id.bRank);
        bStart.setOnClickListener(this);
        bRank.setOnClickListener(this);
        bSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bStart:
                new MathGenerator().setStartingNumber(0);
                Intent gameModeIntent = new Intent(this, GameModeActivity.class);
                startActivity(gameModeIntent);
                break;
            case R.id.bRank:
                Intent highScoreIntent = new Intent(this, ScoreStat.class);
                startActivity(highScoreIntent);
                break;
            case R.id.bSetting:
                Intent settingIntent = new Intent(this, GameSetting.class);
                startActivity(settingIntent);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
