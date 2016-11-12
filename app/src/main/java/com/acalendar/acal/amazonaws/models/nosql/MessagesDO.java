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

@DynamoDBTable(tableName = "acalendar-mobilehub-1275254137-messages")

public class MessagesDO {
    private String _id;
    private Double _createdAt;
    private String _createdBy;
    private String _eventId;
    private String _messageCategory;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return _id;
    }

    public void setId(final String _id) {
        this._id = _id;
    }
    @DynamoDBAttribute(attributeName = "createdAt")
    public Double getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(final Double _createdAt) {
        this._createdAt = _createdAt;
    }
    @DynamoDBAttribute(attributeName = "createdBy")
    public String getCreatedBy() {
        return _createdBy;
    }

    public void setCreatedBy(final String _createdBy) {
        this._createdBy = _createdBy;
    }
    @DynamoDBIndexHashKey(attributeName = "eventId", globalSecondaryIndexName = "eventIdIndex")
    public String getEventId() {
        return _eventId;
    }

    public void setEventId(final String _eventId) {
        this._eventId = _eventId;
    }
    @DynamoDBAttribute(attributeName = "messageCategory")
    public String getMessageCategory() {
        return _messageCategory;
    }

    public void setMessageCategory(final String _messageCategory) {
        this._messageCategory = _messageCategory;
    }

}
