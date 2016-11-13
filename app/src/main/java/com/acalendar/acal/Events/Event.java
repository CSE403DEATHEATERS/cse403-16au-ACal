package com.acalendar.acal.Events;

import com.acalendar.acal.Login.Account;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Front end Event Model for storing event data.
 */
public class Event {

    /**
     * Fields that are not editable
     */
    private String eid;
    private String ownerId;
    private Date createTime;
    /**
     * Event Info fields
     */
    private String eventTitle;
    private Date startTime;
    private Date endTime;
    private Location location;
    private String description;
    private Boolean isPublic;
    private List<Account> listOfParticipantUsers;


    public Event(String eid, String ownerId, Date createTime,
                 String eventTitle, Date startTime, Date endTime,
                 String address, String description, Boolean isPublic) {
        this.eid = eid;
        this.ownerId = ownerId;
        this.createTime = createTime;
        this.eventTitle = eventTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        setLocation(address);
        this.description = description;
        this.isPublic = isPublic;
        listOfParticipantUsers = new ArrayList<>();
    }

    public Event(String eventTitle, Date startTime, Date endTime,
                 String address, String description, Boolean isPublic) {
        this(null, null, null, eventTitle, startTime, endTime, address, description, isPublic);
    }

    public void setEventId(String eid) {
        if (this.eid != null) {
            this.eid = eid;
        }
    }

    public void setOwnerId(String userId) {
        if (this.ownerId != null) {
            this.ownerId = userId;
        }
    }

    public void setCreateTime(Date time) {
        if (this.createTime != null) {
            this.createTime = time;
        }
    }

    public void addParticipant(Account friend) {
        listOfParticipantUsers.add(friend);
    }

    public List<String> getListOfParticipantsUids() {
        List<String> listOfUserids = new ArrayList<>();
        for (Account u : listOfParticipantUsers) {
            listOfUserids.add(u.getUserId());
        }
        return listOfUserids;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time passed in should not be null!");
        }

        this.startTime = startTime;
    }

    public Date getEndTime() {
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

    public void setLocation(String address) {
        if (address != null && address.length() != 0) {
            //TODO: validate the addr, valid then put in actual data, otherwise putin fake data
            /*
            "location" = {
                "lat" = "$input.params('lat')",
                "lng" = "$input.params('lng')",
                "address" = "$input.params('address')",
                "postal" = "$input.params('postal')",
                "state" = "$input.params('state')",
                "streetName" = "$input.params('streetName')",
                "streetNumber" = "$input.params('streetNumber')"}
            */
            Location l = new Location(address);
            this.location = l;
        }
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



//    @Override
//    public int compareTo(Event rhs) {
//        return 0;
//    }
}
