package com.acalendar.acal.Events;

import android.os.Parcel;
import android.os.Parcelable;

import com.acalendar.acal.Friend.Friend;
import com.acalendar.acal.Login.LoginedAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Front end Event Model for storing event data.
 */
public class Event implements Parcelable {

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
    private List<Friend> listOfParticipantUsers;


    /**
     * Constructor used to when data is parsed from database and a know eventId is provided.
     */
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

    /**
     * Constructor used when the event is newly created and eventId is unknown.
     */
    public Event(String eventTitle, Date startTime, Date endTime,
                 String address, String description, Boolean isPublic) {
        this(null, LoginedAccount.getUserId(), null, eventTitle, startTime, endTime, address, description, isPublic);
    }

    public String getEventId() {
        return this.eid;
    }

    public void setEventId(String eid) {
        if (this.eid == null) {
            this.eid = eid;
        }
    }

    public void setCreateTime(Date time) {
        if (this.createTime == null) {
            this.createTime = time;
        }
    }

    public void addParticipant(Friend friend) {
        listOfParticipantUsers.add(friend);
    }

    public void setListOfParticipants(List<Friend> listOfFriend) {
        this.listOfParticipantUsers = listOfFriend;
    }

    public List<Friend> getListOfParticipantingFriends() {
        return this.listOfParticipantUsers;
    }

    public List<String> getListOfParticipantingFriendsUserIds() {
        List<String> uids = new ArrayList<>();
        for (Friend f : listOfParticipantUsers) {
            uids.add(f.getUserId());
        }
        return uids;
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

    /**
     * Below is the code to make Event object Parcelable. Event need to be Parcelable to be
     * put into a Bundle object(saved in an Intent) and pass in to an Activity.
     * The Parcelable Event should not be used (passed around) when any of the fields is null.
     */


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eid);
        dest.writeString(this.ownerId);
        dest.writeLong(this.createTime != null ? this.createTime.getTime() : -1);
        dest.writeString(this.eventTitle);
        dest.writeLong(this.startTime != null ? this.startTime.getTime() : -1);
        dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
        dest.writeSerializable(this.location);
        dest.writeString(this.description);
        dest.writeValue(this.isPublic);
        dest.writeList(this.listOfParticipantUsers);
    }

    protected Event(Parcel in) {
        this.eid = in.readString();
        this.ownerId = in.readString();
        long tmpCreateTime = in.readLong();
        this.createTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
        this.eventTitle = in.readString();
        long tmpStartTime = in.readLong();
        this.startTime = tmpStartTime == -1 ? null : new Date(tmpStartTime);
        long tmpEndTime = in.readLong();
        this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
        this.location = (Location) in.readSerializable();
        this.description = in.readString();
        this.isPublic = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.listOfParticipantUsers = new ArrayList<>();
        in.readList(this.listOfParticipantUsers, Friend.class.getClassLoader());
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        Event e = (Event)o;
        return this.eid.equals(e.eid);
    }

    @Override
    public int hashCode() {
        return this.eid.hashCode();
    }
}
