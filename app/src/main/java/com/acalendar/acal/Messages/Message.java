package com.acalendar.acal.Messages;

import java.util.Date;

/**
 * Class representing a message in the db
 *
 */
public class Message {

    /**
     * Id of the message
     */
    private int messageId;

    /**
     * Category of the message
     */
    private String messageCategory;

    /**
     * With which user is this message associated
     */
    private int createBy;

    /**
     * Time of creation of message
     */
    private Date createdAt;

    /**
     * With which event is this message associated
     */
    private int eventId;

    /////////////////////////////////////////////////////////////////////
    //////                  Getters and setters                     /////
    /////////////////////////////////////////////////////////////////////

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessageCategory() {
        return this.messageCategory;
    }

    public void setMessageCategory(String messageCategory) {
        if (messageCategory == null) {
            throw new IllegalArgumentException("messageCategory passed in should not be null.");
        }

        this.messageCategory = messageCategory;
    }

    public int getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(int createBy) {
        this.createBy = createBy;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        if (createdAt == null) {
            throw new IllegalArgumentException("createdAt passed in should not be null.");
        }
        
        this.createdAt = createdAt;
    }

    public int getEventId() {
        return this.eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

}
