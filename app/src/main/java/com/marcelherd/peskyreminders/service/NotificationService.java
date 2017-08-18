package com.marcelherd.peskyreminders.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.marcelherd.peskyreminders.R;
import com.marcelherd.peskyreminders.activity.MainActivity;
import com.marcelherd.peskyreminders.model.Reminder;
import com.marcelherd.peskyreminders.persistence.ReminderRepository;
import com.marcelherd.peskyreminders.util.NotificatonUtil;

import java.util.List;

public class NotificationService extends Service {

    private ReminderRepository reminderRepository;

    public NotificationService() {
        this.reminderRepository = new ReminderRepository(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        List<Reminder> reminders = reminderRepository.active();

        for (Reminder reminder : reminders) {
            NotificatonUtil.create(this, reminder);
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
