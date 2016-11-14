package com.acalendar.acal;

import com.acalendar.acal.Events.Event;
import com.acalendar.acal.Events.Location;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Time;
import java.util.Date;

/**
 * Test class for event
 */
public class EventTest {

    private Event event;
    private Time createTime;
    private Time startTime;
    private Time endTime;
    private Boolean isPublic;

    @Mock
    private String address;

    private final String eid = "An event id";
    private final String ownerId = "An ownerId";
    private final String eventTitle = "An eventTitle";
    private final String description = "A description";

    @Before
    public void setup() {
        this.startTime = new Time((new Date()).getTime());
        this.endTime = new Time((new Date()).getTime());
        this.createTime = new Time((new Date()).getTime());
        this.isPublic = false;

        this.event = new Event(eid, ownerId, createTime, eventTitle, startTime, endTime, address, description, isPublic);
    }

    /////////////////////////////////////////////////////////////////////
    //////                    setEventTitle()                       /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setEventTitleTest_nullEventTitle() {
        event.setEventTitle(null);
    }

    /////////////////////////////////////////////////////////////////////
    //////                    setStartTime()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setStartTime_nullStartTime() {
        event.setStartTime(null);
    }

    /////////////////////////////////////////////////////////////////////
    //////                    setEndTime()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setEndTime_nullEndTime() {
        event.setEndTime(null);
    }

    /////////////////////////////////////////////////////////////////////
    //////                     setLocation()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setLocation_nullLocation() {
        event.setLocation(null);
    }

    /////////////////////////////////////////////////////////////////////
    //////                    setDescription()                      /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setDescription_nullDescription() {
        event.setDescription(null);
    }

    /////////////////////////////////////////////////////////////////////
    //////                      setIsPublic()                       /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setIsPublic_nullIsPublic() {
        event.setPublic(null);
    }

}
