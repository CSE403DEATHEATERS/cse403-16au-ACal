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

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

}
