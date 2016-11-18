package model;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.BatchWriteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static dbManager.DynamoDBManager.dynamoDB;

public class EventSharingService {
    public static final String EVENT_TABLE_NAME = "acalendar-mobilehub-1275254137-eventInfo";
    public static final String EVENT_SHARING_TABLE_NAME = "acalendar-mobilehub-1275254137-eventSharing";
    public static final Table EVENT_TABLE = dynamoDB.getTable(EVENT_TABLE_NAME);
    public static final Table EVENT_SHARING_TABLE = dynamoDB.getTable(EVENT_SHARING_TABLE_NAME);

    public static List<String> editEventAttendees(String eventId, String userId, List<String> invited, List<String> removed) {
        Event event = new Event(eventId);
        Map<String, Object> eventInfo = event.getInfo();
        String eventOwner = (String) eventInfo.get("ownerId");
        // only owner is allowed to remove attendees
        if (!eventOwner.equals(userId) && removed != null && removed.size() > 0) {
            throw new IllegalArgumentException("Not authorized for this action.");
        }
        // only owner is allowed to share his private event
        if (!((boolean) eventInfo.get("isPublic")) && !eventOwner.equals(userId)) {
            throw new IllegalArgumentException("Not authorized for this action.");
        }

        TableWriteItems attendeeList = new TableWriteItems(EVENT_SHARING_TABLE_NAME);
        if (invited != null) {
            for (String attendee : invited) {
                attendeeList.addItemToPut(new Item().withPrimaryKey("eventId", eventId, "recipientId", attendee)
                        .withString("inviteStatus", "PENDING")
                        .withNumber("sentTime", new Date().getTime()));
            }
        }
        if (removed != null) {
            for (String attendee: removed) {
                attendeeList.addHashAndRangePrimaryKeyToDelete("eventId", eventId, "recipientId", attendee);
            }
        }
        BatchWriteItemSpec batch = new BatchWriteItemSpec().withTableWriteItems(attendeeList);
        dynamoDB.batchWriteItem(batch);

        return (List<String>) Event.getInfoByEventId(eventId).get("attendees");
    }

    public static boolean handleEventInvitation(String userId, String eventId, boolean accept) {
        GetItemSpec getItem = new GetItemSpec().withPrimaryKey(new PrimaryKey("eventId", eventId, "recipientId", userId));
        Item item = EVENT_SHARING_TABLE.getItem(getItem);
        if (item == null || item.getString("inviteStatus") == null) {
            return false;
        }
        if (!item.getString("inviteStatus").equals("PENDING")) {
            return false;
        }

        if (accept) {
            item = new Item().withPrimaryKey(new PrimaryKey("eventId", eventId, "recipientId", userId));
            EVENT_SHARING_TABLE.putItem(item);
        } else {
            EVENT_SHARING_TABLE.deleteItem(new PrimaryKey("eventId", eventId, "recipientId", userId));
        }

        return true;
    }

}
