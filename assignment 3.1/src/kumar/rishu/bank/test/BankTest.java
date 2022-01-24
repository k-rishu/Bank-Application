package kumar.rishu.bank.test;
import kumar.rishu.bank.BankAccount;
import static org.junit.Assert.*;

import org.junit.Test;

public class BankTest {
	
	BankAccount a1 = new BankAccount("aman", 124, "aman@123", 23344, 12);
	@Test
	public void depositAmountShouldNotBeLessorEqualsToZero(){
		String result = a1.deposit(-5);
		assertEquals("Invalid Amount", result);
	}
	
	@Test
	public void depositAmountShouldBeGreaterThanZero(){
		String result = a1.deposit(5000);
		assertEquals("Amount deposited", result);
	}
	
	@Test
	public void withdrawWithValidPassword(){
		String result = a1.withdraw(500, "aman@123");
		assertEquals("Amount Withdrawn", result);
	}
	
	@Test
	public void cantWithdrawWithInvalidPassword(){
		String result = a1.withdraw(500, "aman123");
		assertEquals("Not Authorised", result);
	}
	
	@Test
	public void withdrawlAmountShouldBeLessThanBalance(){
		String result = a1.withdraw(500, "aman@123");
		assertEquals("Amount Withdrawn", result);
	}
	
	@Test
	public void withdrawlAmountCantBeGreaterThanBalance(){
		String result = a1.withdraw(30000, "aman@123");
		assertEquals("Invalid Amount", result);
	}
	
	@Test
	public void cantChangePasswordWithoutOldOne(){
		String result = a1.changePassword("aman123","rishu@123");
		assertEquals("Wrong Current Password.", result);
	}
	
	@Test
	public void canChangePasswordWithoutOldOne(){
		String result = a1.changePassword("aman@123","rishu@123");
		assertEquals("Password Successfully updated", result);
	}
	
	

}
