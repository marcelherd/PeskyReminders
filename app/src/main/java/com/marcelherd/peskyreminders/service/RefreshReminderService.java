package com.marcelherd.peskyreminders.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;
import com.marcelherd.peskyreminders.persistence.ReminderRepository;
import com.marcelherd.peskyreminders.util.NotificatonUtil;

/**
 * Refreshes all reminders (i.e. activates them) and then runs ActiveNotificationService.
 *
 * @see ActiveNotificationService
 */
public class RefreshReminderService extends WakefulIntentService {

    private ReminderRepository reminderRepository;

    public RefreshReminderService() {
        super("RefreshReminderService");
        this.reminderRepository = new ReminderRepository(this);
    }

    @Override
    protected void doWakefulWork(Intent intent) {
        reminderRepository.activateAll();
        NotificatonUtil.createAll(this, reminderRepository.active());
    }

}
