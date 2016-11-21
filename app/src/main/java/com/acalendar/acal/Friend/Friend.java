package com.acalendar.acal.Friend;


import com.acalendar.acal.Login.Account;

public class Friend extends Account {

    private boolean selected = false;

    public Friend(String fname, boolean selected) {
        super(null, null, null, null, fname);
        this.selected = selected;
    }

    public Friend(String lastName, String firstName, String email, String username, String userId) {
        super(userId, username, email, lastName, firstName);
    }
    public String getName() {
        return this.firstname + this.lastname;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String toString() {
        return getName();
    }

}