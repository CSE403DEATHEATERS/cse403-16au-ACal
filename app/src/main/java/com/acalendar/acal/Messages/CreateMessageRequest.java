package com.acalendar.acal.Messages;

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
     * */
    private int eventId;

    /////////////////////////////////////////////////////////////////////
    //////                  Getters and setters                     /////
    /////////////////////////////////////////////////////////////////////

    public MessageCategory getMessageCategory() {
        return this.messageCategory;
    }

    public void setMessageCategory(MessageCategory messageCategory) {
        this.messageCategory = messageCategory;
    }

    public int getEventId() {
        return this.eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

}


