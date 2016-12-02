package model;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;

import java.math.BigDecimal;

import static dbManager.DynamoDBManager.dynamoDB;

/**
 * Helper class for storing and retrieving Message's from db.
 */
public class MessageDBHelper {


    private final static String MESSAGES_TABLE = "acalendar-mobilehub-1275254137-message";
    public static Table MESSAGE_TABLE = dynamoDB.getTable(MESSAGES_TABLE);


    /**
     * Create a message and store it in our db
     *
     * @param request the request obejct used to construct the new record(s) in table(s)
     * @return a boolean that tell whether the message has created succeed
     * containing the id of the record in the db
     */
    public Map<String, Object> createMessage(CreateMessageRequest request) {
    	validateCreateMessageRequest(request);
    	try {
	        PutItemSpec messagesTableputItemSpec = new PutItemSpec();
	        Item item = new Item();
	        Date currTime = new Date();
	        item.with("createdAt", currTime.getTime());
	        item.with("createdBy", request.getUserId());
	        item.with("eventId", request.getEventId());
	        item.with("messageContent", request.getMessageContent().toString());
	        messagesTableputItemSpec.withItem(item);
	        MESSAGE_TABLE.putItem(messagesTableputItemSpec);
    		Map<String, Object> res = new HashMap<String, Object>();
    		res.put("result", false);
    		return res;

    	} catch (Exception e) {
    		Map<String, Object> res = new HashMap<String, Object>();
    		res.put("result", false);
    		return res;
    	}
    }

    /**
     * Retrieve messages associated with a specific event from db
     *
     * @param request an request object containing criteria used to retrieve records from db
     * @return a GetMessagesResponse containing a list of Messages matching the criteria
     *         specified by the request.
     */
    public Map<String, Object> getMessages(GetMessagesRequest request) {
    	validateGetMessagesRequest(request);
        ArrayList<Message> messages = new ArrayList<Message>();
        
        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#id", "eventId");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":value", request.getEventId().toString());

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("#id = :value")
                .withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = MESSAGE_TABLE.query(spec);
        Iterator<Item> messagesTableIter = items.iterator();
        while (messagesTableIter.hasNext()) {
            Item messagesTableRow = messagesTableIter.next();

            String createBy = (String) messagesTableRow.get("createdBy");
            String eventId= (String) messagesTableRow.get("eventId");
            String content = (String) messagesTableRow.get("messageContent");
            Date createdAt = new Date(((BigDecimal) messagesTableRow.get("createdAt")).longValue());
            Message message = new Message(eventId, createBy, content, createdAt.getTime());
            messages.add(message);
        }
      
        HashMap<String, Object> res = new HashMap<String, Object>();
        for(int i = 0; i < messages.size(); i++) {
        	HashMap<String, Object> message = new HashMap<String, Object>();
        	message.put("eventId", messages.get(i).getEventId());
        	message.put("content", messages.get(i).getMessageContent());
        	message.put("userId", messages.get(i).getCreatedBy());
        	message.put("createAt", messages.get(i).getCreatedAt());
        	res.put(i + "", message);
        }   
        return res;
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
