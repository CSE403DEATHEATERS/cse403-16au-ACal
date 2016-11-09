package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.Account;
import model.FriendManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendHandler {
    private FriendManager fm;
    public static void main(String[] args) {
        Map<String, String> input = new HashMap<String, String>();
        input.put("userId_1", "abc");
        input.put("userId_2", "bcd");

    }

    public FriendHandler() {
        fm = new FriendManager();
    }

    public List<Account> getFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        if (userId == null) {
            return new ArrayList<Account>();
        }
        return fm.getFriendList(userId);
    }

    public boolean addFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId_1 = input.get("userId_1");
        String userId_2 = input.get("userId_2");
        if (userId_1 == null || userId_2 == null) {
            return false;
        }
        return fm.sendFriendRequest(userId_1, userId_2);
    }

    public boolean rejectFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId_1 = input.get("userId_1");
        String userId_2 = input.get("userId_2");
        if (userId_1 == null || userId_2 == null) {
            return false;
        }
        return fm.processFriendRequest(userId_1, userId_2, false);
    }

    public boolean acceptFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId_1 = input.get("userId_1");
        String userId_2 = input.get("userId_2");
        if (userId_1 == null || userId_2 == null) {
            return false;
        }
        return fm.processFriendRequest(userId_1, userId_2, true);
    }
}
