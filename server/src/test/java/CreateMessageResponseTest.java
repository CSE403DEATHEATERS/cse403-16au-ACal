import model.CreateMessageResponse;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Test class for CreateMessageResponse
 *
 */
public class CreateMessageResponseTest {

    private final String MESSAGE_ID = "This is a mesage id";

    /////////////////////////////////////////////////////////////////////
    //////                    getMessageId()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test
    public void getMessageIdTest() {
        CreateMessageResponse response = new CreateMessageResponse();
        response.setMessageId(MESSAGE_ID);

        assertTrue(response.getMessageId().equals(MESSAGE_ID));
    }

    /////////////////////////////////////////////////////////////////////
    //////                    setMessageId()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setMessageIdTest_nullMessageId() {
        CreateMessageResponse response = new CreateMessageResponse();
        response.setMessageId(null);
    }

    @Test
    public void setMessageIdTest_generalCase() {
        CreateMessageResponse response = new CreateMessageResponse();
        response.setMessageId(MESSAGE_ID);

        assertTrue(response.getMessageId().equals(MESSAGE_ID));
    }

}
