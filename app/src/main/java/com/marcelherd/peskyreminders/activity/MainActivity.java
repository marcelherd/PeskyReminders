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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marcelherd.peskyreminders.R;
import com.marcelherd.peskyreminders.model.Reminder;
import com.marcelherd.peskyreminders.service.ReminderService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ReminderService reminderService;

    private List<Reminder> reminders;

    private ArrayAdapter<Reminder> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        reminderService = new ReminderService(this);
        reminders = reminderService.all();

        ListView listView = (ListView) findViewById(R.id.main_list_reminders);
        arrayAdapter = new ArrayAdapter<Reminder>(this, android.R.layout.simple_list_item_1, reminders);
        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        if (reminderService.delete(reminder)) {
            arrayAdapter.remove(reminder);
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                long id = data.getLongExtra("id", -1);
                Reminder newReminder = reminderService.one(id);
                arrayAdapter.add(newReminder);
            }
        }
    }

    public void addReminder(View view) {
        Intent intent = new Intent(this, AddReminderActivity.class);
        startActivityForResult(intent, 1);
    }

}
