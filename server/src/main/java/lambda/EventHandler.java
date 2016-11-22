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

    public static void main(String[] args) {
        Map<String, String> input = new HashMap<>();
        input.put("eventId", "3cd8d3ce-73db-4997-9caf-b1f3b30f5e05");
        //input.put("isPublic", new Boolean(true));
        input.put("title", "test");
        List<String> list = new ArrayList<String>();
        Map<String, Object> location = new HashMap<>();
        location.put("lat", 0.01);
        location.put("lng", 0.01);
        location.put("postal", 98100);
        location.put("address", "123");
        location.put("state", "WA");
        location.put("streetName", "-901");
        location.put("streetNumber", -901);
        //input.put("location", location);
        //list.add("abc");
        //list.add("hahah");
        //new EventHandler().createEvent(input, null);
        Map<String, String> input2 = new HashMap<>();
        input2.put("eventId", "3f19c206-357c-4856-a193-056c70f8aeee");
        input2.put("userId", "7d9943f4-4326-44a6-9f39-50f890140b26");
        System.out.println(new EventHandler().deleteEvent(input, null));
        // System.out.println(new EventHandler().getAttendingEvents(input2, null));
    }



    /**
     * Create event lambda. ownerId, startTime, endTime, isPublic are required for creating an new event.
     * @param input event object fields as keys in a Map<String, Object>.
     * @param context
     * @return a Map stores event info if the event is created, or empty map
     */
    public Map<String, Object> createEvent(Map<String, Object> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String ownerId = (String)input.get("ownerId");
        String title = (String)input.get("title");
        Long startTime = (Long)input.get("startTime");
        Long endTime = (Long)input.get("endTime");
        String description = (String)input.get("description");
        Boolean isPublic = (Boolean)input.get("isPublic");
        Map<String, Object> location = (Map<String, Object>)input.get("location");
        List<String> attendees = (List<String>)input.get("attendees");
        if ((ownerId == null || ownerId.isEmpty()) || isPublic == null || startTime == null || endTime == null) {
            return new HashMap<String, Object>();
        }
        System.out.println("(createEvent) " + ownerId + ":" + ":" + startTime + ":" + endTime + ":" + isPublic);
        return Event.createEvent(ownerId, startTime, endTime, title, description, location, isPublic, attendees);
    }

    /**
     * Edit event lambda. eventId is required to update event info, eventId and createTime cannot be updated
     * @param input event updated fieilds as keys in a Map<String, Object>
     * @return a Map stores event info if the event is updated, or empty map
     */
    public Map<String, Object> editEvent(Map<String, Object> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String eventId = (String)input.get("eventId");
        String ownerId = (String)input.get("ownerId");
        String title = (String)input.get("title");
        Long startTime = (Long)input.get("startTime");
        Long endTime = (Long)input.get("endTime");
        String description = (String)input.get("description");
        Boolean isPublic = (Boolean)input.get("isPublic");
        Map<String, Object> location = (Map<String, Object>)input.get("location");
        List<String> attendees = (List<String>)input.get("attendees");
        List<String> delete = (List<String>)input.get("delete");
        if (eventId == null || eventId.isEmpty()) {
            return new HashMap<String, Object>();
        }
        System.out.println("(editEvent) " + eventId);
        return Event.updateEvent(eventId, ownerId, title, startTime, endTime, description, isPublic, location, attendees, delete);
    }

    /**
     * Delete event lambda. eventId is required to delete event
     * @param input eventId as keys in a Map<String, String>
     * @return a Map stores result of deleting an Event
     */
    public Map<String, Object> deleteEvent(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String eventId = (String)input.get("eventId");
        if (eventId == null || eventId.isEmpty()) {
            return new HashMap<String, Object>();
        }
        System.out.println("(deleteEvent) " + eventId);
        return Event.deleteEvent(eventId);
    }

    /**
     * Get one event lambda. eventId is required for getting an existing event.
     * @param input userId as keys in a Map<String, Object>.
     * @param context
     * @return a Map stores event info if eventId exists, or empty map
     */
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

    /**
     * Get person's events lambda. userId is required for getting user's events.
     * @param input userId and event status as keys in a Map<String, Object>.
     * @param context
     * @return a List of Map stores event info, that the user attends/invited to the event
     */
    public Map<String, Object> getEvents(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        String status = input.get("status");
        if (userId == null || userId.isEmpty()) {
            return new HashMap<String, Object>();
        }
        System.out.println("(getEvents)" + userId);
        return Event.getEventsByUserId(userId, status);
    }
}
