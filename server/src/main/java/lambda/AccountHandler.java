package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.Account;

import java.util.*;

public class AccountHandler {
    /*
	public static void main(String[] args) {
		Map<String, String> test = new HashMap<String, String>();
		test.put("username", "rettmoo");
		test.put("password", "hehe");
		test.put("email", "rettymoo@hotmail.");
		test.put("firstname", "Ruoyu");
		test.put("lastname", "Mo");

		new AccountHandler().signup(test, null);

	}
	*/
	
	public Map<String, String> login(Map<String, String> input, Context context) {
		System.out.println("(login) " + input.get("username") + ": " + input.get("password"));
        return new Account(input.get("username"), input.get("password")).isLogin();
	}
	
	public Map<String, String> signup(Map<String, String> input, Context context) {
		System.out.println("(signup) " + input.get("username") + ": " + input.get("password"));
		return Account.signup(input.get("username"), input.get("password"), input.get("email"), input.get("lastname"),
				input.get("firstname"));
	}
}
