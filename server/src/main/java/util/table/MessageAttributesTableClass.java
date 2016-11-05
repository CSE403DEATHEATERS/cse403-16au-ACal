package util.table;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import model.MessageAttributeType;

/**
 * Each instance of this class represents a row in the message_attributes table
 *
 */
@DynamoDBTable(tableName = "message_attributes")
public class MessageAttributesTableClass {

    /**
     * Id of the message
     */
    private String messageId;

    /**
     * Type of this attribute
     */
    private String attributeType;

    /**
     * Value associated with this message and attribute
     */
    private String attributeValue;

    /////////////////////////////////////////////////////////////////
    /////                Getters and setters                   //////
    /////////////////////////////////////////////////////////////////

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(MessageAttributeType attributeType) {
        this.attributeType = attributeType.toString();
    }

}
