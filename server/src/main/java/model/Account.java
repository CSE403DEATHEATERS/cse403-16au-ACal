package model;

import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

public class Account {
    public static final AmazonDynamoDBClient CLIENT = new AmazonDynamoDBClient().withEndpoint("https://dynamodb.us-west-2.amazonaws.com");
    public static final DynamoDB DYNAMO_DB = new DynamoDB(CLIENT);
    public static final String TABLE_NAME = "acalendar-mobilehub-1275254137-Account";
    public static final Table TABLE = DYNAMO_DB.getTable(TABLE_NAME);
	String userId;
	String username;
	String password;
	String email;
	String lastname;
	String firstname;
	boolean login;

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		// TODO: verify account password from database and assign following fields
        GetItemSpec testGet = new GetItemSpec().withPrimaryKey("userName", this.username);
        Item res = TABLE.getItem(testGet);
		this.userId = res.getString("userId");
        System.out.println(this.userId);
        System.out.println(res.toString());
		this.email = res.getString("email");
		this.lastname = res.getString("lastname");
		this.firstname = res.getString("firstname");
		if (this.password.equals(res.getString("password"))) {
			this.login = true;
		} else {
			this.login = false;
		}
	}

	private Account(String username, String password, String email, String lastname, String firstname) {
        // only use for new account sign up
        // TODO: autogenerate userId
		this.userId = "000";
		this.username = username;
		this.password = password;
		this.email = email;
		this.lastname = lastname;
		this.firstname = firstname;
        // TODO: check unique email and username, then decide login and write data to db
        this.login = true;
	}

    /**
     * Get information about the account
     *
     * @return Map<String, String> stores account info
     */
	private Map<String, String> getInfo() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("userId", this.userId);
		info.put("username", this.username);
		info.put("lastname", this.lastname);
		info.put("firstname", this.firstname);
        return info;
	}
	
	/**
	 * Get login status of the account
	 * 
	 * @return Map<String, String> that stores necessary information of an account
	 * 							   if the account username and password is matched.
	 * 							   If not, return empty map
	 */
	public Map<String, String> isLogin() {
		return this.login ? this.getInfo() : new HashMap<String, String>();
	}
	
	/**
	 * Signup an new account with unique email and username
	 *
	 * @param username String
	 * @param password String
	 * @param email String
	 * @param firstname String
	 * @param lastname String
	 * @return Map<String, String> that stores necessary information of an account
	 */
	public static Map<String, String> signup(String username, String password, String email, String lastname,
											 String firstname) {
		// TODO: check information is unique, save account to database, assign
		Account newAccount = new Account(username, password, email, lastname, firstname);
		return newAccount.getInfo();
	}

}
