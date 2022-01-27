package in.conceptarchitect.banking.specs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import in.conceptarchitect.banking.Bank;
import in.conceptarchitect.banking.Response;

public class BankSpecs {
	
	final String bankName="ICICI";
	final double rate=12;
	final String correctPassword="p@ss";
	final double initialBalance=50000;
	final String type = "Saving";
	int existingAccount1, existingAccount2;
	int initialTotalAccounts;
	Bank bank;
	
	@Before
	public void arrange() {
		//ARRANGE
		bank=new Bank(bankName,rate);
		existingAccount1=bank.openAccount("Name1", correctPassword, initialBalance, "Savings");
		existingAccount2=bank.openAccount("Name", correctPassword, initialBalance, "Savings");
		initialTotalAccounts=bank.getAccountCount();
	}
	
	
	@Test
	public void bankShouldHaveAName() {
				
		//ACT
		Bank bank=new Bank(bankName,10);
		
		//ASSERT
		assertEquals(bankName, bank.getName());
		
	}
	
	
	@Test
	public void bankShouldHaveAInterestRAte() {
		
		//ACT
		Bank bank=new Bank("Some Name",rate);
		//ASSERT
		assertEquals(rate, bank.getRate(),0);
				
		
	}
	
	@Test
	public void openAccountShouldTakeNamePasswordAndBalanceAndReturnAccountNumber() {
		
		//ACT
		int accountNumber1 = bank.openAccount("Aman", "mypassword", 1000.0,"Current");
		
		// ASSERT
		assertTrue(accountNumber1 > 0);
	}
	
	
	@Test		
	public void openAccountShouldReturnUniqueAccountNumber() {
		
		
		// ACT 
		var accountNumber1 = bank.openAccount("aman", "mypassword", 6000.0,"Savings" );
		var accountNumber2 = bank.openAccount("arpit", "hispassword", 7000.0,"Savings" );
		
		// ASSERT
		assertNotEquals(accountNumber1, accountNumber2);
	}
	
	
	@Test
	public void openAccountShouldIncreaseTotalAccountCountInTheBank() {
		
		
		// ACT 
		var accountNumber1 = bank.openAccount("aman", "mypassword", 1000.0,"Saving" );
		
		
		// ASSERT
		assertEquals(initialTotalAccounts+1, bank.getAccountCount());
	}
	
	
	
	@Test
	public void closeAccountShouldFailFromInvalidAccountNumber() {
		
		//ACT
		var result = bank.closeAccount(initialTotalAccounts+1, "any-password");
		
		assertEquals(-1, result,0);
		
		
	}
	
	@Test
	public void closeAccountShouldFailFromInvalidAccountPassword() {
		//ACT
		var result = bank.closeAccount(existingAccount1, "wrong-password");
		
		assertEquals(-1, result,0);
		
	}
	
	
	
	@Test
	public void closeAccountShouldCloseTheAccountWithRightCredentials() {
		//ACT
		var result = bank.closeAccount(existingAccount1, correctPassword);
		
		//ASSERT
		assertNotEquals(-1, result,0);
	}
	
	
	
	@Test
	public void closeAccountShouldReturnBalanceOnSuccessfulClosure() {
		
		//ACT
		var result= bank.closeAccount(existingAccount1, correctPassword);
		//ASSERT
		assertEquals(initialBalance, result,0.01);
		
		
	}
	
	@Test
	public void closeAccountShouldReduceTheAccountCountInTheBank() {
		//ACT
		var result= bank.closeAccount(existingAccount1, correctPassword);
		
		//ASSERT
		assertEquals(initialTotalAccounts-1, bank.getAccountCount());
	}
	
	@Test
	public void closeShouldFailForAlreadyClosedAccount() {
		
		//ARRANGE
		bank.closeAccount(existingAccount1, correctPassword);
		//Now existingAccount1 is closed. It can't be closed again
		
		//ACT
		var result=bank.closeAccount(existingAccount1, correctPassword);		
		
		//ASSERT
		assertEquals(-1, result,0);
		
	}
	
	@Test
	public void accountNumberShouldNotBeReused() {
		//ARRANGE
		String a4Name="Account 4";
		String a5Name="Account 5";
		bank.openAccount("Name", correctPassword, initialBalance,"Savings"); //3
		bank.openAccount(a4Name, correctPassword, initialBalance,"Savings"); //4
		
		bank.closeAccount(3, correctPassword); //we closed account 3
		
		//ACT
		
		var accountNumber= bank.openAccount(a5Name, correctPassword, initialBalance,"Savings");
		
		
		//ASSERT
		assertEquals(5,accountNumber);
		
		var account4= bank.getAccount(4,correctPassword);
		
		assertEquals(a4Name, account4.getName());
		
		
		
		
		
	}
	
	
	
	
	@Test
	public void weShouldNotBeAbleToGetClosedAccount() {
		//ACT
		bank.closeAccount(1, correctPassword);
		var account1 = bank.getAccount(1, correctPassword);
		//ASSERT
		assertEquals(account1, null);
	}
	
	
	
	@Test
	public void getBalanceShouldReturnBalanceForCorrectAccountAndPassword() {
		//ACT
		double result = bank.getBalance(1, correctPassword);
		//ASSERT
		assertEquals(result, initialBalance, 0);
	}

	
	@Test
	public void getBalanceShouldFailForInvalidAccountNumber() {
		//ACT
		double result = bank.getBalance(3, correctPassword);
		//ASSERT
		assertEquals(result, -1, 0);	
	}
	
