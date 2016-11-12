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

@DynamoDBTable(tableName = "acalendar-mobilehub-1275254137-relationship")

public class RelationshipDO {
    private String _userId1;
    private String _userId2;
    private String _createTime;
    private Double _status;

    @DynamoDBHashKey(attributeName = "userId_1")
    @DynamoDBAttribute(attributeName = "userId_1")
    public String getUserId1() {
        return _userId1;
    }

    public void setUserId1(final String _userId1) {
        this._userId1 = _userId1;
    }
    @DynamoDBRangeKey(attributeName = "userId_2")
    @DynamoDBAttribute(attributeName = "userId_2")
    public String getUserId2() {
        return _userId2;
    }

    public void setUserId2(final String _userId2) {
        this._userId2 = _userId2;
    }
    @DynamoDBAttribute(attributeName = "createTime")
    public String getCreateTime() {
        return _createTime;
    }

    public void setCreateTime(final String _createTime) {
        this._createTime = _createTime;
    }
    @DynamoDBAttribute(attributeName = "status")
    public Double getStatus() {
        return _status;
    }

    public void setStatus(final Double _status) {
        this._status = _status;
    }

}
