package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent.getAction().equals(Intent.ACTION_SEND)) {
            Log.i("Share", intent.getStringExtra(Intent.EXTRA_TEXT));
        }


    }
}