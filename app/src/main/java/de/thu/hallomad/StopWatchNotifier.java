package de.thu.hallomad;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class StopWatchNotifier {
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    public StopWatchNotifier(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel("stop");
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel("stop", "Show stopwatch state", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        //3. Create Notification builder
        notificationBuilder = new NotificationCompat.Builder(context, "stop")
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Stop Watch Running ...")
                .setAutoCancel(false);
        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
        notificationBuilder.setContentIntent(resultPendingIntent);
    }


    public void showorUpdateNotification(int value) {
        notificationBuilder.setContentText(value + " seconds");
        notificationManager.notify(123, notificationBuilder.build());
    }

    public void removeNotification() {
        notificationManager.cancel(123);
    }
}
