package de.thu.hallomad;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class StopWatchRunnable implements Runnable {
    private boolean running = false;
    private int value = 0;
    private TextView textView;
    private StopWatchNotifier stopWatchNotifier;

    public StopWatchRunnable(TextView view) {
        textView = view;
        this.stopWatchNotifier = new StopWatchNotifier(textView.getContext());
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
        Toast toast = Toast.makeText(textView.getContext(), "Clicked Start/Stop", Toast.LENGTH_LONG);
        toast.show();
        updateUI();


    }

    synchronized public void reset() {
        running = false;
        value = 0;
        updateUI();

        Snackbar.make(textView.getRootView(), "Reset Everything", BaseTransientBottomBar.LENGTH_LONG).show();

    }

    // sent to UI thread
    private void updateUI() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                textView.setText(value + " seconds");
                if (running) {
                    stopWatchNotifier.showorUpdateNotification(value);
                } else {
                    stopWatchNotifier.removeNotification();
                }
            }
        };
        textView.post(r);
    }

}
