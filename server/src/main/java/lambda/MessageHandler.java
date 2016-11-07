package lambda;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.datapipeline.model.Query;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;

import model.*;

import java.math.BigDecimal;
import java.util.*;

public class MessageHandler {

    public CreateMessageResponse createMessage(CreateMessageRequest request, Context context) {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("https://dynamodb.us-west-2.amazonaws.com");
        DynamoDB dynamoDB = new DynamoDB(client);
        Table messagesTable = dynamoDB.getTable("acalendar-mobilehub-1275254137-messages");
        Table messageAttributesTable = dynamoDB.getTable("acalendar-mobilehub-1275254137-message_attributes");

        PutItemSpec messagesTableputItemSpec = new PutItemSpec();
        Item item = new Item();
        UUID randomUUID = UUID.randomUUID();
        item.with("id", randomUUID.toString());
        Date currTime = new Date();
        item.with("createdAt", currTime.getTime());
        item.with("createdBy", request.getUserId());
        item.with("eventId", request.getEventId());
        item.with("messageCategory", request.getMessageCategory());
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

    public GetMessagesResponse getMessages(GetMessagesRequest request, Context context) {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("https://dynamodb.us-west-2.amazonaws.com");
        DynamoDB dynamoDB = new DynamoDB(client);
        Table messagesTable = dynamoDB.getTable("acalendar-mobilehub-1275254137-messages");
        Table messageAttributesTable = dynamoDB.getTable("acalendar-mobilehub-1275254137-message_attributes");

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
            primaryKey.addComponent("messageId", "10");
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

    /**
     * Keep this main here for easy testing.
     * Remove before shipping.
     *
     * @param args
     */
    public static void main(String[] args) {

    }

}
