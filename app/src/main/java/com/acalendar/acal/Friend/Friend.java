package com.acalendar.acal.Friend;


import android.os.Parcel;
import android.os.Parcelable;

import com.acalendar.acal.Login.Account;

public class Friend extends Account implements Parcelable {

    private boolean selected = false;

    public Friend(String fname, boolean selected) {
        super(null, null, null, null, fname);
        this.selected = selected;
    }

    public Friend(String lastName, String firstName, String email, String username, String userId) {
        super(userId, username, email, lastName, firstName);
    }
    public String getName() {
        return this.firstname + " " + this.lastname;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte(this.selected ? (byte) 1 : (byte) 0);
    }

    protected Friend(Parcel in) {
        super(in);
        this.selected = in.readByte() != 0;
    }

    public static final Creator<Friend> CREATOR = new Creator<Friend>() {
        @Override
        public Friend createFromParcel(Parcel source) {
            return new Friend(source);
        }

        @Override
        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}