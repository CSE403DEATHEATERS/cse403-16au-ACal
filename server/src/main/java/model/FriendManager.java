package model;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.BatchGetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static dbManager.DynamoDBManager.dynamoDB;

public class FriendManager {
    public static final String TABLE_NAME = "acalendar-mobilehub-1275254137-relationship";
    public static final Table TABLE = dynamoDB.getTable(TABLE_NAME);

    public List<Account> getFriendList(String userId) {
        List<Account> result = new ArrayList<Account>();
        QuerySpec query = new QuerySpec().withHashKey("userId_1", userId);
        ItemCollection<QueryOutcome> items = TABLE.query(query);
        Iterator<Item> itemIterator = items.iterator();
        BatchGetItemSpec batch = new BatchGetItemSpec();
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            batch.withTableKeyAndAttributes(new TableKeysAndAttributes(TABLE_NAME)
                    .withPrimaryKeys(new PrimaryKey("userId_1",
                            item.getString("userId_2"), "userId_2", item.getString("userId_1"))));
        }

        return result;
    }

    public boolean sendFriendRequest(String userId_1, String userId_2) {
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
