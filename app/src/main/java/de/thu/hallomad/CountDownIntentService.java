package de.thu.hallomad;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class CountDownIntentService extends IntentService {

    public CountDownIntentService() {
        super("CountDownIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("CountDownIntentService", "Start operation");
        for (int i = 0; i <= 100; i += 10) {
            try {
                Thread.sleep(1000);
                Log.d("CountDownIntentService", i + " % completed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("CountDownIntentService", "Ended operation");
    }
}
