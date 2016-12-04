package com.ravtrix.ravinder.mathgame;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ravinder on 12/23/15.
 */
class MyPreference {
    private static MyPreference yourPreference;
    private SharedPreferences sharedPreferences;

    static MyPreference getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new MyPreference(context);
        }
        return yourPreference;
    }

    private MyPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("YourCustomNamedPreference", Context.MODE_PRIVATE);
    }

    void saveData(String key, int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    int getData(String key) {
        if (sharedPreferences!= null) {
            return sharedPreferences.getInt(key, 0);
        }
        return 0;
    }
}
