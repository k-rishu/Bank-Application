package kumar.rishu.bank.test;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import kumar.rishu.bank.Bank;
import kumar.rishu.bank.BankAccount;

public class BankTest {
	
	Bank boi = new Bank("BOI", 12);
	
	int temp = boi.openAccount("rishu", "rishu123", 2000);
	int t1 = boi.openAccount("abhay", "abhay123", 2000);
	
	BankAccount a1 = boi.getAccount(temp);
	BankAccount a2 = boi.getAccount(t1);
	
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
		String result = a1.withdraw(500, "rishu123");
		assertEquals("Amount Withdrawn", result);
	}
	
	@Test
	public void cantWithdrawWithInvalidPassword(){
		String result = a1.withdraw(500, "rishu@123");
		assertEquals("Not Authorised", result);
	}
	
	@Test
	public void withdrawlAmountShouldBeLessThanBalance(){
		String result = a1.withdraw(500, "rishu123");
		assertEquals("Amount Withdrawn", result);
	}
	
	@Test
	public void withdrawlAmountCantBeGreaterThanBalance(){
		String result = a1.withdraw(30000, "rishu123");
		assertEquals("Invalid Amount", result);
	}
	
	@Test
	public void cantChangePasswordWithoutOldOne(){
		String result = a1.changePassword("rishi123","rishu@123");
		assertEquals("Wrong Current Password.", result);
	}
	
	@Test
	public void canChangePasswordWithOldOne(){
		String result = a1.changePassword("rishu123","rishu@123");
		assertEquals("Password Successfully updated", result);
	}
	
	@Test
	public void checkPasswordWillReturnTrueForValidPassword() {
		boolean result = a1.checkPassword("rishu123");
		assertEquals(true, result);
	}
	
	@Test
	public void checkPasswordWillReturnFalseForValidPassword() {
		boolean result = a1.checkPassword("p@ss#1");
		assertEquals(false, result);
	}
	
	@Test
	public void canTransferMoneyIfAccountBalanceIsGreaterThanTransferAmount() {
		String result = a1.moneyTransfer(a1, a2, 500);
		assertEquals("Amount Transfered", result);
	}
	
	@Test
	public void canNotTransferMoneyIfAccountBalanceIsGreaterThanTransferAmount() {
		String result = a1.moneyTransfer(a1, a2, 20000);
		assertEquals("Transfer can not be Completed", result);
	}

}
