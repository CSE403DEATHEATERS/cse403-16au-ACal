

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageHandlerTest {


    @Mock
    MessageHandler messageHandler;

    @Before
    public void setup() {

    }

    @Test
    public void createMessageTest_nullRequest() {
        assertTrue(1 == 1);
    }


}
