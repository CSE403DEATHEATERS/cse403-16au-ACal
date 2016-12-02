package lambda;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;

import model.*;

public class MessageHandler {
	
    public static void main(String[] args) {
    	String eventId = "7e541db9-0690-4638-9881-99e60ec2978f";
    	MessageHandler handler = new MessageHandler();

        HashMap<String, Object> request1 = new HashMap<String, Object>();
        request1.put("eventId", eventId);
        request1.put("userId", "user1");
        request1.put("content", "hello");
        
        HashMap<String, Object> request2 = new HashMap<String, Object>();
        request2.put("eventId", eventId);
        request2.put("userId", "user2");
        request2.put("content", "hi");
        
        HashMap<String, Object> request3 = new HashMap<String, Object>();
        request3.put("eventId", eventId);
        request3.put("userId", "user3");
        request3.put("content", "hum");
        
        HashMap<String, Object> request4 = new HashMap<String, Object>();
        request4.put("eventId", eventId);
        request4.put("userId", "user1");
        request4.put("content", "hum");

        handler.createMessage(request1, null);
        handler.createMessage(request2, null);
        handler.createMessage(request3, null);
        handler.createMessage(request4, null);
        
        
        HashMap<String, Object> get = new HashMap<String, Object>();
        get.put("eventId", eventId);
        Map<String, Object> messages = handler.getMessages(get, null);
        
        for (int i = 0; i < messages.size(); i++) {
            String value = (String) messages.get("" + i);
            String[] token = value.split("\\s+");
            Long createAt = Long.parseLong(token[0]);
            String createBy = token[1];
            String content = token[2];
            System.out.println(createAt + " " + createBy + " " + content); 
        }
    }

    /**
     * Create a message and store it in our db
     *
     * @param request the request obejct used to construct the new record(s) in table(s)
     * @return a CreateMessageResponse containing the id of the record in the db
     */
    public Map<String, Object> createMessage(Map<String, Object> input, Context context) {
    	CreateMessageRequest request = new CreateMessageRequest((String)input.get("eventId"), (String)input.get("userId"), (String) input.get("content"));
        validateCreateMessageRequest(request);

        MessageDBHelper helper = new MessageDBHelper();
        return helper.createMessage(request);
    }

    /**
     * Retrieve messages associated with a specific event from db
     *
     * @param request an GetMessageRequest object containing criteria used to retrieve records from db
     * @return a GetMessagesResponse containing a list of Messages matching the criteria
     *         specified by the request.
     */
    public Map<String, Object> getMessages(Map<String, Object> input, Context context) {
    	GetMessagesRequest request = new GetMessagesRequest((String)input.get("eventId"));
        validateGetMessagesRequest(request);

        MessageDBHelper helper = new MessageDBHelper();
        return helper.getMessages(request);
    }

    private void validateCreateMessageRequest(CreateMessageRequest request) {
        if ((request == null) || (request.getEventId() == null)
                || (request.getMessageContent() == null) || (request.getUserId() == null)) {
            throw new IllegalArgumentException("Request passed in and its fields should not be null!");
        }
    }

    private void validateGetMessagesRequest(GetMessagesRequest request) {
        if ((request == null) || (request.getEventId() == null)) {
            throw new IllegalArgumentException("Request passed in and its fields should not be null!");
        }
    }

}
