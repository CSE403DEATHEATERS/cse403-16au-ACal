package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.Account;

import java.util.*;

public class AccountHandler {

	public static void main(String[] args) {
		Map<String, String> test = new HashMap<String, String>();
		test.put("username", "myfriend");
		test.put("password", "hehe");
		test.put("email", "rettymoo@gmail.com");
		test.put("firstname", "Ruoyu");
		test.put("lastname", "Mo");
		//System.out.println(test.get("heh"));
		//new AccountHandler().signup(test, null);

	}

	/**
	 * Login lambda
     *
	 * @param input username and password as key in a Map<String, String>
	 * @param context
	 * @return a Map stores user info if username and password are matched, or empty map
	 */
	public Map<String, String> login(Map<String, String> input, Context context) {
		if (input == null) {
			throw new IllegalArgumentException();
		}
        String username = input.get("username");
		String password = input.get("password");
		if (username == null || password == null) {
			return new HashMap<String, String>();
		}
		System.out.println("(login) " + username + ": " + password);
        return new Account(username, password).isLogin();
	}

	/**
	 * Signup lambda, register user account to the system if username and email have not been used.
	 *
	 * @param input username, password, email, lastname, firstname as key in a Map<String, String>
	 * @param context
	 * @return a Map stores user info if username and email are unique (never be used by others), or empty map
	 */
	public Map<String, String> signup(Map<String, String> input, Context context) {
		if (input == null) {
			throw new IllegalArgumentException();
		}
		String username = input.get("username");
		String password = input.get("password");
		String email = input.get("email");
		String lastname = input.get("lastname");
		String firstname = input.get("firstname");
		if (username == null || password == null || email == null || lastname == null || firstname == null) {
			return new HashMap<String, String>();
		}
		System.out.println("(signup) " + username + ": " + password);
		return Account.signup(username, password, email, lastname, firstname);
	}
}
