package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.Account;

import java.util.*;

public class AccountHandler {
	
	public Map<String, String> login(Map<String, String> input, Context context) {
		System.out.println("(login) " + input.get("username") + ": " + input.get("password"));
        return new Account(input.get("username"), input.get("password")).isLogin();
	}
	
	public Map<String, String> signup(Map<String, String> input, Context context) {
		System.out.println("(signup) " + input.get("username") + ": " + input.get("password"));
		return new HashMap<String, String>();
	}
}
