package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudiomanager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT|| focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                        //Audiofocus_Loss_Transient means temporary loss of audio focus
                        //Audiofocus_Loss_Transient_can_duck means temporary loss of audio focus,can
                        //duck or lower volume if applicable
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //The AUDIOFOCUS_GAIN means that regained focus and can
                        //resume playback
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //The AUDIOFOCUS_LOSS means we have audio focus and
                        //stop playback and cleanup resourcecs
                        releaseMediaPlayer();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors_activity);
        mAudiomanager=(AudioManager) getSystemService(AUDIO_SERVICE);  //Requesting Audio Service
        final ArrayList<Word> color=new ArrayList<>();
        color.add(new Word("Red","Wetetti",R.drawable.color_red,R.raw.color_red));
        color.add(new Word("Green","Chokokki",R.drawable.color_green,R.raw.color_green));
        color.add(new Word("Brown","takaakki",R.drawable.color_brown,R.raw.color_brown));
        color.add(new Word("Gray","Topoppi",R.drawable.color_gray,R.raw.color_gray));
        color.add(new Word("Black","Kululli",R.drawable.color_black,R.raw.color_black));
        color.add(new Word("White","Kwlwlli",R.drawable.color_white,R.raw.color_white));
        color.add(new Word("Dusty Yellow","Topiise",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        color.add(new Word("Mustard Yellow","Chiwiite",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        WordAdapter adapter=new WordAdapter(this,color,R.color.category_colors);
        ListView listView=(ListView) findViewById(R.id.color_item);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Get the object at the given position the user clicked on
                Word voice = color.get(position);
                releaseMediaPlayer();

                int result = mAudiomanager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //mAudioManager.registerMediaButtonEventReceiver();
                    // Start playback
                    //create and setup the{@link Medisplayer} for the audio resource assiciated
                    //with the current word
                    


                    mediaPlayer = MediaPlayer.create(ColorsActivity.this, voice.getmAudioresourceid());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });



   }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            mAudiomanager.abandonAudioFocus(afChangeListener);
        }

    }
}

