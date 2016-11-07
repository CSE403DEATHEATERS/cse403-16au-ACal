package model;


import java.util.Date;

/**
 * Class representing a message in the db
 *
 */
public class Message {

    /**
     * Id of the message
     * (a UUID string)
     */
    private String messageId;

    /**
     * Category of the message
     */
    private String messageCategory;

    /**
     * With which user is this message associated
     * (a UUID string)
     */
    private String createdBy;

    /**
     * Time of creation of message
     * Stored as a Long
     */
    private Long createdAt;

    /**
     * With which event is this message associated
     * (a UUID string)
     */
    private String eventId;

    /**
     * Content of the message
     */
    private String messageContent;

    /////////////////////////////////////////////////////////////////////
    //////                  Getters and setters                     /////
    /////////////////////////////////////////////////////////////////////

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageCategory() {
        return this.messageCategory.toString();
    }

    public void setMessageCategory(MessageCategory messageCategory) {
        if (messageCategory == null) {
            throw new IllegalArgumentException("messageCategory passed in should not be null.");
        }

        this.messageCategory = messageCategory.toString();
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedAt() {
        return new Long(this.createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("createdAt passed in should not be null.");
        }

        this.createdAt = new Long(createdAt.getTime());
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

}

