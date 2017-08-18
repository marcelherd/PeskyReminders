package com.marcelherd.peskyreminders.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.marcelherd.peskyreminders.R;
import com.marcelherd.peskyreminders.model.Reminder;
import com.marcelherd.peskyreminders.persistence.ReminderRepository;
import com.marcelherd.peskyreminders.util.NotificatonUtil;

public class AddReminderActivity extends AppCompatActivity {

    private ReminderRepository reminderRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        reminderRepository = new ReminderRepository(this);
    }

    public void cancel(View view) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    public void save(View view) {
        EditText editText = (EditText) findViewById(R.id.addReminder_editText_reminder);
        String text = editText.getText().toString();

        if (text == null || text.isEmpty()) {
            editText.setError(getResources().getText(R.string.error_editText_empty));
        } else {
            Reminder reminder = new Reminder(text);

            long retval = reminderRepository.createOrUpdate(reminder);
            NotificatonUtil.create(this, reminder);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("id", retval);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

}
