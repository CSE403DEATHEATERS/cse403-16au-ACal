package model;

import java.util.Date;

public class Event {
    private String eventId;
    private String ownerId;
    private String title;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String description;
    private Location location;
    private boolean isPublic;

    public class Location {
        double lat;
        double lng;
        int postal;

    }

}
