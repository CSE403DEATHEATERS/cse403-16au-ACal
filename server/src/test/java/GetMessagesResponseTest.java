import model.GetMessagesResponse;
import model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Test class for GetMessagesResponse
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GetMessagesResponseTest {

    /////////////////////////////////////////////////////////////////////
    //////                     setMessages()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void setMessagesTest_nullMessages() {
        GetMessagesResponse response = new GetMessagesResponse();

        response.setMessages(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMessagesTest_nullElement() {
        GetMessagesResponse response = new GetMessagesResponse();
        List<Message> messages = new ArrayList<Message>();
        messages.add(null);

        response.setMessages(messages);
    }

    @Test
    public void setMessagesTest_generalCase() {
        GetMessagesResponse response = new GetMessagesResponse();
        List<Message> messages = new ArrayList<Message>();
        Message message = new Message();
        messages.add(message);

        response.setMessages(messages);

        assertTrue(response.getMessages().equals(messages));
    }

}
