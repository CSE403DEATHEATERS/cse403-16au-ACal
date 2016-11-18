package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.Event;
import model.EventSharingService;

import java.util.List;
import java.util.Map;

/**
 * Created by rettymoo on 28/10/2016.
 */
public class EventSharingHandler {
    public List<String> editAttendees(Map<String, Object> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String eventId = (String) input.get("eventId");
        String userId = (String) input.get("userId");
        List<String> removed = (List<String>) input.get("removed");
        List<String> invited = (List<String>) input.get("invited");

        return EventSharingService.editEventAttendees(eventId, userId, invited, removed);
    }

    public boolean acceptEventInvitation(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        String eventId = input.get("eventId");

        return EventSharingService.handleEventInvitation(userId, eventId, true);
    }

    public boolean declineEventInvitation(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        String eventId = input.get("eventId");

        return EventSharingService.handleEventInvitation(userId, eventId, false);
    }

    public List<Map<String, Object>> getPendingEventsByUserId(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        return Event.getEventsByUserId(userId, "PENDING");
    }
}
