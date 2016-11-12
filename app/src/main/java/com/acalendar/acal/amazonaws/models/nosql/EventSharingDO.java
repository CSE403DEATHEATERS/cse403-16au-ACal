package com.acalendar.acal.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "acalendar-mobilehub-1275254137-eventSharing")

public class EventSharingDO {
    private String _eventId;
    private String _recipientUid;
    private String _sentTime;
    private Double _status;

    @DynamoDBHashKey(attributeName = "eventId")
    @DynamoDBAttribute(attributeName = "eventId")
    public String getEventId() {
        return _eventId;
    }

    public void setEventId(final String _eventId) {
        this._eventId = _eventId;
    }
    @DynamoDBAttribute(attributeName = "recipientUid")
    public String getRecipientUid() {
        return _recipientUid;
    }

    public void setRecipientUid(final String _recipientUid) {
        this._recipientUid = _recipientUid;
    }
    @DynamoDBAttribute(attributeName = "sentTime")
    public String getSentTime() {
        return _sentTime;
    }

    public void setSentTime(final String _sentTime) {
        this._sentTime = _sentTime;
    }
    @DynamoDBAttribute(attributeName = "status")
    public Double getStatus() {
        return _status;
    }

    public void setStatus(final Double _status) {
        this._status = _status;
    }

}
