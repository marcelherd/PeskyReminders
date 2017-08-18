package com.marcelherd.peskyreminders.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.marcelherd.peskyreminders.R;
import com.marcelherd.peskyreminders.activity.MainActivity;
import com.marcelherd.peskyreminders.model.Reminder;

public class NotificatonUtil {

    public static void cancel(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }

    public static void cancel(Context context, Reminder reminder) {
        cancel(context, reminder.getId());
    }

    public static void cancelAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void create(Context context, Reminder reminder) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
        PendingIntent notificationIntent = PendingIntent.getActivity(context, reminder.getId(), new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setContentTitle(reminder.getText())
                .setSmallIcon(R.drawable.ic_info_black_24dp)
                .setContentIntent(notificationIntent)
                .setOngoing(true)
                .build();
        notificationManager.notify(reminder.getId(), notification);
    }

}
