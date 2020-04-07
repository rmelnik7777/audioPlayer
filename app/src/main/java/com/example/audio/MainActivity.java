package com.example.audio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ImageView playPause;
    SeekBar volumeSeekBar;
    SeekBar trackSeekBar;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPause = findViewById(R.id.playImage);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.original);

        trackSeekBar = findViewById(R.id.trackSeekBar);

        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        trackSeekBar.setMax(mediaPlayer.getDuration());
        trackSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                trackSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    public void track() {

    }

    public void play(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPause.setImageResource(R.drawable.ic_play_arrow_24dp);
        } else {
            mediaPlayer.start();
            playPause.setImageResource(R.drawable.ic_pause_24dp);
        }
    }

    public void next(View view) {
        trackSeekBar.setProgress(mediaPlayer.getDuration());
        mediaPlayer.seekTo(mediaPlayer.getDuration());
        mediaPlayer.pause();
        playPause.setImageResource(R.drawable.ic_play_arrow_24dp);
    }

    public void previous(View view) {
        trackSeekBar.setProgress(0);
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
        playPause.setImageResource(R.drawable.ic_play_arrow_24dp);
    }
}
