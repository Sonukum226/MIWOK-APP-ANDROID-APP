package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class NumberActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    private AudioManager mAudioManager;

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


    /*
    * This listner gets triggeres when the {@link Mediaplayer} has completed
    * playing the audio file
    * */


    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_activity);
        //requesting audio focus
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("Two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("Three", "toloolousu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_five, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("Ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));


        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get the object at the given position the user clicked on
                Word word = words.get(position);


                //release media player if it currently exits because we are about to
                //play a diffrent song
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //mAudioManager.registerMediaButtonEventReceiver();
                    // Start playback
                    //create and setup the{@link Medisplayer} for the audio resource assiciated
                    //with the current word

                    mediaPlayer = MediaPlayer.create(NumberActivity.this, word.getmAudioresourceid());
                    //start the media file
                    mediaPlayer.start();


                    //setup a listner on the media player, so that we can stop and release the
                    //media player once the sound has finished playing

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

            //This notifies the system that you no longer require focus and
            // unregisters the associated OnAudioFocusChangeListener. If you
            // requested transient focus, this will notify an app that paused or ducked
            // that it may continue playing or restore its volume.
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
}

