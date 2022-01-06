package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = MediaPlayer.create(this, R.raw.luftrum__forestsurroundings);
        player.setLooping(true);
    }

    public void imageClicked(View view) {
        if (!player.isPlaying()) {
            player.start();
        } else {
            player.pause();
        }
    }
}