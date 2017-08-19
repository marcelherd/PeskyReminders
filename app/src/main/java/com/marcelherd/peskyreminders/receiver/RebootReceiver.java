package com.marcelherd.peskyreminders.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.marcelherd.peskyreminders.model.Reminder;
import com.marcelherd.peskyreminders.service.RebootService;
import com.marcelherd.peskyreminders.util.NotificatonUtil;

public class RebootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, RebootService.class);
        serviceIntent.putExtra("caller", "RebootReceiver");
        context.startService(serviceIntent);
    }

}
