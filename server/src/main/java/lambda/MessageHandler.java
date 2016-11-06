package lambda;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;

import model.CreateMessageRequest;
import model.CreateMessageResponse;
import model.GetMessagesRequest;
import model.GetMessagesResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageHandler {

    public CreateMessageResponse createMessage(CreateMessageRequest request, Context context) {


        return null;
    }

    public GetMessagesResponse getMessages(GetMessagesRequest request, Context context) {

        return null;
    }

    /**
     * Keep this main here for easy testing.
     * Remove before shipping.
     *
     * @param args
     */
    public static void main(String[] args) {
        BasicAWSCredentials b = new BasicAWSCredentials("AKIAJUJRA7EPIJNW4QNQ", "F90UEQ3dj0KYTOBqfSJqm/pi8xdHrG0rQe5rkAmM");
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(b).withEndpoint("https://dynamodb.us-west-2.amazonaws.com");
        DynamoDB dynamoDB = new DynamoDB(client);
        Table table = dynamoDB.getTable("acalendar-mobilehub-1275254137-messages");

        PutItemSpec putItemSpec = new PutItemSpec();
        Item item = new Item();
        item.with("id", UUID.randomUUID().toString());
        item.with("createdAt", 333);
        item.with("createBy", "a user id");
        item.with("eventId", "an event id");
        item.with("messageCategory", "a category");
        putItemSpec.withItem(item);
        table.putItem(putItemSpec);

        System.out.println("Got to here???");
    }

}
