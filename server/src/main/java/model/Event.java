package model;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.BatchWriteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.math.BigDecimal;
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
        GetItemSpec getItem = new GetItemSpec().withPrimaryKey("eventId", eventId);
        Item item = EVENT_TABLE.getItem(getItem);
        if (item != null) {
            this.eventId = eventId;
            this.ownerId = item.getString("ownerId");
            this.title = item.getString("title");
            this.description = item.getString("description");
            this.isPublic = item.getBoolean("isPublic");
            this.createTime = new Date(item.getNumber("createTime").longValue());
            if (item.getNumber("startTime") != null) {
                this.startTime = new Date(item.getNumber("startTime").longValue());
            }
            if (item.getNumber("endTime") != null) {
                this.endTime = new Date(item.getNumber("endTime").longValue());
            }
            if (item.getMap("location") != null && !item.getMap("location").isEmpty()) {
                System.out.println("load location");
                this.location = new Location(item.getMap("location"), true);
            }
            QuerySpec query = new QuerySpec().withHashKey(new KeyAttribute("eventId", this.eventId));
            Iterator<Item> items = EVENT_SHARING_TABLE.query(query).iterator();
            this.attendees = new ArrayList<String>();
            while (items.hasNext()) {
                Item attendee = items.next();
                attendees.add(attendee.getString("recipientId"));
            }
        }
    }

    public Event(String ownerId, Long startTime, Long endTime, String title, String description,
                 Location location, boolean isPublic, List<String> attendees) {
        this.eventId = UUID.randomUUID().toString();
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.location = location;
        this.createTime = new Date();
        this.startTime = new Date(startTime);
        this.endTime = new Date(endTime);
        try {
            Item item = new Item()
                    .withPrimaryKey("eventId", this.eventId)
                    .withString("ownerId", this.ownerId)
                    .withBoolean("isPublic", this.isPublic)
                    .withNumber("createTime", this.createTime.getTime())
                    .withNumber("startTime", this.startTime.getTime())
                    .withNumber("endTime", this.endTime.getTime());
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
            attendeeList.addItemToPut(new Item().withPrimaryKey("eventId", this.eventId, "recipientId", ownerId)
                    .withString("status",  "ACCEPT")
                    .withNumber("sentTime", new Date().getTime()));
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

    public Map<String, Object> update(String ownerId, String title, Long startTime,
                                      Long endTime, String description, boolean isPublic,
                                      Map<String, Object> location, List<String> attendees) {
        return null;
    }

    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<String, Object>();
        if (this.eventId == null || this.eventId.isEmpty()) {
            return info;
        }
        info.put("eventId", this.eventId);
        info.put("ownerId", this.ownerId);
        info.put("isPublic", this.isPublic);
        info.put("createTime", this.createTime.getTime());
        info.put("startTime", this.startTime.getTime());
        info.put("endTime", this.endTime.getTime());
        if (this.title != null && !this.title.isEmpty()) {
            info.put("title", this.title);
        }
        if (this.location != null) {
            info.put("location", this.location.getInfo());
        }
        if (this.description != null && !this.description.isEmpty()) {
            info.put("description", this.description);
        }
        if (this.attendees != null && !this.attendees.isEmpty()) {
            info.put("attendees", this.attendees);
        }
        return info;
    }

    public static Map<String, Object> createEvent(String ownerId, Long startTime, Long endTime, String title,
                                      String description, Map<String, Object>location, boolean isPublic, List<String> attendees) {
        Event event = new Event(ownerId, startTime, endTime, title, description,
                (location != null && !location.isEmpty()) ? new Location(location) : null, isPublic, attendees);
        return event.getInfo();
    }

    public static Map<String, Object> updateEvent(String eventId, String ownerId, String title, Long startTime,
                                                  Long endTime, String description, boolean isPublic,
                                                  Map<String, Object> location, List<String> attendees) {
        return new Event(eventId).update(ownerId, title, startTime, endTime, description, isPublic, location, attendees);
    }

    public static Map<String, Object> getInfoByEventId(String eventId) {
        return new Event(eventId).getInfo();
    }

    public static List<Map<String, Object>> getEventsByUserId(String userId, String status) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        ScanSpec scan = new ScanSpec().withFilterExpression("recipientId=:v_recipientId AND status=:v_status")
                                            .withValueMap(new ValueMap().withString(":v_recipientId", userId)
                                                                        .withString(":v_status", status));
        Iterator<Item> items = EVENT_SHARING_TABLE.scan(scan).iterator();
        while (items.hasNext()) {
            result.add(Event.getInfoByEventId(items.next().getString("eventId")));
        }
        return result;
    }

    public static class Location {
        double lat;
        double lng;
        String address;
        int postal;
        String state;
        String streetName;
        int streetNumber;

        public Location(Map<String, Object> info, boolean test) {
            if (info != null) {
                System.out.println(info);
                this.lat = ((BigDecimal)info.get("lat")).doubleValue();
                this.lng = ((BigDecimal)info.get("lng")).doubleValue();
                this.address = (String) info.get("address");
                this.postal = ((BigDecimal)info.get("postal")).intValue();
                this.state = (String) info.get("state");
                this.streetName = (String) info.get("streetName");
                this.streetNumber = ((BigDecimal)info.get("streetNumber")).intValue();
                System.out.println(lat + lng + address + postal + state);
            }
        }

        public Location(Map<String, Object> info) {
            if (info != null) {
                System.out.println(info);
                this.lat = (Double) info.get("lat");
                this.lng = (Double) info.get("lng");
                this.address = (String) info.get("address");
                this.postal = (Integer) info.get("postal");
                this.state = (String) info.get("state");
                this.streetName = (String) info.get("streetName");
                this.streetNumber = (Integer) info.get("streetNumber");
                System.out.println(lat + lng + address + postal + state);
            }
        }

        public Location(double lat, double lng, String address, int postal, String state, String streetName, Integer streetNumber) {
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
