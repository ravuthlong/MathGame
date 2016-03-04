package com.ravtrix.ravinder.mathgame;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GameSetting extends AppCompatActivity implements View.OnClickListener {
    RadioGroup radiogroup;
    ToggleButton toggleButton;
    Button bsaveSetting;
    Typeface setFont;
    TextView txtSound, txtLevel;
    static int gameLevel = 0; // Default at normal mode
    static boolean isSoundToggleChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Game Setting");

        setFont = Typeface.createFromAsset(getAssets(), "setFont.otf");

        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        bsaveSetting = (Button) findViewById(R.id.bsaveSetting);

        txtSound = (TextView) findViewById(R.id.txtSound);
        txtLevel = (TextView) findViewById(R.id.txtLevel);


        txtSound.setTypeface(setFont, Typeface.BOLD);
        txtLevel.setTypeface(setFont, Typeface.BOLD);
        bsaveSetting.setTypeface(setFont, Typeface.BOLD);
        toggleButton.setTypeface(setFont, Typeface.BOLD);


        bsaveSetting.setOnClickListener(this);

        if (isSoundToggleChecked) {
            toggleButton.setChecked(true);

        } else {
            toggleButton.setChecked(false);
        }

        populateRadioGroup();

        switch(gameLevel) {
            case 0:
                ((RadioButton)radiogroup.getChildAt(0)).setChecked(true);
                break;
            case 1:
                ((RadioButton)radiogroup.getChildAt(1)).setChecked(true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bsaveSetting:

                gameLevel = radiogroup.getCheckedRadioButtonId();

                if (!toggleButton.isChecked()) {
                    isSoundToggleChecked = false;
                } else {
                    isSoundToggleChecked = true;
                }

                Intent homeIntent = new Intent(this, StartingActivity.class);
                startActivity(homeIntent);
                break;
        }
    }

    public void populateRadioGroup() {

        String[] levelOptions = {"Normal", "Expert"};
        for (int i = 0; i < 2; i++) {
            RadioButton newRadioButton = new RadioButton(this);
            newRadioButton.setText(levelOptions[i]);
            newRadioButton.setId(i);
            newRadioButton.setTypeface(setFont, Typeface.BOLD);
            radiogroup.addView(newRadioButton);
        }
    }


    // Back press button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
