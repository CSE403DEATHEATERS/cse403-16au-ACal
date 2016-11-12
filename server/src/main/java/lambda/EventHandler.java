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
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("ownerId", "123");
        input.put("createTime", new Long(1478914319677L));
        input.put("isPublic", new Boolean(false));
        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("hahah");
        input.put("attendees", list);
        new EventHandler().createEvent(input, null);
    }

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

    public List<Map<String, Object>> getEvent(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }

        return null;
    }
}
