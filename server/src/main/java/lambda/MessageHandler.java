package lambda;

import model.*;

public class MessageHandler {

    /**
     * Create a message and store it in our db
     *
     * @param request the request obejct used to construct the new record(s) in table(s)
     * @return a CreateMessageResponse containing the id of the record in the db
     */
    public CreateMessageResponse createMessage(CreateMessageRequest request) {
        validateCreateMessageRequest(request);

        MessageDBHelper helper = new MessageDBHelper();
        CreateMessageResponse response = helper.createMessage(request);
        return response;
    }

    /**
     * Retrieve messages associated with a specific event from db
     *
     * @param request an request object containing criteria used to retrieve records from db
     * @return a GetMessagesResponse containing a list of Messages matching the criteria
     *         specified by the request.
     */
    public GetMessagesResponse getMessages(GetMessagesRequest request) {
        validateGetMessagesRequest(request);

        MessageDBHelper helper = new MessageDBHelper();
        GetMessagesResponse response = helper.getMessages(request);
        return response;
    }

    private void validateCreateMessageRequest(CreateMessageRequest request) {
        if ((request == null) || (request.getEventId() == null) || (request.getMessageCategory() == null)
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
