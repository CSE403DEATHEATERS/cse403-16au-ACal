package com.acalendar.acal.Events;

import java.sql.Time;

/**
 * Event Model for storing event data.
 */
public class Event {

    /**
     * Fields that are not editable
     */
    private final String eid;
    private final String ownerId;
    private final Time createTime;
    /**
     * Event Info fields
     */
    private String eventTitle;
    private Time startTime;
    private Time endTime;
    private String location;
    private String description;
    private Boolean isPublic;


    public Event(String eid, String ownerId, Time createTime,
                 String eventTitle, Time startTime, Time endTime,
                 String location, String description, Boolean isPublic) {
        this.eid = eid;
        this.ownerId = ownerId;
        this.createTime = createTime;
        this.eventTitle = eventTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
        this.isPublic = isPublic;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
