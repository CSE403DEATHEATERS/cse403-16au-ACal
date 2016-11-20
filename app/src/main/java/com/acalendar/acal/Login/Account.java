package com.acalendar.acal.Login;

import java.io.Serializable;

public class Account implements Serializable {
    protected String userId;
    protected String username;
    protected String email;
    protected String lastname;
    protected String firstname;

    public Account(String userId, String username, String email, String lastname, String firstname) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public Account(Account account) {
        this.userId = account.userId;
        this.username = account.username;
        this.email = account.email;
        this.lastname = account.lastname;
        this.firstname = account.firstname;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEMail(String email) {
        this.email = email;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
