package dbManager;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * DB Manager
 */
public class DynamoDBManager {
    public static final BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAIG5ZINASWAB33EXA","OeizWTU2FObibQrywkLbE2BMYidieo4HmIcymcUK" );
    public static final AmazonDynamoDBClient CLIENT = new AmazonDynamoDBClient(credentials).withEndpoint("https://dynamodb.us-west-2.amazonaws.com");
    public static final DynamoDB dynamoDB = new DynamoDB(CLIENT);

}
