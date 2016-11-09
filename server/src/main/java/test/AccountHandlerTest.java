package test;

import lambda.AccountHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AccountHandlerTest {
    AccountHandler ah;

    @Before
    public void setup() {
        ah = new AccountHandler();
    }

    @Test(expected = IllegalArgumentException.class)
    public void accountHandler_nullRequest() {
        this.ah.login(null, null);
    }

    @Test
    public void accountHandler_insufficientParameter() {
        Map<String, String> input = new HashMap<String, String>();
        assertTrue(this.ah.login(input, null).isEmpty());
        input.put("username", "log me in");
        assertTrue(this.ah.login(input, null).isEmpty());
        input.put("pass the password", "log me in");
        assertTrue(this.ah.login(input, null).isEmpty());
    }

}
