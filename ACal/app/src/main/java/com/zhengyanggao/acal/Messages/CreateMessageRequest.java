package com.zhengyanggao.acal.Messages;

/**
 * Created by ZhengyangGao on 10/26/16.
 */
public class CreateMessageRequest {

    private MessageCategory messageCategory;
    private int eventId;

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
