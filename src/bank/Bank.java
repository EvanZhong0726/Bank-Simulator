package bank;
import java.util.LinkedList;
import java.util.Scanner;

public class Bank {
	
	public String name;
	private LinkedList<User> users;
	private LinkedList<Account> accounts;
	
	
	public Bank(String name) {
		this.name=name;
		this.users=new LinkedList<User>();
		this.accounts=new LinkedList<Account>();
		
	}
	
	public void setUsers(LinkedList<User> input) {
		this.users = input;
	}
	
	public void setAccounts(LinkedList<Account> input) {
		this.accounts = input;
	}
	
	public static boolean isInteger(String str) {
		 if (str == null) {
		        return false;
		    }
		    int length = str.length();
		    if (length == 0) {
		        return false;
		    }
		    int i = 0;
		    if (str.charAt(0) == '-') {
		        if (length == 1) {
		            return false;
		        }
		        i = 1;
		    }
		    for (; i < length; i++) {
		        char c = str.charAt(i);
		        if (c < '0' || c > '9') {
		            return false;
		        }
		    }
		    return true;
	}
	public static void printNotNumberError() {
		System.out.println("Error: "+"Your input is not a number, please try again!");
	}
	public static void printTypeError() {
		System.out.println("Error: "+"Please type in checking or saving");
	}
	public static void printAmountError(Account account) {
		System.out.println("Error: "+"The amount you entered exceeds your balance of $" + account.getBalance());
	}
	public static void printAccountInfo(Account account) {
		System.out.println("Here is your account information");
		System.out.println("Your account number: "+account.getAccountNumber());
		System.out.println("Your account type: "+account.getAccountType());
		System.out.println("Your balance: "+account.getBalance());
	}
	public static boolean checkType(String type) {
		return type.equalsIgnoreCase("checking") || type.equalsIgnoreCase("saving");
	}
	public static String promptAccountNumber(Scanner input) {
		System.out.println("Please put in your account number");
		String accountNumber = input.next();
		return accountNumber;
	}
	
