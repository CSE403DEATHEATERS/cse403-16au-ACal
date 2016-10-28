package lambda;

import com.amazonaws.services.lambda.runtime.Context;
import model.AccountRequestClass;
import model.AccountResponseClass;

public class AccountHandler {
	
	public AccountResponseClass login(AccountRequestClass input, Context context) {
		System.out.println("(login) " + input.getUsername() + ": " + input.getPassword());
		return input.isLogin() ? new AccountResponseClass(input) : null;
	}
	
	public AccountResponseClass signup(AccountRequestClass input, Context context) {
		System.out.println("(signup) " + input.getUsername() + ": " + input.getPassword());
		return input.signup() ? new AccountResponseClass(input) : null;
	}
}
