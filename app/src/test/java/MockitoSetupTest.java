import android.os.Message;

import com.acalendar.acal.Messages.MessageActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static android.R.id.list;
import static android.os.Build.VERSION_CODES.M;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoSetupTest {

    @Mock
    MessageActivity mockMessageActivity;

    @Test
    public void testMockitoSetup() {
        when(mockMessageActivity.methodForTestingMockitoSetup()).thenReturn("Message created!");
        assert(mockMessageActivity.methodForTestingMockitoSetup().equals("Message created!"));
    }

}
