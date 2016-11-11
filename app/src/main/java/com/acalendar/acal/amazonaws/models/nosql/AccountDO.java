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

@DynamoDBTable(tableName = "acalendar-mobilehub-1275254137-Account")

public class AccountDO {
    private String _userId;
    private String _createdTime;
    private String _email;
    private String _firstname;
    private String _iconid;
    private String _lastname;
    private String _password;
    private String _username;
    private Double _verificationCode;
    private Boolean _verified;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "createdTime")
    public String getCreatedTime() {
        return _createdTime;
    }

    public void setCreatedTime(final String _createdTime) {
        this._createdTime = _createdTime;
    }
    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return _email;
    }

    public void setEmail(final String _email) {
        this._email = _email;
    }
    @DynamoDBAttribute(attributeName = "firstname")
    public String getFirstname() {
        return _firstname;
    }

    public void setFirstname(final String _firstname) {
        this._firstname = _firstname;
    }
    @DynamoDBAttribute(attributeName = "iconid")
    public String getIconid() {
        return _iconid;
    }

    public void setIconid(final String _iconid) {
        this._iconid = _iconid;
    }
    @DynamoDBAttribute(attributeName = "lastname")
    public String getLastname() {
        return _lastname;
    }

    public void setLastname(final String _lastname) {
        this._lastname = _lastname;
    }
    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return _password;
    }

    public void setPassword(final String _password) {
        this._password = _password;
    }
    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return _username;
    }

    public void setUsername(final String _username) {
        this._username = _username;
    }
    @DynamoDBAttribute(attributeName = "verificationCode")
    public Double getVerificationCode() {
        return _verificationCode;
    }

    public void setVerificationCode(final Double _verificationCode) {
        this._verificationCode = _verificationCode;
    }
    @DynamoDBAttribute(attributeName = "verified")
    public Boolean getVerified() {
        return _verified;
    }

    public void setVerified(final Boolean _verified) {
        this._verified = _verified;
    }

}
