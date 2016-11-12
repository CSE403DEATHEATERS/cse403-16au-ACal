package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class EventHandler {
    /*
    public static void main(String[] args) {
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("ownerId", "123");
        input.put("createTime", new Long(1478914319677L));
        input.put("isPublic", new Boolean(false));
        List<String> list = new ArrayList<String>();
        Map<String, Object> location = new HashMap<>();
        location.put("lat", 0.01);
        location.put("lng", 0.01);
        location.put("postal", 98105);
        location.put("address", "902 NE 43rd St Apt 214");
        location.put("state", "WA");
        location.put("streetName", "902");
        location.put("streetNumber", 902);
        input.put("location", location);
        list.add("abc");
        list.add("hahah");
        input.put("attendees", list);
        //new EventHandler().createEvent(input, null);
        Map<String, String> input2 = new HashMap<>();
        input2.put("eventId", "fa6f6d78-0795-41d7-994c-56b6f987c0fb");
        input2.put("userId", "abc");
        System.out.println(new EventHandler().getEvent(input2, null));
        System.out.println(new EventHandler().getEvents(input2, null));
    }
    */

    public boolean createEvent(Map<String, Object> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }

        String ownerId = (String)input.get("ownerId");
        String title = (String)input.get("title");
        Long createTime = (Long) input.get("createTime");
        Long startTime = (Long)input.get("startTime");
        Long endTime = (Long)input.get("endTime");
        String description = (String)input.get("description");
        Boolean isPublic = (Boolean)input.get("isPublic");
        Map<String, Object> location = (Map<String, Object>)input.get("location");
        List<String> attendees = (List<String>)input.get("attendees");
        if ((ownerId == null || ownerId.isEmpty()) || createTime == null || isPublic == null) {
            return false;
        }
        System.out.println("(createEvent) " + ownerId + ": " + createTime + ":" + isPublic);
        return Event.createEvent(ownerId, createTime, startTime, endTime, title, description, location, isPublic, attendees);
    }

    public Map<String, Object> getEvent(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String eventId = input.get("eventId");
        if (eventId == null || eventId.isEmpty()) {
            return new HashMap<String, Object>();
        }
        System.out.println("(getEvent) "  + eventId);
        return Event.getInfoByEventId(eventId);
    }

    public List<Map<String, Object>> getEvents(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        if (userId == null || userId.isEmpty()) {
            return new ArrayList<Map<String, Object>>();
        }
        return Event.getEventsByUserId(userId);
    }
}
