package com.marcelherd.peskyreminders;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
    }

    public void cancel(View view) {
        NavUtils.navigateUpFromSameTask(this);
    }

    public void save(View view) {

    }

}
