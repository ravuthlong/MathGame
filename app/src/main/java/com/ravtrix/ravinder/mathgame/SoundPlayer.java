package com.ravtrix.ravinder.mathgame;

import android.content.Context;
import android.media.MediaPlayer;

// Class to start sound

class SoundPlayer {

    private MediaPlayer mp;
    private Context context;
    private int soundID;

    SoundPlayer(Context context, int soundID) {
        this.context = context;
        this.soundID = soundID;
    }

    /**
     * Play the sound and set listener
     */
    void playSound() {
        mp = MediaPlayer.create(context, soundID);
        mp.start();
        onCompleteListener();
    }

    /**
     * Reset and release resource upon completion
     */
    private void onCompleteListener() {
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
            }
        });
    }
}
