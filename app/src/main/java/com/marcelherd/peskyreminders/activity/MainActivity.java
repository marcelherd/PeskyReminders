package com.marcelherd.peskyreminders.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.marcelherd.peskyreminders.R;
import com.marcelherd.peskyreminders.adapter.RemindersListAdapter;
import com.marcelherd.peskyreminders.model.Reminder;
import com.marcelherd.peskyreminders.persistence.ReminderRepository;
import com.marcelherd.peskyreminders.receiver.RebootReceiver;
import com.marcelherd.peskyreminders.util.NotificatonUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ReminderRepository reminderRepository;

    private ArrayList<Reminder> reminders;

    private RemindersListAdapter remindersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        reminderRepository = new ReminderRepository(this);
        reminders = new ArrayList<Reminder>(reminderRepository.all());

        ListView listView = (ListView) findViewById(R.id.main_list_reminders);
        remindersAdapter = new RemindersListAdapter(this, reminders);
        listView.setAdapter(remindersAdapter);
        registerForContextMenu(listView);

        NotificatonUtil.createAll(this, reminderRepository.active());
        RebootReceiver.scheduleRefreshReminders(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        if (view.getId() == R.id.main_list_reminders) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.add(Menu.NONE, 0, 0, getResources().getText(R.string.action_delete));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Reminder reminder = reminders.get(info.position);

        if (reminderRepository.delete(reminder)) {
            remindersAdapter.remove(reminder);
            NotificatonUtil.cancel(this, reminder.getId());
            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                long id = data.getLongExtra("id", -1);
                Reminder newReminder = reminderRepository.one(id);
                remindersAdapter.add(newReminder);
            }
        }
    }

    public void addReminder(View view) {
        Intent intent = new Intent(this, AddReminderActivity.class);
        startActivityForResult(intent, 1);
    }

}
