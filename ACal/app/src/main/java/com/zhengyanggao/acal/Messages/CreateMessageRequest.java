package com.zhengyanggao.acal.Messages;

/**
 * Created by ZhengyangGao on 10/26/16.
 */
public class CreateMessageRequest {

    /**
     * The category of the message
     * */
    private MessageCategory messageCategory;

    /**
     * The id of the event with which the message is associated
     * */
    private int eventId;

    /**
     * Return the message category of this CreateMessageRequest
     *
     * @return the message category of this CreateMessageRequest
     * */
    public MessageCategory getMessageCategory() {
        return this.messageCategory;
    }

    /**
     * Set the message category of this CreateMessageRequest
     *
     * @param messageCategory
     * */
    public void setMessageCategory(MessageCategory messageCategory) {
        this.messageCategory = messageCategory;
    }

    /**
     * Get the event id
     *
     * @return event id
     * */
    public int getEventId() {
        return this.eventId;
    }

    /**
     * Set the event id
     *
     * @param eventId
     * */
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

}
