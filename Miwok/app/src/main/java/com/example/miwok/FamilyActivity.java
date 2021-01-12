package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
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

    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_activity);
        mAudioManager=(AudioManager) getSystemService(AUDIO_SERVICE);
        final ArrayList<Word> family = new ArrayList<Word>();
        family.add(new Word("Father", "epe", R.drawable.family_father, R.raw.family_father));
        family.add(new Word("Mother", "eta", R.drawable.family_mother, R.raw.family_mother));
        family.add(new Word("Son", "angsi", R.drawable.family_son, R.raw.family_son));
        family.add(new Word("Daughter", "Tune", R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new Word("Older Brother", "Taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        family.add(new Word("Younger Brother", "Chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        family.add(new Word("Younger Sister", "Kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        family.add(new Word("older Sister", "TETE", R.drawable.family_older_sister, R.raw.family_older_sister));
        family.add(new Word("Grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        family.add(new Word("Grand Father", "Paapa", R.drawable.family_father, R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(this, family, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.family_item);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word fam = family.get(position);//get the object when the user clicked on
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

                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, fam.getmAudioresourceid());
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
                mAudioManager.abandonAudioFocus(afChangeListener);
            }

    }

}