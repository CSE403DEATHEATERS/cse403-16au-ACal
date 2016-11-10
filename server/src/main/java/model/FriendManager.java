package model;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.BatchGetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static dbManager.DynamoDBManager.dynamoDB;

public class FriendManager {
    public static final String TABLE_NAME = "acalendar-mobilehub-1275254137-relationship";
    public static final Table TABLE = dynamoDB.getTable(TABLE_NAME);

    /**
     * Get friend list of a user by userId
     *
     * @param userId userId of the user
     * @return list of account info which is the user's friend, empty list if no friend
     */
    public List<Map<String, String>> getFriendList(String userId) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        QuerySpec query = new QuerySpec().withHashKey("userId_1", userId);
        ItemCollection<QueryOutcome> items = TABLE.query(query);
        Iterator<Item> itemIterator = items.iterator();
        if (!itemIterator.hasNext()) {
            System.out.println("no friends.");
            return result;
        }
        BatchGetItemSpec batch = new BatchGetItemSpec();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            System.out.println(item.getString("userId_2"));
            batch.withTableKeyAndAttributes(new TableKeysAndAttributes(TABLE_NAME)
                    .withPrimaryKeys(new PrimaryKey("userId_1",
                            item.getString("userId_2"), "userId_2", item.getString("userId_1"))));
        }
        List<Map<String, AttributeValue>> approvedFriends = dynamoDB.batchGetItem(batch).getBatchGetItemResult()
                .getResponses().get(TABLE_NAME);

        for (Map<String, AttributeValue> friend : approvedFriends) {
            Map<String, String> friendInfo = Account.getInfoByUserId(friend.get("userId_1").getS());
            if (!friendInfo.isEmpty()) {
                result.add(friendInfo);
            }
        }
        return result;
    }

    /**
     * send friend request from userId_1 to userId_2 or user with username or email
     *
     * @param userId_1 userId of the request sender
     * @param userId_2 userId of the request receiver
     * @param username username of the request receiver
     * @param email email of the request receiver
     * @required userId_1 must be in Account table
     * @return true if request is sent, false if request is already sent before or receiver doesn't exist
     */
    public boolean sendFriendRequest(String userId_1, String userId_2, String username, String email) {
        if (userId_2 == null) {
            userId_2 = Account.getUserIdByUsernameOrEmail(username, email);
        }
        if (userId_2.length() == 0) {
            return false;
        }
        GetItemSpec getItem = new GetItemSpec().withPrimaryKey(new PrimaryKey("userId_1", userId_1, "userId_2", userId_2));
        Item item = TABLE.getItem(getItem);
        if (item == null) {
            item = new Item().withPrimaryKey(new PrimaryKey("userId_1", userId_1, "userId_2", userId_2));
            TABLE.putItem(item);
            return true;
        } else {
            return false;
        }
    }

    /**
     * accept or reject request from userId_2 to userId_1
     *
     * @param userId_1 userId of the request receiver
     * @param userId_2 userId of the request sender
     * @param accept true when receiver accepts the request, or false when reject
     * @return true if request is accepted or rejected, false if no request
     */
    public boolean processFriendRequest(String userId_1, String userId_2, boolean accept) {
        GetItemSpec getItem = new GetItemSpec().withPrimaryKey(new PrimaryKey("userId_1", userId_2, "userId_2", userId_1));
        Item item = TABLE.getItem(getItem);
        if (item == null) {
            return false;
        } else {
            if (accept) {
                item = new Item().withPrimaryKey(new PrimaryKey("userId_1", userId_1, "userId_2", userId_2));
                TABLE.putItem(item);
            } else {
                TABLE.deleteItem(new PrimaryKey("userId_1", userId_2, "userId_2", userId_1));
            }
            return true;
        }
    }
}
