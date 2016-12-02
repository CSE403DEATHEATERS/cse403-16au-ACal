import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

import lambda.MessageHandler;
import model.FriendManager;
import model.MessageDBHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

/**
 * Test class for MessageHandler.
 */
public class MessageHandlerTest {

    MessageHandler messageHandler;

    @Mock
    MessageHandler mockMessageHandler;


    private final String MESSAGE_CONTENT = "This is a message content.";
    private final String EVENT_ID = "This is an event id.";
    private final String USER_ID = "Handler test";
    public final BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAIG5ZINASWAB33EXA","OeizWTU2FObibQrywkLbE2BMYidieo4HmIcymcUK" );
    public final AmazonDynamoDBClient CLIENT = new AmazonDynamoDBClient(credentials).withEndpoint("https://dynamodb.us-west-2.amazonaws.com");
    public final DynamoDB dynamoDB = new DynamoDB(CLIENT);

    @Before
    public void setup() {
        this.messageHandler = new MessageHandler();
    }

    /////////////////////////////////////////////////////////////////////
    //////                    createMessage()                       /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullEventId() {
        HashMap<String, Object> request = new HashMap<String, Object>();
        request.put("eventId", null);
        request.put("userId", USER_ID);
        request.put("content", MESSAGE_CONTENT);
        this.messageHandler.createMessage(request, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullMessageContent() {
        HashMap<String, Object> request = new HashMap<String, Object>();
        request.put("eventId", EVENT_ID);
        request.put("userId", USER_ID);
        request.put("content", null);
        this.messageHandler.createMessage(request, null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void createMessageTest_nullUserId() {
        HashMap<String, Object> request = new HashMap<String, Object>();
        request.put("eventId", EVENT_ID);
        request.put("userId", null);
        request.put("content", MESSAGE_CONTENT);
        this.messageHandler.createMessage(request, null);
    }

    /////////////////////////////////////////////////////////////////////
    //////                     getMessages()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = NullPointerException.class)
    public void getMessagesTest_nullRequest() {
        this.messageHandler.getMessages(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMessagesTest_nullEventId() {
        HashMap<String, Object> request = new HashMap<String, Object>();
        request.put("eventId", null);
        this.messageHandler.getMessages(request, null);
    }

    @Test
    public void readWrite () {

        Table table = mock(Table.class);
        MessageDBHelper.MESSAGE_TABLE = table;
        HashMap<String, Object> sentRequest = new HashMap<String, Object>();
        sentRequest.put("eventId", EVENT_ID);
        sentRequest.put("userId", USER_ID);
        sentRequest.put("content", MESSAGE_CONTENT);
        this.messageHandler.createMessage(sentRequest, null);

        HashMap<String, Object> getRequest = new HashMap<String, Object>();
        getRequest.put("eventId", EVENT_ID);
        Map<String, Object> messages = this.messageHandler.getMessages(getRequest, null);

        @SuppressWarnings("unchecked")
        Map<String, Object> lastMessage = (Map<String, Object>) messages.get("" + (messages.size() - 1));

        String content = (String) lastMessage.get("content");
        String id = (String) lastMessage.get("eventId");
        String createBy = (String) lastMessage.get("userId");

        assertTrue(content.equals(MESSAGE_CONTENT));
        assertTrue(createBy.equals(USER_ID));
        assertTrue(id.equals(EVENT_ID));
    }

}
