package com.ravtrix.ravinder.mathgame;

import android.content.Context;
import android.media.MediaPlayer;

// Class to start sound

public class SoundPlayer {

    MediaPlayer mp;
    Context context;
    int soundID;

    public SoundPlayer(Context context, int soundID) {
        this.context = context;
        this.soundID = soundID;
    }

    public void playSound() {
        mp = MediaPlayer.create(context, soundID);
        mp.start();
        onCompleteListener();
    }

    public void onCompleteListener() {
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });
    }
}
