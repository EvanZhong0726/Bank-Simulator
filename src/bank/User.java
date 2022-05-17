package bank;
import java.util.LinkedList;


public class User {
	public String username;
	private String password;
	private LinkedList<Account> accounts;
	
	public User(String username, String password) {
		if(username=="") {
			throw new IllegalArgumentException("name cannot be empty");
		}
		if(password.length()<8) {
			throw new IllegalArgumentException("Password has to be at least 8 characters long");
		}
		this.username=username;
		this.password=password;
		this.accounts=new LinkedList<Account>();
	}
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	public Account openAccount(String type) {
		Account account=new Account(this.username,type);
		this.accounts.add(account);
		return account;
	}
	
	public Account openAccount(String type, int balance) {
		Account account=new Account(this.username,type,balance);
		this.accounts.add(account);
		return account;
	}
	
	public void closeAccount(Account account) {
		this.accounts.remove(account);
	}
	
	public LinkedList<Account> getAccounts(){
		return this.accounts;
	}
	
	public Account getSingleAccount(String accountNumber) {
		for (Account account : this.accounts) {
			if (account.getAccountNumber().equals(accountNumber)) {
				return account;
			}
		} throw new IllegalArgumentException("there is no such account, wrong account number");
	}

}
