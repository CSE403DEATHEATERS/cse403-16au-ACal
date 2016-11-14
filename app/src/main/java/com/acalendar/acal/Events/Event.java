package com.acalendar.acal.Events;

import com.acalendar.acal.Login.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Front end Event Model for storing event data.
 */
public class Event {

    /**
     * Fields that are not editable after first set.
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
        setLocation(address); // Note: is the address passed in is empty string or null, Location is not set
        this.description = description;
        this.isPublic = isPublic;
        listOfParticipantUsers = new ArrayList<>();
    }

    public Event(String eventTitle, Date startTime, Date endTime,
                 String address, String description, Boolean isPublic) {
        this(null, null, null, eventTitle, startTime, endTime, address, description, isPublic);
    }

    public String getEventId() {
        return this.eid;
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

    public void setListOfParticipantsUids(List<String> listOfUserids) {
        //this.listOfParticipantUsers = new ArrayList<>(listOfUserids);
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

    public void setStartTime(Date startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time passed in should not be null!");
        }

        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("End time passed in should not be null!");
        }

        this.endTime = endTime;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * An Overload funciton for setLocation. Used only
     *
     * @param address the address text that user specified
     */
    private void setLocation(String address) {
        if (address != null && address.length() != 0) {
            //TODO: validate the addr, valid then put in actual data, otherwise putin fake data

            Location l = new Location(address);  // for now it contains a lot of fake data.
            this.location = l;
        }
    }

    public void setLocation(Location location) {
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



//    @Override
//    public int compareTo(Event rhs) {
//        return 0;
//    }
}
