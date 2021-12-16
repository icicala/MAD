package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static int indexSong;
    private TextView artistTextView;
    private TextView titleTextView;
    private TextView albumTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artistTextView = findViewById(R.id.id_artist);
        titleTextView = findViewById(R.id.id_title);
        albumTextView = findViewById(R.id.id_album);

    }

    public void onClickRandom(View view) {

        indexSong = randomlySelectSong();

    }

    private int randomlySelectSong() {

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);
        int randomIndex = new Random().nextInt(cursor.getCount() - 1);
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(randomIndex);
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));

            artistTextView.setText(artist);
            titleTextView.setText(title);
            albumTextView.setText(album);
        }

        cursor.close();
        return randomIndex;

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        indexSong = preferences.getInt("IndexSong", -1);

        if (indexSong != -1) {
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);

            cursor.moveToPosition(indexSong);
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            artistTextView.setText(artist);
            titleTextView.setText(title);
            albumTextView.setText(album);
            cursor.close();
        } else {
            indexSong = randomlySelectSong();
        }

        Log.i("IndexIn", indexSong + " onResume");


    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("IndexSong", indexSong);
        Log.i("IndexOut", indexSong + " onPause");
        editor.apply();


    }
}