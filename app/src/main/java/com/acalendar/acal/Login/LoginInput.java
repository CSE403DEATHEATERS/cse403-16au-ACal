package com.acalendar.acal.Login;

/**
 * Created by tongshen on 11/9/16.
 */

public class LoginInput {
    private String username;
    private String password;

    public LoginInput() {}

    public LoginInput(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
