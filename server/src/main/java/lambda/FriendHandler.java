package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.FriendManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendHandler {

    public static void main(String[] args) {
        Map<String, String> input = new HashMap<String, String>();
        input.put("userId_2", "6b16c70c-9111-434b-b1a2-163f783163f3");
        input.put("userId_1", "7d9943f4-4326-44a6-9f39-50f890140b26");
        input.put("requestStatus", "PENDING");

        //List<Map<String, String>> res = new FriendHandler().getFriend(input, null);

        //for (Map<String, String> map : res) {
        //    System.out.println(map);
        //}

        //System.out.println(res.size());

        System.out.println(new FriendHandler().addFriend(input, null));
    }

    /**
     * Get friend list lambda
     *
     * @param input userId, requestStatus as key in a Map<String, Object> indicating the user and friend request status
     * @param context
     * @return a list of account info who is friend with the userId
     */
    public Map<String, Object> getFriends(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId = input.get("userId");
        String status = input.get("requestStatus");
        if ((userId == null || userId.isEmpty())) {
            return new HashMap<String, Object>();
        }
        System.out.println("(getFriends)" + userId);
        return FriendManager.getFriendList(userId, status);
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
    public Map<String, Object> addFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId_1 = input.get("userId_1");
        String userId_2 = input.get("userId_2");
        String username = input.get("username");
        String email = input.get("email");
        if ((userId_1 == null || userId_1.isEmpty()) || ((userId_2 == null || userId_2.isEmpty()) &&
                (username == null || username.isEmpty()) && (email == null || email.isEmpty()))) {
            return new HashMap<String, Object>();
        }
        System.out.println("(addFriend)" + userId_1 + ":" + userId_2 + ":" + username + ":" + email);
        Map<String, Object> result = new HashMap<>();
        result.put("result", FriendManager.sendFriendRequest(userId_1, userId_2, username, email));
        return result;
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
    public Map<String, Object> rejectFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId_1 = input.get("userId_1");
        String userId_2 = input.get("userId_2");
        if ((userId_1 == null || userId_1.isEmpty()) || (userId_2 == null || userId_2.isEmpty())) {
            return new HashMap<String, Object>();
        }
        System.out.println("(rejectFriend)" + userId_1 + ":" + userId_2);
        Map<String, Object> result = new HashMap<>();
        result.put("result", FriendManager.processFriendRequest(userId_1, userId_2, false));
        return result;
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
    public Map<String, Object> acceptFriend(Map<String, String> input, Context context) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        String userId_1 = input.get("userId_1");
        String userId_2 = input.get("userId_2");
        if ((userId_1 == null || userId_1.isEmpty()) || (userId_2 == null || userId_2.isEmpty())) {
            return new HashMap<String, Object>();
        }
        System.out.println("(acceptFriend)" + userId_1 + ":" + userId_2);
        Map<String, Object> result = new HashMap<>();
        result.put("result", FriendManager.processFriendRequest(userId_1, userId_2, true));
        return result;
    }
}
