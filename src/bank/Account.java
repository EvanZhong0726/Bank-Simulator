package bank;

import java.util.Random;

public class Account {
	private String accountType;
	private String accountNumber;
	private String user;
	private double balance;
	
	public void setAccountNumber(String input) {
		this.accountNumber = input;
	}
	
	
	public static String generateAccountNumber() {
		Random random = new Random();
		char[] digits = new char[8];
		for (int i = 0; i < 8; i++) {
			digits[i] = (char) (random.nextInt(10) + '0');
		}
		return new String(digits);
	}  
	
	public static boolean checkType(String type) {
		if(!(type.equalsIgnoreCase("saving")||type.equalsIgnoreCase("checking"))) {
			return false;
		}
		return true;
	}
	public static String updateType(String type) {
		if(type.equalsIgnoreCase("saving")){
			String s="saving";
			return s;
			}
		else if(type.equalsIgnoreCase("checking")) {
			String s="checking";
			return s;
		}
		return null;
			
	}
	public Account(String name, String type) {
		this.user=name;
		this.balance=0;
		this.accountNumber=generateAccountNumber();
		if(updateType(type)==null) {
			throw new IllegalArgumentException("invalid account type: can only be saving or checking");
		}
		this.accountType=updateType(type);
	}
	
	public Account(String name,String type, long balance) {
		this.user=name;
		this.balance=balance;
		this.accountNumber=generateAccountNumber();
		if(updateType(type)==null) {
			throw new IllegalArgumentException("invalid account type: can only be saving or checking");
		}
		this.accountType=updateType(type);
		
	}
	
	public void setDepositBalance(double amountAdded) { 
		if(amountAdded<=0) {
			throw new IllegalArgumentException("The deposit ammount must be positive");
		}
		this.balance += amountAdded;
	}
	
	public void setWithdrawBalance(double amountWithdrawn) { 
		if(this.accountType.equalsIgnoreCase("saving")) {
			throw new IllegalArgumentException("You cannot withdraw money from saving account");
		}
		if(amountWithdrawn<=0) {
			throw new IllegalArgumentException("The withdraw amount must be positive");
		}
		if(amountWithdrawn>=this.balance) {
			throw new IllegalArgumentException("The withdraw amount cannot exceed your current balance");
		}
		this.balance -= amountWithdrawn;
	}
	
	public void setLoanBalance(double amountLoaned) { 
		if(amountLoaned<=0) {
			throw new IllegalArgumentException("The loan amount must be positive");
		}
		this.balance += amountLoaned;
	}
	
	public String getAccountNumber() {
		return this.accountNumber;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public String getAccountType() {
		return this.accountType;
	}
	

}