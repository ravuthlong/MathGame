package com.ravtrix.ravinder.mathgame;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameSetting extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ToggleButton.OnCheckedChangeListener {

    @BindView(R.id.radioGroup) protected RadioGroup radiogroup;
    @BindView(R.id.toggleButton) protected ToggleButton toggleButton;
    private Typeface setFont;
    @BindView(R.id.txtSound) protected TextView txtSound;
    @BindView(R.id.txtLevel) protected TextView txtLevel;
    static int gameLevel = 0; // Default at normal mode
    static boolean isSoundToggleChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Game Setting");
        setTypeface();
        setToggle();
        setListeners();
        populateRadioGroup();
        setSelectedLevel();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        gameLevel = radiogroup.getCheckedRadioButtonId();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (!toggleButton.isChecked()) {
            isSoundToggleChecked = false;
        } else {
            isSoundToggleChecked = true;
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

    private void setTypeface() {
        setFont = Typeface.createFromAsset(getAssets(), "setFont.otf");

        txtSound.setTypeface(setFont, Typeface.BOLD);
        txtLevel.setTypeface(setFont, Typeface.BOLD);
        toggleButton.setTypeface(setFont, Typeface.BOLD);
    }

    private void setToggle() {
        if (isSoundToggleChecked) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }
    }

    private void setSelectedLevel() {
        switch(gameLevel) {
            case 0:
                ((RadioButton)radiogroup.getChildAt(0)).setChecked(true);
                break;
            case 1:
                ((RadioButton)radiogroup.getChildAt(1)).setChecked(true);
                break;
        }
    }

    private void setListeners() {
        toggleButton.setOnCheckedChangeListener(this);
        radiogroup.setOnCheckedChangeListener(this);
    }
}
