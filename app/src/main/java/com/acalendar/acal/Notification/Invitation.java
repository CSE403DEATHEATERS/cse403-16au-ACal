package com.acalendar.acal.Notification;

import com.acalendar.acal.Events.Event;

import java.util.Date;
import java.util.Map;

/**
 * Created by tongshen on 11/21/16.
 */

public class Invitation {
    private String title;
    private String ownerName;
    private String startTime;
    private String location;
    private boolean checked;

    public Invitation(String title, String ownerName, String startTime, String location, boolean checked) {
        this.title = title;
        this.ownerName = ownerName;
        this.startTime = startTime;
        this.location = location;
        this.checked = checked;
    }

    public boolean isSelected() {
        return checked;
    }
    public void setSelected(boolean checked) {
        this.checked = checked;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title + "\n" + "Owner: " + ownerName + "\t" + startTime + "\t" + location);
        return sb.toString();
    }
}
