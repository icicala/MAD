package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

                Log.d("Music", album + " " + artist + " " + title + " " + time);
            }
        }
        cursor.close();

    }
}