package de.thu.hallomad;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExchangeRateUpdateNotifier {
    private static final int NOTIFICATION_ID = 107;
    private static String CHANNEL_ID = "updaterate_channel";
    private static String CHANNEL_DESCRIPTION = "Updated Currency Exchange Rates";

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    public ExchangeRateUpdateNotifier(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_DESCRIPTION, NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.small_update_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.small_update_icon))
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setContentTitle(CHANNEL_DESCRIPTION)
                .setAutoCancel(false);

        Intent backtoApplication = new Intent(context, MainActivity.class);
        backtoApplication.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, backtoApplication, 0);
        notificationBuilder.setContentIntent(resultPendingIntent);


    }

    public void showOrUpdateNotification(String value) {
        notificationBuilder.setContentText("Last Updated on: " + value);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public void removeNotification() {
        notificationManager.cancel(NOTIFICATION_ID);
    }

}
