package com.acalendar.acal.Login;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Account implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.lastname);
        dest.writeString(this.firstname);
    }

    protected Account(Parcel in) {
        this.userId = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.lastname = in.readString();
        this.firstname = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public String toString() {
        return userId + username + email + lastname + firstname;
    }

    @Override
    public int hashCode() {
        return this.userId.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return ((Account)o).userId.equals(this.userId);
    }
}
