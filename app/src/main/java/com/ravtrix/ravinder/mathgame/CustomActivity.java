package com.ravtrix.ravinder.mathgame;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Ravinder on 2/6/16.
 */
public class CustomActivity extends AppCompatActivity {

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT > 19) {//19 or above api
            this.getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        } else {
            //for lower api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}
