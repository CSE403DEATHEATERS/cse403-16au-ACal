package dbManager;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class TestDBManager {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BasicAWSCredentials b = new BasicAWSCredentials("AWS_ACCESS_KEY_ID", "AWS_SECRET_ACCESS_KEY");

		AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("https://dynamodb.us-west-2.amazonaws.com");

		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("acalendar-mobilehub-1275254137-Account");

		int year = 2015;
		String title = "The Big New Movie";

		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
				.withPrimaryKey(new PrimaryKey("year", year, "title", title))
				.withConditionExpression("info.rating <= :val").withValueMap(new ValueMap().withNumber(":val", 5.0));
		GetItemSpec testGet = new GetItemSpec().withPrimaryKey("userId", "123");

		// Conditional delete (we expect this to fail)

		try {
			System.out.println("Attempting a conditional delete...");
			Item res = table.getItem(testGet);
			System.out.println("GetItem succeeded: " + res);
		} catch (Exception e) {
			System.err.println("Unable to get item: 123");
			System.err.println(e.getMessage());
		}
	}

}

//public class MoviesItemOps06 {
//
//	public static void main(String[] args) throws Exception {
//
//		AmazonDynamoDBClient client = new AmazonDynamoDBClient().withEndpoint("http://localhost:8000");
//
//		DynamoDB dynamoDB = new DynamoDB(client);
//
//		Table table = dynamoDB.getTable("Movies");
//
//		int year = 2015;
//		String title = "The Big New Movie";
//
//		DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
//				.withPrimaryKey(new PrimaryKey("year", year, "title", title))
//				.withConditionExpression("info.rating <= :val").withValueMap(new ValueMap().withNumber(":val", 5.0));
//
//		// Conditional delete (we expect this to fail)
//
//		try {
//			System.out.println("Attempting a conditional delete...");
//			table.deleteItem(deleteItemSpec);
//			System.out.println("DeleteItem succeeded");
//		} catch (Exception e) {
//			System.err.println("Unable to delete item: " + year + " " + title);
//			System.err.println(e.getMessage());
//		}
//	}
//}