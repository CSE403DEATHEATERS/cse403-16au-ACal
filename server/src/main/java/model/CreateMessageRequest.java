package model;

/**
 * Request object used to create a message
 * 
 * */
public class CreateMessageRequest {

    /**
     * Category of the message.
     * */
    private MessageCategory messageCategory;

    /**
     * The id of the event that is associated with this message.
     * (a UUID string)
     * */
    private String eventId;

    /**
     * The id of the user that created this message
     */
    private String userId;

    /**
     * The content of the message
     */
    private String messageContent;
    
    public CreateMessageRequest (String eventId, String userId, String messageContent) {
    	this.eventId = eventId;
    	this.messageContent = messageContent;
    	this.userId = userId;
    }

    /////////////////////////////////////////////////////////////////////
    //////                  Getters and setters                     /////
    /////////////////////////////////////////////////////////////////////

    /**
     * Return the message category of this CreateMessageRequest
     *
     * @return the message category of this CreateMessageRequest
     * */
    public MessageCategory getMessageCategory() {
        return this.messageCategory;
    }

    /**
     * Get the event id
     *
     * @return event id
     * */
    public String getEventId() {
        return this.eventId;
    }


    public String getUserId() {
        return this.userId;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

}


