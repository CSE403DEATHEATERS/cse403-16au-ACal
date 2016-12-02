package model;

/**
 * Reqeust object used when retrieving objects from db
 *
 */
public class GetMessagesRequest {

    /**
     * The id of the event whose messages we want to retrieve
     * (a UUID String)
     * */
    private String eventId;


    /////////////////////////////////////////////////////////////////
    /////                getters and setters                   //////
    /////////////////////////////////////////////////////////////////
    
    public GetMessagesRequest(String eventId) {
    	this.eventId = eventId;
    }

    public String getEventId() {
        return this.eventId;
    }
}
