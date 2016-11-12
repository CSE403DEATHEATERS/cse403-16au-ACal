package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.Event;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class EventHandler {
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
        if ((ownerId == null || ownerId.isEmpty()) || createTime == null ||
                isPublic == null) {
            return false;
        }
        System.out.println("(createEvent) " + ownerId + ": " + createTime + ":" + isPublic);
        return Event.createEvent(ownerId, createTime, startTime, endTime, title, description, location, isPublic, attendees);
    }
}
