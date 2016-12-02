import model.Message;
import model.MessageCategory;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertTrue;

/**
 * Test class for Message.java
 *
 */
public class MessageTest {

    private final String CREATED_BY = "A user name";
    private final Long CREATED_AT = new Date().getTime();
    private final String EVENT_ID = "This is an event id";
    private final String MESSAGE_CONTENT = "This is a message content";
    private final Message message = new Message(EVENT_ID, CREATED_BY, MESSAGE_CONTENT, CREATED_AT);



    /////////////////////////////////////////////////////////////////////
    //////                    getCreatedBy()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getCreatedBy() {
        assertTrue(message.getCreatedBy().equals(CREATED_BY));
    }


    /////////////////////////////////////////////////////////////////////
    //////                    getCreatedAt()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getCreatedAt() {
        assertTrue(message.getCreatedAt().equals(CREATED_AT));
    }

    /////////////////////////////////////////////////////////////////////
    //////                     getEventId()                         /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getEventIdTest() {
        assertTrue(message.getEventId().equals(EVENT_ID));
    }


    /////////////////////////////////////////////////////////////////////
    //////                  getMessageContent()                     /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getMessageContentTest() {
        assertTrue(message.getMessageContent().equals(MESSAGE_CONTENT));
    }
}