	@Test
	public void getBalanceShouldFailForInvalidPassword() {
		//ACT
		double result = bank.getBalance(3, "incorrectPassword");
		//ASSERT
		assertEquals(result, -1, 0);	
	}
	
	@Test
	public void depositShouldFailForInvalidAccountNumber() {
		//ACT
		double result = bank.deposit(3, 5000);
		//ASSERT
		assertEquals(result, -1, 0);
	}
	
	@Test
	public void depositShouldFailForInvalidAmount() {
		//ACT
		double result = bank.deposit(3, -5000);
		//ASSERT
		assertEquals(result, -1, 0);
	}
	
	@Test
	public void depositShouldCreditBalanceOnSuccess() {
		//ACT
		double result = bank.deposit(1, 5000);
		//ASSERT
		assertEquals(result, 55000, 0);
	}
	
	@Test
	public void withdrawShouldFailForInvalidAccountNumber() {
		//Act
		Response result = bank.withdraw(3, 5000,correctPassword);
		//ASSERT
		assertEquals(result.getMessage(), "Invalid Credentials");
	}
	
	@Test
	public void withdrawShouldFailForInvalidPassword() {
		//Act
		Response result = bank.withdraw(1, 5000,"IncorrectPassowrd");
		//ASSERT
		assertEquals(result.getMessage(), "Invalid Credentials");
	}
	
	@Test
	public void withdrawShouldFailForInvalidAmount() {
		//Act
		Response result = bank.withdraw(1, -5000,correctPassword);
		//ASSERT
		assertEquals(result.getMessage(), "Invalid Credentials");
	}
	
	
	@Test
	public void withdrawShouldReduceBalanceByAmountOnSuccess() {
		//Act
		var a1 = bank.getAccount(1, correctPassword);
		Response result = bank.withdraw(1, 5000,correctPassword);
		//ASSERT
		assertEquals(initialBalance-5000, a1.getBalance(correctPassword), 0);
	}
	
	
	@Test
	public void transferShouldFailForInvalidSourceAccountNumber() {
		//ACT
		Response result = bank.transfer(3, 5000, correctPassword, 2);
		//ASSERT
		assertEquals(result.getMessage(), "Invalid Credentials");
	}
	
	@Test
	public void transferShouldFailForInvalidPassword() {
		//ACT
		Response result = bank.transfer(1, 5000, "invalidPassword", 2);
		//ASSERT
		assertEquals(result.getMessage(), "Invalid Password");	
	}
	
	@Test
	public void transferShouldFailForInvalidAmount() {
		//ACT
		Response result = bank.transfer(1, 55000, correctPassword, 2);
		//ASSERT
		assertEquals(result.getMessage(), "Insufficient Funds");	
	}

	
	@Test
	public void transferShouldFailForInvalidTargetAccountNumber() {
		//ACT
		Response result = bank.transfer(1, 55000, correctPassword, 3);
		//ASSERT
		assertEquals(result.getMessage(), "Invalid Credentials");	
		
	}
	
	@Ignore
	@Test
	public void transferShouldAddBalanceInTargetOnSuccess() {
		//ACT
		Response result = bank.transfer(1, 5000, correctPassword, 2);
		double remainingBalance = bank.getBalance(2, correctPassword);
		//ASSERT
		System.out.println(result.getMessage());
		assertEquals(55000, remainingBalance, 0);		
	}
	
	 
	@Test
	public void transferShouldFailForOverDraft() {
		//ACT
		var overdraftAccount = bank.openAccount("sup", correctPassword, 2000, "Overdraft");
		Response result = bank.transfer(3, 1000, correctPassword, 1);
		//ASSERT
		assertEquals(result.getMessage(), "Invalid Credentials");

	}
	
	
	@Ignore
	@Test
	public void transferShouldReduceBalanceInSourceAccountOnSuccess() {
		//ACT
		Response result = bank.transfer(1, 5000, correctPassword, 2);
		double remainingBalance = bank.getBalance(1, correctPassword);
		//ASSERT
		System.out.println(result.getMessage());
		assertEquals(45000, remainingBalance, 0);	
	}
	
	@Ignore
	@Test
	public void creditInterstShouldCreditInterstToAllAccounts() {
		//ACT
		System.out.println("account1" + bank.getAccount(1,correctPassword));
		System.out.println("account1" + bank.getAccount(2,correctPassword));

		bank.creaditIntrest(rate);
		double result = (double)(initialBalance + (initialBalance*rate/1200));
		var account1 = bank.getAccount(1, correctPassword);
		var account2 = bank.getAccount(1, correctPassword);
		//ASSERT
		System.out.println("result "+ result+"balance "+account1.getBalance(correctPassword));
		assertEquals(result, account1.getBalance(correctPassword),0);
		assertEquals(result, account2.getBalance(correctPassword),0);
		
		
	}
	
	
	@Test
	public void withdrawShouldFailForOverDraft() {
		//ACT
		var overdraftAccount = bank.openAccount("sup", correctPassword, 2000, "Overdraft");
		Response result = bank.withdraw(3, 1000, correctPassword);
		//ASSERT
		assertEquals(result.getMessage(), "Invalid Credentials");
	}
	
	
	
	
	
	

}
