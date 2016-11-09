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

    /*
    public static void main(String[] args) {
        Map<String, String> input = new HashMap<String, String>();
        input.put("userId", "acdb4fe0-5945-442c-9252-fdceb9682820");
        input.put("userId_2", "7d9943f4-4326-44a6-9f39-50f890140b26");
        //input.put("username", "myfriend");
        input.put("email", "rettymoo@gmail");

        List<Map<String, String>> res = new FriendHandler().getFriend(input, null);

        for (Map<String, String> map : res) {
            System.out.println(map);
        }

        System.out.println(res.size());

        boolean add = new FriendHandler().acceptFriend(input, null);
        System.out.println(add);

    }
    */

    public FriendHandler() {
        fm = new FriendManager();
    }

    /**
     * Get friend list lambda
     *
     * @param input userId as key in a Map<String, String> indicating the user
     * @param context
     * @return a list of account info who is friend with the userId
     */
    public List<Map<String, String>> getFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        if (userId == null) {
            return new ArrayList<Map<String, String>>();
        }
        System.out.println("(getFriends)" + userId);
        return fm.getFriendList(userId);
    }

    /**
     * Send friend request
     *
     * @param input userId_1, userId_2 or username or email as key in a Map<String, String>
     *              which userId_1 is the user who send the friend request,
     *                  userId_2 or username or email is the user who will receive the friend request
     * @param context
     * @return true if request is sent, false if other user doesn't exist or already friends
     */
    public boolean addFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId_1 = input.get("userId_1");
        String userId_2 = input.get("userId_2");
        String username = input.get("username");
        String email = input.get("email");
        if (userId_1 == null || (userId_2 == null && username == null && email == null)) {
            return false;
        }
        System.out.println("(addFriend)" + userId_1 + ":" + userId_2 + ":" + username + ":" + email);
        return fm.sendFriendRequest(userId_1, userId_2, username, email);
    }

    /**
     * Reject friend request
     *
     * @param input userId_1, userId_2 as key in a Map<String, String>
     *              which userId_1 is the user who received the friend request,
     *                  userId_2 is the user who will be rejected
     * @param context
     * @return true if request is rejected, false if other user doesn't exist or no request between them
     */
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

    /**
     * Reject friend request
     *
     * @param input userId_1, userId_2 as key in a Map<String, String>
     *              which userId_1 is the user who received the friend request,
     *                  userId_2 is the user who will be accepted
     * @param context
     * @return true if request is accepted, false if other user doesn't exist or no request between them
     */
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
