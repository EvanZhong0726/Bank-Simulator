package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import bank.Account;
import bank.User;

class userTest {

	@Test
	void testExpectedException() {
		assertThrows(IllegalArgumentException.class, ()->{
			User user=new User("","ABCDEFGH");
		});
		assertThrows(IllegalArgumentException.class,()->{
			User user=new User("Evan","");
		});
	}
	@Test
	void testNoException() {
		try {
			User user=new User("Evan","ABCDEFGH");
		}
		catch(Exception e) {
			fail("Error in constructor");
		}
	}
	@Test
	void testOpenAccountOne() {
		User user=new User("Evan","ABCDEFGH");
		Account account=user.openAccount("Saving");
		assertEquals("Evan",account.getUser());
		assertEquals("saving",account.getAccountType());
		assertEquals(0,account.getBalance());
		assertEquals(8,account.getAccountNumber().length());
	}
	@Test
	void testOpenAccountTwo() {
		User user=new User("Evan","ABCDEFGH");
		Account account=user.openAccount("Checking",800);
		assertEquals("Evan",account.getUser());
		assertEquals("checking",account.getAccountType());
		assertEquals(800,account.getBalance());
		assertEquals(8,account.getAccountNumber().length());
	}
	@Test
	void testOpenAccountExceptionOne() {
		User user=new User("Evan","ABCDEFGH");
		assertThrows(IllegalArgumentException.class,()->{
			user.openAccount("sav");
		});
		assertThrows(IllegalArgumentException.class,()->{
			user.openAccount("chec");
		});	
	}
	
	@Test
	void testGetAccount() {
		User user=new User("Evan","ABCDEFGH");
	    user.openAccount("checking",800);
	    user.openAccount("saving",1000);
	    user.openAccount("checking");
	    LinkedList<Account> accounts=user.getAccounts();
	    assertEquals(3,accounts.size());
	}
	
	@Test 
	void testCloseAccount() {
		User user=new User("Evan","ABCDEFGH");
	    Account account = user.openAccount("checking",800);
	    user.closeAccount(account);
	    LinkedList<Account> accounts = user.getAccounts();
	    assertEquals(0, accounts.size());
	}
	@Test
	void testGetSingleAccountOne() {
		User user=new User("Evan","ABCDEFGH");
	    Account accountOne=user.openAccount("checking",800);
	    String accountOneNum=accountOne.getAccountNumber();
	    assertEquals(accountOne,user.getSingleAccount(accountOneNum));
	}
	
	@Test
	void testGetSingleAccountTwo() {
		User user=new User("Evan","ABCDEFGH");
	    Account accountOne=user.openAccount("checking",800);
	    String accountOneNum=accountOne.getAccountNumber();
	    assertThrows(IllegalArgumentException.class, ()->{user.getSingleAccount("");});
	}
	
}

