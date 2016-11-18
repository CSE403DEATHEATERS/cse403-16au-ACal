package com.acalendar.acal.Friend;


public class Friend {
    String name;
    String email;
    String username;
    String userId;
    boolean selected = false;

    public Friend(String name, boolean selected) {
        super();
        this.name = name;
        this.selected = selected;
    }

    public Friend(String name, String email, String username, String userId) {
        super();
        this.name = name;
        this.email = email;
        this.username = username;
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String toString() {
        return this.name;
    }

}