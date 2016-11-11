package dbManager;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * DB Manager
 */
public class DynamoDBManager {
    public static final AmazonDynamoDBClient CLIENT = new AmazonDynamoDBClient().withEndpoint("https://dynamodb.us-west-2.amazonaws.com");
    public static final DynamoDB dynamoDB = new DynamoDB(CLIENT);
}
