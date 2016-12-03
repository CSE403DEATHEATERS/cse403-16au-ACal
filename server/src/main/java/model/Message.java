package model;

/**
 * Class representing a message in the db
 *
 */
public class Message {

//    private String messageCategory;

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
    
    public Message(String eventId, String createBy, String messageContent, Long createdAt){
    	if (eventId == null || createBy == null|| messageContent == null||createdAt == null ) {
    		 throw new IllegalArgumentException("Request passed in and its fields should not be null!");
    	}
    	this.eventId = eventId;
    	this.createdAt = createdAt;
    	this.createdBy = createBy;
    	this.messageContent = messageContent;
    }

    /////////////////////////////////////////////////////////////////////
    //////                  Getters and setters                     /////
    /////////////////////////////////////////////////////////////////////


//    public String getMessageCategory() {
//        return this.messageCategory.toString();
//    }
//
//    public void setMessageCategory(MessageCategory messageCategory) {
//        if (messageCategory == null) {
//            throw new IllegalArgumentException("messageCategory passed in should not be null.");
//        }
//
//        this.messageCategory = messageCategory.toString();
//    }

    public String getCreatedBy() {
        return this.createdBy;
    }


    public Long getCreatedAt() {
        return new Long(this.createdAt);
    }

    public String getEventId() {
        return this.eventId;
    }


    public String getMessageContent() {
        return this.messageContent;
    }

}

