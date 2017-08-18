package com.marcelherd.peskyreminders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.marcelherd.peskyreminders.R;
import com.marcelherd.peskyreminders.model.Reminder;
import com.marcelherd.peskyreminders.persistence.ReminderRepository;
import com.marcelherd.peskyreminders.util.NotificatonUtil;

import java.util.ArrayList;

public class RemindersListAdapter extends ArrayAdapter<Reminder> {

    private ReminderRepository reminderRepository;

    public RemindersListAdapter(Context context, ArrayList<Reminder> reminders) {
        super(context, 0, reminders);
        reminderRepository = new ReminderRepository(context);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final Reminder reminder = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_reminders_row, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.row_text);
        Switch switchWidget = (Switch) convertView.findViewById(R.id.row_switch);

        textView.setText(reminder.getText());
        switchWidget.setChecked(reminder.isActive());

        switchWidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NotificatonUtil.create(parent.getContext(), reminder);
                } else {
                    NotificatonUtil.cancel(parent.getContext(), reminder);
                }

                reminder.setActive(isChecked);
                reminderRepository.createOrUpdate(reminder);
            }

        });

        return convertView;
    }
}
