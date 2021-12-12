package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    StopWatchRunnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        runnable = new StopWatchRunnable(findViewById(R.id.textView));
        Thread t = new Thread(runnable);
        t.start();
    }

    public void onClickStartStop(View view) {
        runnable.startStop();

    }

    public void onClickReset(View view) {
        runnable.reset();
    }
}