package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.EventSharingService;

import java.util.ArrayList;
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

    public List<String> handleEventInvitation(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        String eventId = input.get("eventId");
        String accept = input.get("accept");

        return new ArrayList<>();
    }

}
