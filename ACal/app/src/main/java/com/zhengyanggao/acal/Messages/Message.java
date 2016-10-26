package com.zhengyanggao.acal.Messages;

import java.util.Date;
import java.util.IllegalFormatCodePointException;

/**
 * Created by ZhengyangGao on 10/26/16.
 *
 * Message class used by ACalendar.
 */
public class Message {

    /**
     * The id of the message
     * */
    private int messageId;

    /**
     * The category of the message
     * */
    private MessageCategory messageCategory;

    /**
     * The user that triggered the event that is creating this message
     * */
    private int createdBy;

    /**
     * Time when the Message is created
     * */
    private Date createdAt;

    /**
     * The event id with which this message is associated
     * */
    private int eventId;

    /////////////////////////////////////////////////////////////////
    /////                getters and setters                   /////
    ////////////////////////////////////////////////////////////////

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public MessageCategory getMessageCategory() {
        return this.messageCategory;
    }

    public void setMessageCategory(MessageCategory messageCategory) {
        if (messageCategory == null) {
            throw new IllegalArgumentException("messageCategory passed in should not be null.");
        }

        this.messageCategory = messageCategory;
    }

    public int getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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
