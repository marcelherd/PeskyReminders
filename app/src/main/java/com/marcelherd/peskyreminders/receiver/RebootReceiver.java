package com.marcelherd.peskyreminders.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.marcelherd.peskyreminders.service.ActiveNotificationService;
import com.marcelherd.peskyreminders.service.RefreshReminderService;

import java.util.Calendar;

public class RebootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Restore notifications for active reminders
        Intent notificationServiceIntent = new Intent(context, ActiveNotificationService.class);
        notificationServiceIntent.putExtra("caller", "RebootReceiver");
        context.startService(notificationServiceIntent);

        // Refresh reminders regularly
        if (intent.getAction() == null) {
            WakefulIntentService.sendWakefulWork(context, RefreshReminderService.class);
        } else {
            scheduleRefreshReminders(context);
        }
    }

    public static void scheduleRefreshReminders(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, RebootReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
