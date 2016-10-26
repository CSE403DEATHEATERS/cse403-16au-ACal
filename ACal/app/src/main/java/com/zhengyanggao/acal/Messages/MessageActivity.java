package com.zhengyanggao.acal.Messages;

/**
 * Created by ZhengyangGao on 10/26/16.
 */
public class MessageActivity {

    /**
     * Create a message and store it in our database.
     *
     * @param request the request object used to create a message
     * @throws IllegalArgumentException if request passed in is null
     * @return a CreateMessageResponse containing the id of the message created
     * */
    public CreateMessageResponse createMessage(CreateMessageRequest request) {
        CreateMessageResponse response = new CreateMessageResponse();




        return response;
    }

    /**
     * Get a certain amount of messages from our database.
     *
     * @param request the request object used to create a message
     * @throws IllegalArgumentException if request passed in is null
     * @return a list of Message's that match the criteria described in the request
     * */
    public GetMessagesResponse getMessages(GetMessagesRequest request) {
        GetMessagesResponse response = new GetMessagesResponse();




        return response;
    }
    
}
