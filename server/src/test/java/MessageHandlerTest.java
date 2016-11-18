import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import lambda.MessageHandler;
import model.CreateMessageRequest;
import model.GetMessagesRequest;
import model.MessageCategory;
import model.MessageDBHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

/**
 * Test class for MessageHandler.
 */
public class MessageHandlerTest {

    MessageHandler messageHandler;

    @Mock
    MessageHandler mockMessageHandler;
    

    private final String MESSAGE_CONTENT = "This is a message content.";
    private final String EVENT_ID = "This is an event id.";
    private final MessageCategory MESSAGE_CATEGORY = MessageCategory.ACTUAL_MESSAGE;
    private final String USER_ID = UUID.randomUUID().toString();
    private final String DYNAMODB_ENDPOINT = "https://dynamodb.us-west-2.amazonaws.com";
    private final String MESSAGES_TABLE = "acalendar-mobilehub-1275254137-messages";
    private final String MESSAGE_ATTRIBUTES_TABLE = "acalendar-mobilehub-1275254137-message_attributes";

    @Before
    public void setup() {
        this.messageHandler = new MessageHandler();
    }

    /////////////////////////////////////////////////////////////////////
    //////                    createMessage()                       /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullEventId() {
        CreateMessageRequest request = new CreateMessageRequest();
        request.setMessageContent(MESSAGE_CONTENT);
        request.setMessageCategory(MESSAGE_CATEGORY);
        request.setUserId(USER_ID);
        this.messageHandler.createMessage(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullMessageContent() {
        CreateMessageRequest request = new CreateMessageRequest();
        request.setEventId(EVENT_ID);
        request.setMessageContent(null);
        request.setMessageCategory(MESSAGE_CATEGORY);
        request.setUserId(USER_ID);
        this.messageHandler.createMessage(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullMessageCategory() {
        CreateMessageRequest request = new CreateMessageRequest();
        request.setEventId(EVENT_ID);
        request.setMessageContent(MESSAGE_CONTENT);
        request.setMessageCategory(null);
        request.setUserId(USER_ID);
        this.messageHandler.createMessage(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullUserId() {
        CreateMessageRequest request = new CreateMessageRequest();
        request.setEventId(EVENT_ID);
        request.setMessageContent(MESSAGE_CONTENT);
        request.setMessageCategory(MESSAGE_CATEGORY);
        request.setUserId(null);
        this.messageHandler.createMessage(request);
    }

    /////////////////////////////////////////////////////////////////////
    //////                     getMessages()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void getMessagesTest_nullRequest() {
        this.messageHandler.getMessages(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMessagesTest_nullEventId() {
        GetMessagesRequest request = new GetMessagesRequest();
        request.setHowMany(10);
        request.setOffset(0);
        request.setEventId(null);
        this.messageHandler.getMessages(request);
    }

}
