package com.acalendar.acal.Login;

/**
 * Created by Yaoz on 11/11/16.
 */

public class LoginedAccount {
    private static Account user;

    public Account getCurrentUser() {
        if (user == null) {
            throw new NullPointerException();
        }
        return new Account(user);
    }

    public void logIn(String username, String password) {
        // TODO
    }

    public void signUp() {
        // TODO
    }

    public void logOut() {
        user = null;
    }

    public boolean logedIn() {
        return user != null;
    }

    public String getUserName() {
        return user.getUsername();
    }

    public String getUserFullName() {
        return user.getFirstname() + " " + user.getLastname();
    }

    public String getEmail() {
        return user.getEmail();
    }
}
