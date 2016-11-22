package model;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.BatchGetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.sun.org.apache.bcel.internal.classfile.ExceptionTable;

import javax.mail.internet.InternetAddress;
import java.util.*;

import static dbManager.DynamoDBManager.dynamoDB;
import static model.Account.getInfoByUserId;

public class FriendManager {
    public static final String TABLE_NAME = "acalendar-mobilehub-1275254137-relationship";
    public static final Table TABLE = dynamoDB.getTable(TABLE_NAME);

    /**
     * Get friend list of a user by userId
     *
     * @param userId userId of the user
     * @return list of account info which is the user's friend, empty list if no friend
     */
    public static Map<String, Object> getFriendList(String userId, String status) {
        Map<String, Object> result = new HashMap<String, Object>();
        QuerySpec query = new QuerySpec().withHashKey("userId_1", userId);
        if (status != null && !status.isEmpty())
            query.withFilterExpression("requestStatus=:v_requestStatus")
                    .withValueMap(new ValueMap().withString(":v_requestStatus", status));
        ItemCollection<QueryOutcome> items = TABLE.query(query);
        Iterator<Item> itemIterator = items.iterator();
        if (!itemIterator.hasNext()) {
            System.out.println("no friends.");
            return result;
        }
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            System.out.println(item.getString("userId_2"));
            if (!result.containsKey(item.getString("requestStatus"))) {
                result.put(item.getString("requestStatus"), new ArrayList<Map<String, Object>>());
            }
            Map<String, Object> friend = new Account(item.getString("userId_2")).isLogin();
            if (!friend.isEmpty()) {
                ((List<Map<String, Object>>)result.get(item.getString("requestStatus"))).add(friend);
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
    public static boolean sendFriendRequest(String userId_1, String userId_2, String username, String email) {
        if (userId_2 == null || userId_2.isEmpty()) {
            userId_2 = Account.getUserIdByUsernameOrEmail(username, email);
        }
        if (userId_2.isEmpty()) {
            return false;
        }
        GetItemSpec getItem = new GetItemSpec().withPrimaryKey(new PrimaryKey("userId_1", userId_1, "userId_2", userId_2));
        Item item = TABLE.getItem(getItem);
        System.out.println(item);
        if (item == null) {
            item = new Item().withPrimaryKey(new PrimaryKey("userId_1", userId_1, "userId_2", userId_2))
                        .withString("requestStatus", "SENT");
            TABLE.putItem(item);
            item = new Item().withPrimaryKey(new PrimaryKey("userId_1", userId_2, "userId_2", userId_1))
                        .withString("requestStatus", "PENDING");
            TABLE.putItem(item);

            try {
                Map<String, Object> account_1 = Account.getInfoByUserId(userId_1);
                Map<String, Object> account_2 = Account.getInfoByUserId(userId_2);
                javax.mail.Address senderEmail = new InternetAddress("acal.techteam@gmail.com");
                javax.mail.Address[] recipientEmails = new InternetAddress[1];
                recipientEmails[0] = new InternetAddress((String)account_2.get("email"));
                String emailContent = "Yo! Dear " + account_2.get("firstname") + ",<br><br>" +
                        "You have a new CalPal request from " + account_1.get("username") +
                        "<br><br>Best Wishes<br>" +
                        "ACalendar Tech Team";
                Email confirmationEmail = new Email("Sign Up Confirmation", "Welcome to ACalendar!", emailContent, senderEmail, recipientEmails);
                EmailManager emailManager = new EmailManager();
                emailManager.send(confirmationEmail);
            } catch (Exception e) {
                System.out.println("email not sent.");
            }
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
    public static boolean processFriendRequest(String userId_1, String userId_2, boolean accept) {
        GetItemSpec getItem = new GetItemSpec().withPrimaryKey(new PrimaryKey("userId_1", userId_1, "userId_2", userId_2));
        Item item = TABLE.getItem(getItem);
        if (item == null || !item.getString("requestStatus").equals("PENDING")) {
            return false;
        } else {
            if (accept) {
                UpdateItemSpec update = new UpdateItemSpec().withPrimaryKey(new PrimaryKey("userId_1", userId_1, "userId_2", userId_2))
                                                .addAttributeUpdate(new AttributeUpdate("requestStatus").put("ACCEPT"));
                TABLE.updateItem(update);
                update = new UpdateItemSpec().withPrimaryKey(new PrimaryKey("userId_1", userId_2, "userId_2", userId_1))
                        .addAttributeUpdate(new AttributeUpdate("requestStatus").put("ACCEPT"));
                TABLE.updateItem(update);
            } else {
                TABLE.deleteItem(new PrimaryKey("userId_1", userId_2, "userId_2", userId_1));
                TABLE.deleteItem(new PrimaryKey("userId_1", userId_1, "userId_2", userId_2));
            }
            return true;
        }
    }
}
