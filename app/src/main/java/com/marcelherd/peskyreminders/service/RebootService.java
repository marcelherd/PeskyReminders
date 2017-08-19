package com.marcelherd.peskyreminders.service;

import android.app.IntentService;
import android.content.Intent;

import com.marcelherd.peskyreminders.model.Reminder;
import com.marcelherd.peskyreminders.persistence.ReminderRepository;
import com.marcelherd.peskyreminders.util.NotificatonUtil;

import java.util.List;

public class RebootService extends IntentService {

    private ReminderRepository reminderRepository;

    public RebootService() {
        super("RebootService");
        this.reminderRepository = new ReminderRepository(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String intentType = intent.getExtras().getString("caller");

        if (intentType == null) {
            return;
        }

        if (intentType.equals("RebootReceiver")) {
            List<Reminder> reminders = reminderRepository.active();
            NotificatonUtil.createAll(this, reminders);
        }
    }

}
