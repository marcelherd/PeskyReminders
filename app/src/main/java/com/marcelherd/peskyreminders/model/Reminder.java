package com.marcelherd.peskyreminders.model;

public class Reminder {

    private Long _id;
    private boolean active;
    private String text;

    public Reminder() { this.active = true; }
    public Reminder(String text) { this.text = text; this.active = true; }

    public int getId() { return this._id.intValue(); }
    public boolean isActive() { return this.active; }
    public void setActive(boolean active) { this.active = active; }
    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }

    public String toString() {
        return text;
    }

}
