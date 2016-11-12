package model;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.BatchWriteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static dbManager.DynamoDBManager.dynamoDB;

public class Event {
    public static final String EVENT_TABLE_NAME = "acalendar-mobilehub-1275254137-eventInfo";
    public static final String EVENT_SHARING_TABLE_NAME = "acalendar-mobilehub-1275254137-eventSharing";
    public static final Table EVENT_TABLE = dynamoDB.getTable(EVENT_TABLE_NAME);
    public static final Table EVENT_SHARING_TABLE = dynamoDB.getTable(EVENT_SHARING_TABLE_NAME);
    private String eventId;
    private String ownerId;
    private String title;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private String description;
    private Location location;
    private List<String> attendees;
    private boolean isPublic;

    public Event(String eventId) {
        this.eventId = eventId;
        GetItemSpec getItem = new GetItemSpec().withPrimaryKey("eventId", eventId);
        Item item = EVENT_TABLE.getItem(getItem);
        if (item != null) {
            this.ownerId = item.getString("ownerId");
            this.title = item.getString("title");
            this.description = item.getString("description");
            this.isPublic = item.getBoolean("isPublic");
            this.location = new Location(item.getMap("location"));
        }
    }

    public Event(String ownerId, Long createTime, Long startTime, Long endTime, String title, String description,
                 Location location, boolean isPublic, List<String> attendees) {
        this.eventId = UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.location = location;
        this.createTime = new Date(createTime);
        if (startTime != null) {
            this.startTime = new Date(startTime);
        }
        if (endTime != null) {
            this.endTime = new Date(endTime);
        }
        try {
            Item item = new Item()
                    .withPrimaryKey("eventId", this.eventId)
                    .withString("ownerId", this.ownerId)
                    .withBoolean("isPublic", this.isPublic)
                    .withString("createTime", this.createTime.toString());
            if (startTime != null) {
                item.withString("startTime", this.startTime.toString());
            }
            if (endTime != null) {
                item.withString("endTime", this.endTime.toString());
            }
            if (location != null) {
                item.withMap("location", this.location.getInfo());
            }
            if (title != null) {
                item.withString("title", this.title);
            }
            if (description != null) {
                item.withString("description", this.description);
            }
            EVENT_TABLE.putItem(item);
            TableWriteItems attendeeList = new TableWriteItems(EVENT_SHARING_TABLE_NAME);
            for (String attendee : attendees) {
                attendeeList.addItemToPut(new Item().withPrimaryKey("eventId", this.eventId, "recipientId", attendee)
                                            .withString("status",  "PENDING")
                                            .withNumber("sentTime", new Date().getTime()));
            }
            BatchWriteItemSpec batch = new BatchWriteItemSpec().withTableWriteItems(attendeeList);
            dynamoDB.batchWriteItem(batch);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("eventId", this.eventId);
        info.put("ownerId", this.ownerId);
        info.put("title", this.title);
        info.put("description", this.description);
        info.put("attendees", this.attendees);
        info.put("isPublic", this.isPublic);
        return info;
    }

    public static boolean createEvent(String ownerId, Long createTime, Long startTime, Long endTime, String title,
                                      String description, Map<String, Object>location, boolean isPublic, List<String> attendees) {
        Event event = new Event(ownerId, createTime, startTime, endTime, title, description, new Location(location), isPublic, attendees);
        return event != null;
    }

    public static class Location {
        double lat;
        double lng;
        String address;
        int postal;
        String state;
        String streetName;
        String streetNumber;

        public Location(Map<String, Object> info) {
            if (info != null) {
                this.lat = (Double) info.get("lat");
                this.lng = (Double) info.get("lng");
                this.address = (String) info.get("address");
                this.postal = (Integer) info.get("postal");
                this.state = (String) info.get("state");
                this.streetName = (String) info.get("streetName");
                this.streetNumber = (String) info.get("streetNumber");
                System.out.println(lat + lng + address + postal + state);
            }
        }

        public Location(double lat, double lng, String address, int postal, String state, String streetName, String streetNumber) {
            this.lat =  lat;
            this.lng = lng;
            this.address = address;
            this.postal = postal;
            this.state = state;
            this.streetName = streetName;
            this.streetNumber = streetNumber;
        }

        public Map<String, Object> getInfo() {
            Map<String, Object> info = new HashMap<String, Object>();
            info.put("lat", new Double(this.lat));
            info.put("lng", new Double(this.lng));
            info.put("postal", new Integer(this.postal));
            info.put("address", this.address);
            info.put("state", this.state);
            info.put("streetName", this.streetName);
            info.put("streetNumber", this.streetNumber);
            return info;

        }
    }

}
