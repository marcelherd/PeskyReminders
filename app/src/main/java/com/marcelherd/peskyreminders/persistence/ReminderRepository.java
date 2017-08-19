package com.marcelherd.peskyreminders.persistence;

import android.content.ContentValues;
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

    /**
     * Returns all reminders.
     *
     * @return all reminders
     */
    public List<Reminder> all() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return cupboard().withDatabase(db).query(Reminder.class).list();
    }

    /**
     * Returns all active reminders.
     *
     * @return all active reminders
     */
    public List<Reminder> active() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return cupboard().withDatabase(db).query(Reminder.class).withSelection("active = ?", "1").list();
    }

    /**
     * Marks all reminders as active.
     *
     * @return the number of reminders that have been set to active
     */
    public int activateAll() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("active", true);
        return cupboard().withDatabase(db).update(Reminder.class, contentValues);
    }

    /**
     * Returns the reminder with the specified ID.
     *
     * @param id - the ID of the searched reminder
     * @return reminder if the specified ID
     */
    public Reminder one(Long id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        return cupboard().withDatabase(db).get(Reminder.class, id);
    }

    /**
     * Stores the reminder in the database, or updates it if it already exists.
     *
     * @param reminder - the reminder to be stored in the database
     * @return the ID of the reminder
     */
    public long createOrUpdate(Reminder reminder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard().withDatabase(db).put(reminder);
    }

    /**
     * Deletes the given reminder.
     *
     * @param reminder - the reminder which is to be deleted
     * @return true if the reminder has been deleted, otherwise false
     */
    public boolean delete(Reminder reminder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        return cupboard().withDatabase(db).delete(reminder);
    }

}
