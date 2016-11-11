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

@DynamoDBTable(tableName = "acalendar-mobilehub-1275254137-message_attributes")

public class MessageAttributesDO {
    private String _messageId;
    private String _attributeType;
    private String _attributeValue;

    @DynamoDBHashKey(attributeName = "messageId")
    @DynamoDBAttribute(attributeName = "messageId")
    public String getMessageId() {
        return _messageId;
    }

    public void setMessageId(final String _messageId) {
        this._messageId = _messageId;
    }
    @DynamoDBRangeKey(attributeName = "attributeType")
    @DynamoDBAttribute(attributeName = "attributeType")
    public String getAttributeType() {
        return _attributeType;
    }

    public void setAttributeType(final String _attributeType) {
        this._attributeType = _attributeType;
    }
    @DynamoDBAttribute(attributeName = "attributeValue")
    public String getAttributeValue() {
        return _attributeValue;
    }

    public void setAttributeValue(final String _attributeValue) {
        this._attributeValue = _attributeValue;
    }

}
