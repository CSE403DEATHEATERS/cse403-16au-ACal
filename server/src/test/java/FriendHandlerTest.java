import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.lambda.runtime.Context;
import lambda.FriendHandler;
import model.FriendManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for FriendHandler.java
 */
@RunWith(MockitoJUnitRunner.class)
public class FriendHandlerTest {

    @Mock
    Context context;


    private FriendHandler fh;

    @Before
    public void setup() {
        this.fh = new FriendHandler();
    }

    /////////////////////////////////////////////////////////////////////
    //////                      getFriendList()                         /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void getFriendsTest_nullInput() {
        this.fh.getFriends(null, context);
    }

    @Test
    public void getFriendsTest() {
        Table table = mock(Table.class);
        FriendManager.TABLE = table;
        Map<String, String> input = new HashMap<>();
        assertTrue(this.fh.getFriends(input, context).isEmpty());
        input.put("userId", "123");
        when(table.query(any(QuerySpec.class))).thenReturn(new ItemCollection<QueryOutcome>() {
            @Override
            public Integer getMaxResultSize() {
                return null;
            }

            @Override
            public Page<Item, QueryOutcome> firstPage() {
                return null;
            }
        });
        //assertTrue(this.fh.getFriends(input, context).isEmpty());

    }

    /////////////////////////////////////////////////////////////////////
    //////                      addFriend()                         /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void addFriendTest_nullInput() {
        this.fh.addFriend(null, context);
    }

    @Test
    public void addFriendTest() {
        Table table = mock(Table.class);
        FriendManager.TABLE = table;
        Map<String, String> input = new HashMap<>();
        assertTrue(this.fh.addFriend(input, context).isEmpty());
        input.put("userId_1", "123");
        assertTrue(this.fh.addFriend(input, context).isEmpty());
        input.put("userId_2", "1234");
        when(table.getItem(any(GetItemSpec.class))).thenReturn(null);
        assertTrue((boolean)this.fh.addFriend(input, context).get("result"));
        input.put("userId_2", "123");
        assertFalse((boolean)this.fh.addFriend(input, context).get("result"));
    }

    /////////////////////////////////////////////////////////////////////
    //////                    acceptFriend()                        /////
    /////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void acceptFriend_nullInput() {
        this.fh.acceptFriend(null, context);
    }

    @Test
    public void acceptFriend() {
        Table table = mock(Table.class);
        FriendManager.TABLE = table;
        Map<String, String> input = new HashMap<>();
        assertTrue(this.fh.acceptFriend(input, context).isEmpty());
        when(table.getItem(any(GetItemSpec.class))).thenReturn(new Item().withString("requestStatus", "PENDING"));
        input.put("userId_1", "123");
        input.put("userId_2", "1234");
        assertTrue((boolean)this.fh.acceptFriend(input, context).get("result"));
        when(table.getItem(any(GetItemSpec.class))).thenReturn(null);
        assertFalse((boolean)this.fh.acceptFriend(input, context).get("result"));
    }
}
