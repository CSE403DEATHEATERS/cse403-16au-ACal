package model;

public class AccountResponseClass {
	String userId;
	String email;
	String lastname;
	String firstname;
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getLastname() {
		return this.lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getFirstname() {
		return this.firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * Construct an account response class by account request class
	 */
	public AccountResponseClass(AccountRequestClass account) {
		if (account.isLogin()) {
			// TODO: retrieve data from database
			this.userId = "";
			this.email = "";
			this.lastname = "";
			this.firstname = "";
		} else {
			this.userId = null;
			this.email = null;
			this.lastname = null;
			this.firstname = null;
		}
	}
	
}
