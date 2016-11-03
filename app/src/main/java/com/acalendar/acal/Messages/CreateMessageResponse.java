package com.acalendar.acal.Messages;

/**
 * Response object used when creatign a message
 *
 * */
public class CreateMessageResponse {

    /**
     * The id the message that was just created.
     * */
    private int messageId;

    /////////////////////////////////////////////////////////////////////
    //////                  Getters and setters                     /////
    /////////////////////////////////////////////////////////////////////

    /**
     * Return the id of the message created
     *
     * @return the id of the message created
     * */
    public int getMessageId() {
        return this.messageId;
    }

    /**
     * Set the messageId
     *
     * @param messageId
     * */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

}
