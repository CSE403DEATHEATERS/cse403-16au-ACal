

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import model.CreateMessageRequest;
import model.GetMessagesRequest;
import model.MessageDBHelper;

/**
 * Test class for MessageDBHelper
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MessageDBHelperTest {

    MessageDBHelper messageDBHelper;

    @Mock
    MessageDBHelper mockMessageDBHelper;

    @Mock
    AmazonDynamoDBClient mockAmazonDynamoDBClient;

    @Mock
    DynamoDB mockDynamoDB;

    @Mock
    Table mockTable;

    @Mock
    PutItemSpec mockPutItemSpec;

    private final String MESSAGE_CONTENT = "This is a message content.";
    private final String EVENT_ID = "This is an event id.";
    private final String USER_ID = UUID.randomUUID().toString();
    private final String DYNAMODB_ENDPOINT = "https://dynamodb.us-west-2.amazonaws.com";
    private final String MESSAGES_TABLE = "acalendar-mobilehub-1275254137-message";
    private final String MESSAGE_ATTRIBUTES_TABLE = "acalendar-mobilehub-1275254137-message_attributes";

    @Before
    public void setup() {
        this.messageDBHelper = new MessageDBHelper();
    }

    /////////////////////////////////////////////////////////////////////
    //////                    createMessage()                       /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullRequest() {
        this.messageDBHelper.createMessage(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullEventId() {
        CreateMessageRequest request = new CreateMessageRequest(null, "userId", "content");
        this.messageDBHelper.createMessage(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullMessageContent() {
        CreateMessageRequest request = new CreateMessageRequest("eventId", "userId", null);
        this.messageDBHelper.createMessage(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullUserId() {
        CreateMessageRequest request = new CreateMessageRequest("eventId", null, "content");
        this.messageDBHelper.createMessage(request);
    }

    /////////////////////////////////////////////////////////////////////
    //////                     getMessages()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void getMessagesTest_nullRequest() {
        this.messageDBHelper.getMessages(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMessagesTest_nullEventId() {
        GetMessagesRequest request = new GetMessagesRequest(null);
        this.messageDBHelper.getMessages(request);
    }

}
