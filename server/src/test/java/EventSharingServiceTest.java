import model.EventSharingService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test cases for EventSharingService.java
 */
public class EventSharingServiceTest {

    private final String eventId = "This is an eventId";
    private final String userId = "This is a userId";
    private List<String> invited;
    private List<String> removed;

    @Before
    public void setup() {
        invited = new ArrayList<>();
        removed = new ArrayList<>();
    }

    /////////////////////////////////////////////////////////////////////
    //////                    editAttendees()                       /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void TestEditAttendees_nullEventId() {
        EventSharingService.editEventAttendees(null, userId, invited, removed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestEditAttendees_nullUserId() {
        EventSharingService.editEventAttendees(eventId, null, invited, removed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestEditAttendees_nullLists() {
        EventSharingService.editEventAttendees(eventId, userId, null, null);
    }

    /////////////////////////////////////////////////////////////////////
    //////                handleEventInvitation()                   /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void testHandleEventInvitation_nullEventId() {
        EventSharingService.handleEventInvitation(null, userId, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandleEventInvitation_nullUserId() {
        EventSharingService.handleEventInvitation(eventId, null, true);
    }
}
