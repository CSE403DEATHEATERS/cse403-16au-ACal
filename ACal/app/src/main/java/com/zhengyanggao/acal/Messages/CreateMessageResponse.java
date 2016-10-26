package com.zhengyanggao.acal.Messages;

/**
 * Created by ZhengyangGao on 10/26/16.
 */
public class CreateMessageResponse {

    /**
     * The id of the message created
     * */
    private int messageId;

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
