package com.acalendar.acal.Events;

import java.sql.Time;

/**
 * Front end Event Model for storing event data.
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
    private Location location;
    private String description;
    private Boolean isPublic;


    public Event(String eid, String ownerId, Time createTime,
                 String eventTitle, Time startTime, Time endTime,
                 Location location, String description, Boolean isPublic) {
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
        if (eventTitle == null) {
            throw new IllegalArgumentException("Event title passed in should not be null!");
        }

        this.eventTitle = eventTitle;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time passed in should not be null!");
        }

        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("End time passed in should not be null!");
        }

        this.endTime = endTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location passed in should not be null!");
        }

        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description passed in should not be null!");
        }

        this.description = description;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        if (aPublic == null) {
            throw new IllegalArgumentException("Boolean passed in should not be null!");
        }

        isPublic = aPublic;
    }
}
