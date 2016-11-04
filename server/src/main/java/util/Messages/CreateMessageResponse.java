package util.Messages;

/**
 * Response object used when creatign a message
 *
 * */
public class CreateMessageResponse {

    /**
     * The id the message that was just created.
     * (a UUID string)
     * */
    private String messageId;

    /////////////////////////////////////////////////////////////////////
    //////                  Getters and setters                     /////
    /////////////////////////////////////////////////////////////////////

    /**
     * Return the id of the message created
     *
     * @return the id of the message created
     * */
    public String getMessageId() {
        return this.messageId;
    }

    /**
     * Set the messageId
     *
     * @param messageId
     * */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

}
