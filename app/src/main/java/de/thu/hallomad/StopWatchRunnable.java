package de.thu.hallomad;

import android.widget.TextView;

public class StopWatchRunnable implements Runnable {
    private boolean running = false;
    private int value = 0;
    private TextView textView;

    public StopWatchRunnable(TextView view) {
        textView = view;
    }

    // this runs in own thread
    @Override
    public void run() {
        while (true) {
            synchronized (StopWatchRunnable.this) {
                if (running) {
                    value++;
                    updateUI();
                }
            }
            // TODO: Run in background
            try {
                Thread.sleep(500); //sleep 1 sec
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    // are called in the UI THREAD
    synchronized public void startStop() {
        running = !running;
    }

    synchronized public void reset() {
        running = false;
        value = 0;
        updateUI();
    }

    // sent to UI thread
    private void updateUI() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                textView.setText(value + " seconds");
            }
        };
        textView.post(r);
    }

}
