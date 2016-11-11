package model;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {
    private String eventId;
    private String ownerId;
    private String title;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String description;
    private Location location;
    private List<Map<String, String>> attendee;
    private boolean isPublic;

    public Map<String, AttributeValue> getInfo() {
        Map<String, AttributeValue> info = new HashMap<String, AttributeValue>();
        AttributeValue eventId = new AttributeValue();
        eventId.setS(this.eventId);
        AttributeValue ownerId = new AttributeValue();
        ownerId.setS(this.ownerId);
        AttributeValue title = new AttributeValue();
        title.setS(this.title);
        AttributeValue eventId = new AttributeValue();
        eventId.setS(this.eventId);
        AttributeValue eventId = new AttributeValue();
        eventId.setS(this.eventId);
        AttributeValue eventId = new AttributeValue();
        eventId.setS(this.eventId);
        AttributeValue eventId = new AttributeValue();
        eventId.setS(this.eventId);
        info.put("eventId", );

        return info;
    }

    public class Location {
        double lat;
        double lng;
        String address;
        int postal;
        String state;
        String streetName;
        String streetNumber;

        public Location(double lat, double lng, String address, int postal, String state, String streetName, String streetNumber) {
            this.lat =  lat;
            this.lng = lng;
            this.address = address;
            this.postal = postal;
            this.state = state;
            this.streetName = streetName;
            this.streetNumber = streetNumber;
        }

        public Map<String, AttributeValue> getInfo() {
            Map<String, AttributeValue> info = new HashMap<String, AttributeValue>();

        }
    }

}
