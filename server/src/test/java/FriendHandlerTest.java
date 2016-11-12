import com.amazonaws.services.lambda.runtime.Context;
import lambda.FriendHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Test class for FriendHandler.java
 */
public class FriendHandlerTest {

    @Mock
    Context context;

    private FriendHandler fh;

    @Before
    public void setup() {
        this.fh = new FriendHandler();
    }

    /////////////////////////////////////////////////////////////////////
    //////                      getFriend()                         /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void getFriendTest_nullInput() {
        this.fh.getFriend(null, context);
    }

    /////////////////////////////////////////////////////////////////////
    //////                      addFriend()                         /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void addFriendTest_nullInput() {
        this.fh.addFriend(null, context);
    }

    /////////////////////////////////////////////////////////////////////
    //////                    acceptFriend()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void acceptFriend_nullInput() {
        this.fh.acceptFriend(null, context);
    }

}
