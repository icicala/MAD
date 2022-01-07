package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {
    private static int indexSong = 2;
    private TextView artistTextView;
    private TextView titleTextView;
    private TextView albumTextView;
    private MediaPlayer mediaPlayer;
    private Button playPause;
    private Cursor cursor;
    private String uriString;
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artistTextView = findViewById(R.id.id_artist);
        titleTextView = findViewById(R.id.id_title);
        albumTextView = findViewById(R.id.id_album);

        playPause = findViewById(R.id.id_play_pause);
        playPause.setEnabled(false);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());

        contentResolver = getContentResolver();
//        cursor = contentResolver.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);
//        cursor.moveToPosition(indexSong);
//
//        uriString = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
//        Uri myUri = Uri.parse(uriString);
//        try {
//            mediaPlayer.setDataSource(getApplicationContext(), myUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.setOnPreparedListener(this);
//        mediaPlayer.prepareAsync();

    }

    public void onClickRandom(View view) {

//        indexSong = randomlySelectSong();


        cursor = contentResolver.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);
        int randomIndex = new Random().nextInt(cursor.getCount() - 1);
        if (cursor.getCount() > 0) {
            playPause.setEnabled(false);

            cursor.moveToPosition(randomIndex);
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));

            artistTextView.setText(artist);
            titleTextView.setText(title);
            albumTextView.setText(album);

            uriString = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            Uri myUri = Uri.parse(uriString);
            try {
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getApplicationContext(), myUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(true);
        }
        cursor.close();


    }

//    private int randomlySelectSong() {
//
//        ContentResolver contentResolver = getContentResolver();
//        cursor = contentResolver.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);
//        int randomIndex = new Random().nextInt(cursor.getCount() - 1);
//        if (cursor.getCount() > 0) {
//            playPause.setEnabled(false);
//
//            cursor.moveToPosition(randomIndex);
//            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//
//            artistTextView.setText(artist);
//            titleTextView.setText(title);
//            albumTextView.setText(album);
//
//            uriString = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
//            Uri myUri = Uri.parse(uriString);
//            try {
//                mediaPlayer.setDataSource(getApplicationContext(), myUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mediaPlayer.setOnPreparedListener(this);
//            mediaPlayer.prepareAsync();
//        }
//
//        cursor.close();
//        return randomIndex;
//
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
//        indexSong = preferences.getInt("IndexSong", 1);
//
//        if (indexSong != 1) {
//            ContentResolver contentResolver = getContentResolver();
//            cursor = contentResolver.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);
//
//            cursor.moveToPosition(indexSong);
//            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
//            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
//            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
//            artistTextView.setText(artist);
//            titleTextView.setText(title);
//            albumTextView.setText(album);
//            cursor.close();
//        } else {
//            indexSong = randomlySelectSong();
//        }
//
//        Log.i("IndexIn", indexSong + " onResume");
//
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        editor.putInt("IndexSong", indexSong);
//        Log.i("IndexOut", indexSong + " onPause");
//        editor.apply();
//
//
//    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playPause.setEnabled(true);
    }

    public void onClickPlayPause(View view) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }

    }
}