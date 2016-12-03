package model;

/**
 * Request object used to create a message
 * 
 * */
public class CreateMessageRequest {

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


