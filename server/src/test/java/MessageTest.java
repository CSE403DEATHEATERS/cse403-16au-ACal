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

    private final String MESSAGE_ID = "This is a message id";
    private final MessageCategory MESSAGE_CATEGORY = MessageCategory.ACTUAL_MESSAGE;
    private final String CREATED_BY = "A user name";
    private final Date CREATED_AT = new Date();
    private final String EVENT_ID = "This is an event id";
    private final String MESSAGE_CONTENT = "This is a message content";

    /////////////////////////////////////////////////////////////////////
    //////                    getMessageId()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getMessageIdTest() {
        Message message = new Message();
        message.setMessageId(MESSAGE_ID);

        assertTrue(message.getMessageId().equals(MESSAGE_ID));
    }

    /////////////////////////////////////////////////////////////////////
    //////                    setMessageId()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setMessageIdTest_nullMessageId() {
        Message message = new Message();
        message.setMessageId(null);
    }

    @Test
    public void setMessageIdTest() {
        Message message = new Message();
        message.setMessageId(MESSAGE_ID);

        assertTrue(message.getMessageId().equals(MESSAGE_ID));
    }

    /////////////////////////////////////////////////////////////////////
    //////                 getMessageCategory()                     /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void getMessageCategoryTest_nullMessageCategory() {
        Message message = new Message();
        message.setMessageCategory(null);
    }

    @Test
    public void getMessageCategoryTest_generalCase() {
        Message message = new Message();
        message.setMessageCategory(MESSAGE_CATEGORY);

        assertTrue(MessageCategory.valueOf(message.getMessageCategory()).equals(MESSAGE_CATEGORY));
    }

    /////////////////////////////////////////////////////////////////////
    //////                 setMessageCategory()                     /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setMessageCategoryTest_nullMessageCategory() {
        Message message = new Message();
        message.setMessageCategory(null);
    }

    @Test
    public void setMessageCategoryTest_generalCase() {
        Message message = new Message();
        message.setMessageCategory(MESSAGE_CATEGORY);

        assertTrue(MessageCategory.valueOf(message.getMessageCategory()).equals(MESSAGE_CATEGORY));
    }

    /////////////////////////////////////////////////////////////////////
    //////                    getCreatedBy()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getCreatedBy() {
        Message message = new Message();
        message.setCreatedBy(CREATED_BY);

        assertTrue(message.getCreatedBy().equals(CREATED_BY));
    }

    /////////////////////////////////////////////////////////////////////
    //////                    setCreatedBy()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setCreatedBy_nullCreatedBy() {
        Message message = new Message();
        message.setCreatedBy(null);
    }

    @Test
    public void setCreatedBy_generalCase() {
        Message message = new Message();
        message.setCreatedBy(CREATED_BY);

        assertTrue(message.getCreatedBy().equals(CREATED_BY));
    }

    /////////////////////////////////////////////////////////////////////
    //////                    getCreatedAt()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getCreatedAt() {
        Message message = new Message();
        message.setCreatedAt(CREATED_AT);

        assertTrue(message.getCreatedAt().equals(CREATED_AT.getTime()));
    }

    /////////////////////////////////////////////////////////////////////
    //////                    setCreatedAt()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setCreatedAt_nullCreatedAt() {
        Message message = new Message();
        message.setCreatedAt(null);
    }

    @Test
    public void setCreatedAt_generalCase() {
        Message message = new Message();
        message.setCreatedAt(CREATED_AT);

        assertTrue(message.getCreatedAt().equals(CREATED_AT.getTime()));
    }

    /////////////////////////////////////////////////////////////////////
    //////                     getEventId()                         /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getEventIdTest() {
        Message message = new Message();
        message.setEventId(EVENT_ID);

        assertTrue(message.getEventId().equals(EVENT_ID));
    }

    /////////////////////////////////////////////////////////////////////
    //////                     setEventId()                         /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setEventIdTest_nullEventId() {
        Message message = new Message();
        message.setEventId(null);
    }

    @Test
    public void setEventIdTest_generalCase() {
        Message message = new Message();
        message.setEventId(EVENT_ID);

        assertTrue(message.getEventId().equals(EVENT_ID));
    }

    /////////////////////////////////////////////////////////////////////
    //////                  getMessageContent()                     /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getMessageContentTest() {
        Message message = new Message();
        message.setMessageContent(MESSAGE_CONTENT);

        assertTrue(message.getMessageContent().equals(MESSAGE_CONTENT));
    }

    /////////////////////////////////////////////////////////////////////
    //////                 setMessageContent()                      /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setMessageContentTest_nullMessageContent() {
        Message message = new Message();
        message.setMessageContent(null);
    }

    @Test
    public void setMessageContentTest_generalCase() {
        Message message = new Message();
        message.setMessageContent(MESSAGE_CONTENT);

        assertTrue(message.getMessageContent().equals(MESSAGE_CONTENT));
    }

}
