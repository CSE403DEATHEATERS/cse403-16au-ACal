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

@DynamoDBTable(tableName = "acalendar-mobilehub-1275254137-eventInfo")

public class EventInfoDO {
    private String _eventId;
    private String _createTime;
    private String _description;
    private String _endTime;
    private String _isPublic;
    private String _location;
    private String _ownerUid;
    private String _startTime;
    private Boolean _title;

    @DynamoDBHashKey(attributeName = "eventId")
    @DynamoDBAttribute(attributeName = "eventId")
    public String getEventId() {
        return _eventId;
    }

    public void setEventId(final String _eventId) {
        this._eventId = _eventId;
    }
    @DynamoDBAttribute(attributeName = "createTime")
    public String getCreateTime() {
        return _createTime;
    }

    public void setCreateTime(final String _createTime) {
        this._createTime = _createTime;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return _description;
    }

    public void setDescription(final String _description) {
        this._description = _description;
    }
    @DynamoDBAttribute(attributeName = "endTime")
    public String getEndTime() {
        return _endTime;
    }

    public void setEndTime(final String _endTime) {
        this._endTime = _endTime;
    }
    @DynamoDBAttribute(attributeName = "isPublic")
    public String getIsPublic() {
        return _isPublic;
    }

    public void setIsPublic(final String _isPublic) {
        this._isPublic = _isPublic;
    }
    @DynamoDBAttribute(attributeName = "location")
    public String getLocation() {
        return _location;
    }

    public void setLocation(final String _location) {
        this._location = _location;
    }
    @DynamoDBAttribute(attributeName = "ownerUid")
    public String getOwnerUid() {
        return _ownerUid;
    }

    public void setOwnerUid(final String _ownerUid) {
        this._ownerUid = _ownerUid;
    }
    @DynamoDBAttribute(attributeName = "startTime")
    public String getStartTime() {
        return _startTime;
    }

    public void setStartTime(final String _startTime) {
        this._startTime = _startTime;
    }
    @DynamoDBAttribute(attributeName = "title")
    public Boolean getTitle() {
        return _title;
    }

    public void setTitle(final Boolean _title) {
        this._title = _title;
    }

}
