package model;

public class AccountRequestClass {
	String username;
	String password;
	String email;
	String lastname;
	String firstname;
	boolean login;
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public AccountRequestClass(String username, String password, String email, String lastname, String firstname) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.lastname = lastname;
		this.firstname = firstname;
		if (this.lastname == null && this.firstname == null) {
			// TODO: verify account password from database
			this.login = true;
		} else {
			this.login = false;
		}
	}
	
	public AccountRequestClass() {
		this("", null, null, null, null);
	}
	
	/**
	 * Get login status of the account
	 * 
	 * @return boolean true if the account username and password is matched
	 */
	public boolean isLogin() {
		return this.login;
	}
	
	/**
	 * Signup an new account with unique email and username
	 * 
	 * @return boolean true if the account is created without duplicated email and username
	 */
	public boolean signup() {
		// TODO: check information is unique, save account to database
		this.login = true;
		return true;
	}

}
