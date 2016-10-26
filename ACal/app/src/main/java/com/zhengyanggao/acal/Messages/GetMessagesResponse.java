package com.zhengyanggao.acal.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object returned by getMessages()
 *
 * Created by ZhengyangGao on 10/26/16.
 */
public class GetMessagesResponse {

    /**
     * A list of messages fetched from our database.
     * */
    private List<Message> messages;

    /**
     * Get the list of messages fetched from our database.
     *
     * @return a list of messages fetched from our database.
     * */
    public List<Message> getMessages() {
        return new ArrayList<Message>(this.messages);
    }

    /**
     * Set the list of Messages of this response object
     * */
    public void setMessages(List<Message> messages) {
        if (messages == null) {
            throw new IllegalArgumentException("messages passed in should not be null.");
        }

        for (Message message : messages) {
            if (message == null) {
                throw new IllegalArgumentException("messages in messages should not be null.");
            }
        }

        this.messages = new ArrayList<Message>(messages);
    }

}
