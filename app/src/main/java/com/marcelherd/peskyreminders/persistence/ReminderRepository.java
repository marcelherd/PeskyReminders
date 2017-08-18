package com.marcelherd.peskyreminders.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.marcelherd.peskyreminders.model.Reminder;
import com.marcelherd.peskyreminders.persistence.DatabaseHelper;

import java.util.List;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class ReminderRepository {

    private DatabaseHelper databaseHelper;

    public ReminderRepository(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
    }

    public List<Reminder> all() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return cupboard().withDatabase(db).query(Reminder.class).list();
    }

    public List<Reminder> active() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return cupboard().withDatabase(db).query(Reminder.class).withSelection("active = ?", "true").list();
    }

    public Reminder one(Long id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return cupboard().withDatabase(db).get(Reminder.class, id);
    }

    public long createOrUpdate(Reminder reminder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard().withDatabase(db).put(reminder);
    }

    public boolean delete(Reminder reminder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard().withDatabase(db).delete(reminder);
    }

}
