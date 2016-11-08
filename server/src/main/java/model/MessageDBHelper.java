package model;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.math.BigDecimal;
import java.util.*;

/**
 * Helper class for storing and retrieving Message's from db.
 */
public class MessageDBHelper {

    private final String DYNAMODB_ENDPOINT = "https://dynamodb.us-west-2.amazonaws.com";
    private final String MESSAGES_TABLE = "acalendar-mobilehub-1275254137-messages";
    private final String MESSAGE_ATTRIBUTES_TABLE = "acalendar-mobilehub-1275254137-message_attributes";

    /**
     * Create a message and store it in our db
     *
     * @param request the request obejct used to construct the new record(s) in table(s)
     * @return a CreateMessageResponse containing the id of the record in the db
     */
    public CreateMessageResponse createMessage(CreateMessageRequest request) {
        validateCreateMessageRequest(request);

        AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint(DYNAMODB_ENDPOINT);
        DynamoDB dynamoDB = new DynamoDB(client);
        Table messagesTable = dynamoDB.getTable(MESSAGES_TABLE);
        Table messageAttributesTable = dynamoDB.getTable(MESSAGE_ATTRIBUTES_TABLE);

        PutItemSpec messagesTableputItemSpec = new PutItemSpec();
        Item item = new Item();
        UUID randomUUID = UUID.randomUUID();
        item.with("id", randomUUID.toString());
        Date currTime = new Date();
        item.with("createdAt", currTime.getTime());
        item.with("createdBy", request.getUserId());
        item.with("eventId", request.getEventId());
        item.with("messageCategory", request.getMessageCategory().toString());
        messagesTableputItemSpec.withItem(item);
        messagesTable.putItem(messagesTableputItemSpec);

        PutItemSpec messageAttributesTableputItemSpec = new PutItemSpec();
        Item item2 = new Item();
        item2.with("messageId", randomUUID.toString());
        item2.with("attributeType", MessageAttributeType.MESSAGE_CONTENT.toString());
        item2.with("attributeValue", request.getMessageContent());
        messageAttributesTableputItemSpec.withItem(item2);
        messageAttributesTable.putItem(messageAttributesTableputItemSpec);

        CreateMessageResponse response = new CreateMessageResponse();
        response.setMessageId(randomUUID.toString());
        return response;
    }

    /**
     * Retrieve messages associated with a specific event from db
     *
     * @param request an request object containing criteria used to retrieve records from db
     * @return a GetMessagesResponse containing a list of Messages matching the criteria
     *         specified by the request.
     */
    public GetMessagesResponse getMessages(GetMessagesRequest request) {
        validateGetMessagesRequest(request);

        AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint(DYNAMODB_ENDPOINT);
        DynamoDB dynamoDB = new DynamoDB(client);
        Table messagesTable = dynamoDB.getTable(MESSAGES_TABLE);
        Table messageAttributesTable = dynamoDB.getTable(MESSAGE_ATTRIBUTES_TABLE);

        List<Message> messages = new ArrayList<Message>();
        Index eventIdIndex = messagesTable.getIndex("eventIdIndex");
        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("#s = :v_eventId")
                .withNameMap(new NameMap()
                        .with("#s", "eventId"))
                .withValueMap(new ValueMap()
                        .withString(":v_eventId", "an event id"));

        ItemCollection<QueryOutcome> items = eventIdIndex.query(spec);
        Iterator<Item> messagesTableIter = items.iterator();
        while (messagesTableIter.hasNext()) {
            Item messagesTableRow = messagesTableIter.next();
            String messageId = (String) messagesTableRow.get("id");

            // For each messageId, retrieve the corresponding records
            // from message_attributes table.
            PrimaryKey primaryKey = new PrimaryKey();
            primaryKey.addComponent("messageId", messageId);
            primaryKey.addComponent("attributeType", MessageAttributeType.MESSAGE_CONTENT.toString());
            GetItemSpec getItemSpec = new GetItemSpec().withPrimaryKey(primaryKey);
            Item messageAttributesRow = messageAttributesTable.getItem(getItemSpec);

            Message message = new Message();
            message.setMessageId(messageId);
            Date createdAt = new Date(((BigDecimal) messagesTableRow.get("createdAt")).longValue());
            message.setCreatedAt(createdAt);
            message.setCreatedBy((String) messagesTableRow.get("createdBy"));
            message.setEventId((String) messagesTableRow.get("eventId"));
            message.setMessageCategory(MessageCategory.valueOf((String) messagesTableRow.get("messageCategory")));
            message.setMessageContent((String) messageAttributesRow.get("attributeValue"));

            messages.add(message);
        }

        GetMessagesResponse response = new GetMessagesResponse();
        response.setMessages(messages);

        return response;
    }

    private void validateCreateMessageRequest(CreateMessageRequest request) {
        if ((request == null) || (request.getEventId() == null) || (request.getMessageCategory() == null)
                || (request.getMessageContent() == null) || (request.getUserId() == null)) {
            throw new IllegalArgumentException("Request passed in and its fields should not be null!");
        }
    }

    private void validateGetMessagesRequest(GetMessagesRequest request) {
        if ((request == null) || (request.getEventId() == null)) {
            throw new IllegalArgumentException("Request passed in and its fields should not be null!");
        }
    }

    /**
     * Keep this main here for easy testing.
     * Remove before shipping.
     *
     * @param args
     */
    public static void main(String[] args) {

    }

}
