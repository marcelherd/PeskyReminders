package com.marcelherd.peskyreminders.model;

public class Reminder {

    private boolean active;
    private String text;

    public Reminder() { }
    public Reminder(String text) { this.text = text; }

    private boolean isActive() { return this.active; }
    private void setActive(boolean active) { this.active = active; }
    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }

    public void toggle() { this.active = !this.active; }

    public String toString() {
        return text;
    }

}
