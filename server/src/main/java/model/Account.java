package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;

import javax.mail.internet.InternetAddress;

import static dbManager.DynamoDBManager.dynamoDB;

public class Account {
    public static final String TABLE_NAME = "acalendar-mobilehub-1275254137-Account";
    public static final Table TABLE = dynamoDB.getTable(TABLE_NAME);
	private String userId;
	private String username;
	private String password;
	private String email;
	private String lastname;
	private String firstname;
	private boolean login;

    /**
     * Constructor for searching account
     * @param userId
     */
    public Account(String userId) {
        this.userId = userId;
        GetItemSpec getItem = new GetItemSpec().withPrimaryKey("userId", userId);
        Item item = TABLE.getItem(getItem);
        if (item != null) {
            this.username = item.getString("username");
            this.email = item.getString("email");
            this.lastname = item.getString("lastname");
            this.firstname = item.getString("firstname");
            this.login = true;
        } else {
            this.login = false;
        }
    }

    /**
     * Constructor for login account
     * @param username
     * @param password
     */
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
        ScanSpec scan = new ScanSpec().withFilterExpression("username=:v_username")
										.withValueMap(new ValueMap().withString(":v_username", this.username));
        try {
            Iterator<Item> items = TABLE.scan(scan).iterator();
            if (items.hasNext()) {
                Item account = items.next();
                if (items.hasNext()) {
                    this.login = false;
                    throw new IllegalArgumentException("Database corrupted. Duplicate username.");
                }
                if (this.password.equals(account.getString("password"))) {
                    this.login = true;
                    this.userId = account.getString("userId");
                    this.email = account.getString("email");
                    this.lastname = account.getString("lastname");
                    this.firstname = account.getString("firstname");
                } else {
                    this.login = false;
                }
            } else {
                this.login = false;
            }
        } catch (Exception e) {
            this.login = false;
        }
	}

    /**
     * Constructor for creating account
     * @param username
     * @param password
     * @param email
     * @param lastname
     * @param firstname
     */
	private Account(String username, String password, String email, String lastname, String firstname) {
        // only use for new account sign up
		ScanSpec scan = new ScanSpec().withFilterExpression("username=:v_username OR email=:v_email")
				.withValueMap(new ValueMap().withString(":v_username", username)
											.withString(":v_email", email));
        try {
            if (TABLE.scan(scan).iterator().hasNext()) {
                this.login = false;
                System.out.println("Username or email is being used.");
            } else {
                this.userId = UUID.randomUUID().toString();
                this.username = username;
                this.password = password;
                this.email = email;
                this.lastname = lastname;
                this.firstname = firstname;
                this.login = true;
                Item item = new Item()
                        .withPrimaryKey("userId", this.userId)
                        .withString("username", this.username)
                        .withString("password", this.password)
                        .withString("email", this.email)
                        .withString("firstname", this.firstname)
                        .withString("lastname", this.lastname);
                TABLE.putItem(item);
                javax.mail.Address senderEmail = new InternetAddress("acal.techteam@gmail.com");
                javax.mail.Address[] recipientEmails = new InternetAddress[1];
                recipientEmails[0] = new InternetAddress(this.email);
                String emailContent = "Yo! Dear " + firstname + ",<br><br>" + "Congratulations!<br> " +
                        "You are signed up to ACalendar. Have a good time with us!" +
                        "<br><br>Best Wishes<br>" +
                        "ACalendar Tech Team";
                Email confirmationEmail = new Email("Sign Up Confirmation", "Welcome to ACalendar!", emailContent, senderEmail, recipientEmails);
                EmailManager emailManager = new EmailManager();
                emailManager.send(confirmationEmail);
            }
        } catch (Exception e) {
            this.login = false;
        }
	}

    /**
     * Get information about the account
     *
     * @return Map<String, Object> stores account info
     */
	private Map<String, Object> getInfo() {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("userId", this.userId);
		info.put("username", this.username);
        info.put("email", this.email);
		info.put("lastname", this.lastname);
		info.put("firstname", this.firstname);
        return info;
	}
	
	/**
	 * Get login status of the account
	 * 
	 * @return Map<String, Object> that stores necessary information of an account
	 * 							   if the account username and password is matched.
	 * 							   If not, return empty map
	 */
	public Map<String, Object> isLogin() {
		return this.login ? this.getInfo() : new HashMap<String, Object>();
	}
	
	/**
	 * Signup an new account with unique email and username
	 *
	 * @param username String
	 * @param password String
	 * @param email String
	 * @param firstname String
	 * @param lastname String
	 * @return Map<String, Object> that stores necessary information of an account
	 */
	public static Map<String, Object> signup(String username, String password, String email, String lastname,
											 String firstname) {
		Account newAccount = new Account(username, password, email, lastname, firstname);
		return newAccount.isLogin();
	}

    /**
     *
     * @param username
     * @param email
     * @return
     */
	public static String getUserIdByUsernameOrEmail(String username, String email) {
        username = username == null || username.isEmpty() ? null : username;
        email = email == null || email.isEmpty() ? null : email;
        ScanSpec scan = new ScanSpec().withFilterExpression("username=:v_username OR email=:v_email")
                .withValueMap(new ValueMap().withString(":v_username", username)
                        .withString(":v_email", email));
        try {
            Iterator<Item> items = TABLE.scan(scan).iterator();
            if (items.hasNext()) {
                return items.next().getString("userId");
            } else {
                return "";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

	public static Map<String, Object> getInfoByUserId(String userId) {
        return new Account(userId).getInfo();
    }

}