	public static String promptAmount(Scanner input) {
		System.out.println("Please put in the amount of money");
		String balance=input.next(); 
		return balance;
	}
	public static String promptType(Scanner input) {
		System.out.println("What's your desired account type? Please type in checking or saving");
		String type=input.next();
		return type;
	}
	public static String promptUsername(Scanner input) {
		System.out.println("What's the username?");
		String username=input.next();
		return username;
	}
	public static String promptPassword(Scanner input) {
		System.out.println("What's the password");
		String password=input.next();
		return password;
	}
	public static void userPrompt() {
		System.out.println();
	    System.out.println("What would you like to do?");
	    System.out.println("Type open to open an account");
	    System.out.println("Type balance to get the balance");
	    System.out.println("Type deposit to make a deposit");
	    System.out.println("Type withdraw to make a withdraw");
	    System.out.println("Type transfer to make a transfer");
	    System.out.println("Type loan to make a loan");
	    System.out.println("Type show to show all your accounts");
	    System.out.println("Type quit to quit");
	    System.out.println();
	}
	public static void initialPrompt(Bank bank) {
		System.out.println();
		System.out.println("Hi, welcome to "+bank.name);
		System.out.println("Type login to login to your profile");
		System.out.println("Type create to create your profile");
		System.out.println("Type quit to quit");
		System.out.println();
	}
	
	
	public boolean checkUsernameExist(String username) {
		for (User user : this.users) {
			if(user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public Account getSingleAccount(String accountNumber) {
		for (Account account : this.accounts) {
			if (account.getAccountNumber().equals(accountNumber)) {
				return account;
			}
		} throw new IllegalArgumentException("Error: "+"There is no such account, wrong account number");
	}
	
	public User setUpUser(String username, String password) {
		User user=null;
		if(checkUsernameExist(username)) {
			System.out.println("Error: "+"This username already exists, please choose another one!");
			return user;
		}
		try {
		      user=new User(username,password);
		      this.users.add(user);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return user;
		
	}
		
	public User logIntoAccount(String username, String password) {
		for (User user : this.users) {
			if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
				return user;
			}
		}
		return null;
	}
	
	
	public void openAccountMenu(Scanner input, User user) {
		String type=promptType(input);
		if(!checkType(type)) {
			printTypeError();
			return ;
		}
		else {
			System.out.println("Do you want to put in any money now? Please type in Yes or No");
			String yesOrNo=input.next();
			String amount="";
			if(yesOrNo.equalsIgnoreCase("Yes")) {
				amount=promptAmount(input);
			}
			if(!yesOrNo.equalsIgnoreCase("Yes") && !yesOrNo.equalsIgnoreCase("No")) {
				System.out.println("Error: "+"Please put in yes or no for this question");	
				return ;
			}
			else {
				if(isInteger(amount) && yesOrNo.equalsIgnoreCase("Yes")) {
					Account accountOpened=openAccountForUser(user, type, yesOrNo, amount);
					if(accountOpened!=null) {
						printAccountInfo(accountOpened);
						this.accounts.add(accountOpened);
					}
				}
				else if (yesOrNo.equalsIgnoreCase("No")) {
					Account accountOpened=openAccountForUser(user, type, yesOrNo, amount);
					if(accountOpened!=null) {
						printAccountInfo(accountOpened);
					}
				}
				else {
					printNotNumberError();
				}
		    }
		
		}
	}
	
	
	
	public Account openAccountForUser(User user, String type, String yesOrNo, String balance) {
		Account account=null;
		if(yesOrNo.equalsIgnoreCase("Yes")) {
			try {
			account=user.openAccount(type, Integer.parseInt(balance));
			this.accounts.add(account);
			}
			catch(Exception e) {
				System.out.print("Error: "+e.getMessage());
			}
		}
		else{
			try {
			account=user.openAccount(type);
			this.accounts.add(account);
			}
			catch(Exception e) {
				System.out.println("Error: "+e.getMessage());
			}
			
		}
		return account;
		
	}
	
	
	
	public double accountBalance(String accountNumber, User user) {
		double balance=-1;
		try {
		Account accountOfUser = user.getSingleAccount(accountNumber);
		System.out.println("Your balance is: $" + accountOfUser.getBalance());
		balance=accountOfUser.getBalance();
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}  
		return balance;
	}
	
	
	
	public double deposit(String accountNumber, String amount, User user) {
		double balance=-1;
		try {
			Account accountOfUser = user.getSingleAccount(accountNumber);
			accountOfUser.setDepositBalance(Integer.parseInt(amount));
			System.out.println("Your balance is now: $" + accountOfUser.getBalance());
			balance=accountOfUser.getBalance();
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		return balance;
	}
	
	
	
	public double withdrawal(String accountNumber, String amount, User user) {
		double balance=-1;
		try {
			Account accountOfUser = user.getSingleAccount(accountNumber);
			if (accountOfUser.getBalance() >= Integer.parseInt(amount)) {
				accountOfUser.setWithdrawBalance(Integer.parseInt(amount));
				System.out.println("Your balance is now: $" + accountOfUser.getBalance());
				balance=accountOfUser.getBalance();
			}
			else {
				printAmountError(accountOfUser);
			}
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		return balance;
	}
	
	public double loan(String accountNumber, String amount, User user) {
		double balance=-1;
		try {
			Account accountOfUser = user.getSingleAccount(accountNumber);
			accountOfUser.setLoanBalance(Integer.parseInt(amount));
			System.out.println("Your balance is now: $" + accountOfUser.getBalance());
			balance=accountOfUser.getBalance();		
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		return balance;
	}
	
	
	
	public double[] transfer(String accountNumberOne, String accountNumberTwo, String amount, User user) {
		double[] balance=new double[2];
		try {
			Account accountOne = this.getSingleAccount(accountNumberOne);
			Account accountTwo = this.getSingleAccount(accountNumberTwo);
			if(Integer.parseInt(amount)>accountOne.getBalance()) {
				printAmountError(accountOne);
				return balance;
			}
			try {
				user.getSingleAccount(accountNumberOne);
			}
			catch(Exception e){
				System.out.println("Error: The first account must belong to you");
				return balance;
			}
			accountOne.setWithdrawBalance(Integer.parseInt(amount));
			accountTwo.setDepositBalance(Integer.parseInt(amount));
			balance[0]=accountOne.getBalance();
			balance[1]=accountTwo.getBalance();
			System.out.println("The balance of account " + accountOne.getAccountNumber() + " is now: $" + accountOne.getBalance());
			System.out.println("The balance of account " + accountTwo.getAccountNumber() + " is now: $" + accountTwo.getBalance());
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
		return balance;

	}
	
	public String[] showAccounts(User user) {
		String[] accounts=new String[user.getAccounts().size()];
		int i=0;
		for(Account account : user.getAccounts()) {
			accounts[i]=account.getAccountNumber();
			System.out.println("Account"+i+": "+account.getAccountNumber());
			i++;
		}
		return accounts;
	}
	
	public void closeAccountForUser(User user, String accountNumber) {
		try {
			Account accountOfUser = user.getSingleAccount(accountNumber);
			user.closeAccount(accountOfUser);
			this.accounts.remove(accountOfUser);
		}
		catch(Exception e) {
			System.out.println("Error: "+e.getMessage());
		}
	}
	
	
	public void processUserArguments(Scanner input, User user) {
		userPrompt();
		String argument = input.next();
		System.out.println();
		if (argument.equals("open")) {
			openAccountMenu(input,user);
			processUserArguments(input,user);
		} 
		else if (argument.equals("balance")){
			String accountNumber=promptAccountNumber(input);
			this.accountBalance(accountNumber, user);
			processUserArguments(input,user);
		}
		else if(argument.equals("deposit")) {
			String accountNumber=promptAccountNumber(input);
			String amount=promptAmount(input);
			if(isInteger(amount)) {
				deposit(accountNumber, amount, user);
			}
			else {
				printNotNumberError();
			}
			processUserArguments(input,user);
		}
		else if(argument.equals("withdraw")){
			String accountNumber=promptAccountNumber(input);
			String amount=promptAmount(input);
			if(isInteger(amount)) {
				withdrawal(accountNumber, amount, user);
			}
			else {
				printNotNumberError();
			}
			processUserArguments(input,user);
		}
		else if(argument.equals("transfer")) {
			String accountNumberOne=promptAccountNumber(input);
			String accountNumberTwo=promptAccountNumber(input);
			String amount=promptAmount(input);
			if(isInteger(amount)) {
				transfer(accountNumberOne,accountNumberTwo,amount, user);
			}
			else {
				printNotNumberError();
			}
			processUserArguments(input,user);
		}
		else if(argument.equals("loan")) {
			String accountNumber=promptAccountNumber(input);
			String amount=promptAmount(input);
			if(isInteger(amount)) {
				loan(accountNumber,amount, user);
			}
			else {
				printNotNumberError();
			}
			processUserArguments(input,user);
		}
		else if(argument.equals("show")) {
			showAccounts(user);
			processUserArguments(input,user);
		}
		else if(argument.equals("close")) {
			String accountNumber=promptAccountNumber(input);
			closeAccountForUser(user, accountNumber);
		}
		else if(argument.equals("quit")) {		
		}
		else {
			System.out.println("Invalid argument");
			processUserArguments(input, user);
		}

	}
	
	
	
	public void processInitialArguments(Scanner input) {
		initialPrompt(this);
		String argument = input.next();
		System.out.println();
		if(argument.equals("login")) {
			String username=promptUsername(input);
			String password=promptPassword(input);
			User user=this.logIntoAccount(username, password);
			if(user==null) {
				System.out.println("Wrong username and password combination, please try again");
			}
			else {
				processUserArguments(input,user);
			}
			processInitialArguments(input);
			
		}
		else if(argument.equals("create")) {
			String username=promptUsername(input);
			String password=promptPassword(input);
			User user=setUpUser(username, password);
			if(user==null) {
				System.out.println("There was an error in creating your profile, try again");
			}
			else {
				System.out.println("You have successfully created your profile with username: "+user.getUsername());
			}
			processInitialArguments(input);
		}
		else if(argument.equals("quit")) {
			
		}
		else {
			System.out.println("Invalid argument");
			processInitialArguments(input);
		}
	}
	
	
	public static void main(String[] args) {
		Bank bankOfAmerica = new Bank("Bank of America");
		Scanner input =new Scanner(System.in);
	    bankOfAmerica.processInitialArguments(input);

	}

}
