package in.conceptarchitect.banking.specs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import in.conceptarchitect.banking.Bank;
import in.conceptarchitect.banking.CurrentAccount;
import in.conceptarchitect.banking.OverdraftAccount;
import in.conceptarchitect.banking.Response;
import in.conceptarchitect.banking.ResponseStatus;
import in.conceptarchitect.banking.SavingsAccount;
import in.conceptarchitect.banking.exceptions.InsufficientBalanceException;
import in.conceptarchitect.banking.exceptions.InvalidAccountException;
import in.conceptarchitect.banking.exceptions.InvalidAccountTypeException;
import in.conceptarchitect.banking.exceptions.InvalidAmountException;
import in.conceptarchitect.banking.exceptions.InvalidCredentialsException;

public class BankSpecs {
	
	final String bankName="ICICI";
	final double rate=12;
	final String correctPassword="p@ss";
	final double initialBalance=50000;
	
	int savingsAccountNumber, currentAccountNumber,odAccountNumber;
	int initialTotalAccounts;
	Bank bank;
	BankAsserts bankAsserts;
	
	@Before
	public void arrange() {
		//ARRANGE
		bank=new Bank(bankName,rate);
		savingsAccountNumber=bank.openAccount("savings","Name1", correctPassword, initialBalance);
		currentAccountNumber=bank.openAccount("current","Name", correctPassword, initialBalance);
		odAccountNumber=bank.openAccount("overdraft", "Name1", correctPassword,initialBalance);
		bankAsserts=new BankAsserts(bank, correctPassword, initialBalance);
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
	public void openAccountShouldTakeAccountTypeNamePasswordAndBalanceAndReturnAccountNumber() {
		
		//ACT
		int accountNumber1 = bank.openAccount("savings", "Aman", "mypassword", 1000.0);
		
		// ASSERT
		assertTrue(accountNumber1 > 0);
	}
	
	
	@Test		
	public void openAccountShouldReturnUniqueAccountNumber() {
		
		
		// ACT 
		var accountNumber1 = bank.openAccount("savings","aman", "mypassword", 1000.0 );
		var accountNumber2 = bank.openAccount("savings","arpit", "hispassword", 2000.0 );
		
		// ASSERT
		assertNotEquals(accountNumber1, accountNumber2);
	}
	
	
	@Test
	public void openAccountShouldIncreaseTotalAccountCountInTheBank() {
		
		
		// ACT 
		var accountNumber1 = bank.openAccount("savings","aman", "mypassword", 1000.0 );
		
		
		// ASSERT
		assertEquals(initialTotalAccounts+1, bank.getAccountCount());
	}
	
	
	@Test
	public void openAccountShouldSuccessfullyCreateSavingsAccount() {
	
		var accountNumber= bank.openAccount("savings", "Name1", correctPassword, initialBalance);
		
		var account=bank.getAccount(accountNumber, correctPassword);
		
		assertTrue("Account is Not Savings Account",account instanceof SavingsAccount);
		
	}
	
	
	@Test
	public void openAccountShouldSuccessfullyCreateCurrentAccount() {
		var accountNumber= bank.openAccount("current", "Name1", correctPassword, initialBalance);
		
		var account=bank.getAccount(accountNumber, correctPassword);
		
		
		assertTrue("Account is not current Account", account instanceof CurrentAccount);
	}
	
	
	
	@Test
	public void openAccountShouldSuccessfullyCreateOverdraftAccount() {
		var accountNumber= bank.openAccount("overdraft", "Name1", correctPassword, initialBalance);
		
		var account=bank.getAccount(accountNumber, correctPassword);
		
		
		assertTrue( account instanceof OverdraftAccount);
	}
	
	
	
	
	@Test(expected = InvalidAccountTypeException.class)
	public void openAccountShouldFailForInvalidAccountType() {
		var result=bank.openAccount("unknown type","Name",correctPassword,initialBalance);
		assertEquals(initialTotalAccounts, bank.getAccountCount());
	}
	
	@Test (expected = InvalidAccountException.class)
	public void closeAccountShouldFailFromInvalidAccountNumber() {
		
		//ACT
		bank.closeAccount(initialTotalAccounts+1, "any-password");
		
		
		
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void closeAccountShouldFailFromInvalidAccountPassword() {
		//ACT
	
		bank.closeAccount(savingsAccountNumber, "wrong-password");
		
		
	}
	
	@Test(expected = InsufficientBalanceException.class)
	public void closeAccountShouldFailForNegativeBalance() {
		//Arrange ---> make sure you have a negative balance before starting
		bank.withdraw(odAccountNumber, initialBalance+1, correctPassword);
		//Is this withdrawal success	
		//assertEquals(-1, bank.getAccount(odAccountNumber,correctPassword).getBalance(),0);
		var balance= bank.getAccount(odAccountNumber, correctPassword).getBalance();
		assumeTrue("Account doesn't meet precondition of  negative balance", balance<0);
		
		//ACT
	bank.closeAccount(odAccountNumber, correctPassword);
			
		
	}
	
	
	@Test
	public void closeAccountShouldCloseTheAccountWithRightCredentials() {
		//ACT
		var result = bank.closeAccount(savingsAccountNumber, correctPassword);
		
		//ASSERT
		assertNotEquals(-1, result,0);
	}
	
	
	
	@Test
	public void closeAccountShouldReturnBalanceOnSuccessfulClosure() {
		
		//ACT
		var result= bank.closeAccount(savingsAccountNumber, correctPassword);
		//ASSERT
		assertEquals(initialBalance, result,0.01);
		
		
	}
	
	@Test
	public void closeAccountShouldReduceTheAccountCountInTheBank() {
		//ACT
		var result= bank.closeAccount(savingsAccountNumber, correctPassword);
		
		//ASSERT
		assertEquals(initialTotalAccounts-1, bank.getAccountCount());
	}
	
	@Test(expected = InvalidAccountException.class)
	public void closeShouldFailForAlreadyClosedAccount() {
		
		//ARRANGE
		bank.closeAccount(savingsAccountNumber, correctPassword);
		//Now existingAccount1 is closed. It can't be closed again
		
		//ACT
		bank.closeAccount(savingsAccountNumber, correctPassword);		
		

		
	}
	
	@Test
	public void accountNumberShouldNotBeReused() {
		//ARRANGE
		
		String a4Name="Account "+(initialTotalAccounts+1);
		String a5Name="Account "+(initialTotalAccounts+2);
		bank.openAccount("savings",a4Name, correctPassword, initialBalance); //4
		bank.openAccount("savings",a5Name, correctPassword, initialBalance); //5
		
		bank.closeAccount(3, correctPassword); //we closed account 3
		
		//ACT
		
		var accountNumber= bank.openAccount("savings",a5Name, correctPassword, initialBalance);
		
		
		//ASSERT
		//This is the third account we created after initial setup
		assertEquals(initialTotalAccounts+3,accountNumber);
		
		var account4= bank.getAccount(4,correctPassword);
		
		assertEquals(a4Name, account4.getName());
		
		
		
		
		
	}
	
	
	
	
	@Test(expected = InvalidAccountException.class)
	public void weShouldNotBeAbleToGetClosedAccount() {
		var amount=bank.closeAccount(currentAccountNumber, correctPassword);
		assertTrue(amount==initialBalance); //our account is indeed closed
		bank.getAccount(currentAccountNumber, correctPassword);
		
	}
	
	
	@Test
	public void creditInterstShouldCreditInterstToSavingsAccounts() {
		bank.creditInterest();
		double expectedBalance= initialBalance*(1+ rate/1200);
		bankAsserts.assertBalance(savingsAccountNumber, expectedBalance);
		
	}
	
	@Test
	public void creditInterstShouldCreditInterstToOverdraftAccounts() {
		bank.creditInterest();
		double expectedBalance= initialBalance*(1+ rate/1200);		
		bankAsserts.assertBalance(odAccountNumber, expectedBalance);
		
	}
	
	@Test
	public void creditInterstShouldNotCreditInterstToCurrentAccounts() {
		bank.creditInterest();				
		bankAsserts.assertBalanceUnchanged(currentAccountNumber);
		
	}
	
	
	@Test(expected = InvalidAccountException.class)
	public void creditInterstShouldNotCreditInterstToClosedAccount() {
		
		bank.closeAccount(currentAccountNumber, correctPassword);
		bank.getAccount(currentAccountNumber, correctPassword);
		bank.creditInterest();				
		
		//no crash is good for now.
		
	}
	
	
	@Test
	public void getBalanceShouldReturnBalanceForCorrectAccountAndPassword() {
		double balance=bank.getBalance(savingsAccountNumber, correctPassword);
		
		assertEquals(initialBalance,balance, 0.01);
	}
	
	@Test(expected = InvalidAccountException.class)
	public void getBalanceShouldFailForNegativeAccountNumber() {
		bank.getBalance(-1, correctPassword);
		
	}
	
	
	@Test (expected = InvalidAccountException.class)
	public void getBalanceShouldFailForInvalidAccountNumber() {
		double balance=bank.getBalance(initialTotalAccounts+1, correctPassword);
		assertEquals(-1, balance,0);
				
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void getBalanceShouldFailForInvalidPassword() {
		double balance=bank.getBalance(savingsAccountNumber,"not"+ correctPassword);
		
	}
	
	@Test(expected = InvalidAccountException.class)
	public void getBalanceShouldFailForClosedAccount() {
		//Arrange
		bank.closeAccount(savingsAccountNumber, correctPassword);
		//Act
		double balance=bank.getBalance(savingsAccountNumber, correctPassword);
		//Assert
		assertEquals(-1, balance,0);
	}
	
	
	
	@Test(expected = InvalidAccountException.class)
	public void depositShouldFailForInvalidAccountNumber() {
		 bank.deposit(initialTotalAccounts+1, 20000);
		
		//assertFalse(result);
	}
	
	
	@Test(expected = InvalidAmountException.class)
	public void depositShouldFailForInvalidAmount() {
	
		bank.deposit(savingsAccountNumber, -1);
		
	}
	
	
	@Test
	public void depositShouldCreditBalanceOnSuccess() {
	
	    bank.deposit(savingsAccountNumber, 1);
		bankAsserts.assertBalance(savingsAccountNumber, initialBalance+1);
	}
	
	@Test(expected = InvalidAccountException.class)
	public void withdrawShouldFailForInvalidAccountNumber() {
	 bank.withdraw(initialTotalAccounts+1, 1, correctPassword);

		
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void withdrawShouldFailForInvalidPassword() {
	
		bank.withdraw(savingsAccountNumber, 1, "not-"+correctPassword);
		bankAsserts.assertBalanceUnchanged(savingsAccountNumber);
	}
	
	@Test (expected = InvalidAmountException.class)
	public void withdrawShouldFailForInvalidAmount() {
		bank.withdraw(currentAccountNumber, -1, correctPassword);
		bankAsserts.assertBalanceUnchanged(savingsAccountNumber);
	}
	
	
	
	@Test(expected = InvalidAmountException.class)
	public void withdrawShouldFailForAmountExceedMinBalanceInSavingsAccount() {
		SavingsAccount account=(SavingsAccount) bank.getAccount(savingsAccountNumber, correctPassword);
		bank.withdraw(savingsAccountNumber, initialBalance-account.getMinBalance()+1, correctPassword);
		bankAsserts.assertBalanceUnchanged(savingsAccountNumber);
	}
	
	@Test(expected = InvalidAmountException.class)
	public void withdrawShouldFailForOverDraftForCurrentAccount() {
		var toWithdraw= initialBalance + 1;
		
	   bank.withdraw(currentAccountNumber, toWithdraw, correctPassword);
		bankAsserts.assertBalanceUnchanged(currentAccountNumber);
		
	}
	
	@Test(expected = InvalidAmountException.class)
	public void withdrawShouldFailForAmountOverBalancePlusOdLimitForOdAccount() {
		var toWithdraw= initialBalance*1.1 + 1;
		
		bank.withdraw(odAccountNumber, toWithdraw, correctPassword);
		bankAsserts.assertBalanceUnchanged(odAccountNumber);
	}
	
	
	
	@Test
	public void withdrawShouldReduceBalanceByAmountOnSuccess() {
		
		bank.withdraw(savingsAccountNumber, 1, correctPassword);
		bankAsserts.assertBalance(savingsAccountNumber, initialBalance-1);
		
	}
	
	
	@Test(expected = InvalidAccountException.class)
	public void transferShouldFailForInvalidSourceAccountNumber() {
		bank.transfer(initialTotalAccounts+1, 1, correctPassword, savingsAccountNumber);
		bankAsserts.assertBalanceUnchanged(savingsAccountNumber);
		
		
	}
	
	@Test(expected = InvalidAccountException.class)
	public void transferShouldFailForInvalidTargetAccountNumber() {
		
		bank.transfer(savingsAccountNumber, 1, correctPassword, initialTotalAccounts+1);
		bankAsserts.assertBalanceUnchanged(savingsAccountNumber);
		
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void transferShouldFailForInvalidPassword() {
		
		bank.transfer(currentAccountNumber, 1, "not-"+correctPassword, savingsAccountNumber);
		bankAsserts.assertBalanceUnchanged(savingsAccountNumber);
		bankAsserts.assertBalanceUnchanged(currentAccountNumber);
		
	}
	
	@Test (expected = InvalidAmountException.class)
	public void transferShouldFailForInvalidAmount() {
		
		bank.transfer(currentAccountNumber, -1, correctPassword, savingsAccountNumber);
		bankAsserts.assertBalanceUnchanged(savingsAccountNumber);
		bankAsserts.assertBalanceUnchanged(currentAccountNumber);
		
	}
	
	@Test (expected = InvalidAmountException.class)
	public void transferShouldFailForOverDraft() {
		bank.transfer(currentAccountNumber, initialBalance+1, correctPassword, savingsAccountNumber);
		bankAsserts.assertBalanceUnchanged(savingsAccountNumber);
		bankAsserts.assertBalanceUnchanged(currentAccountNumber);
		
		
	}
	
	@Test
	public void transferShouldReduceBalanceInSourceAccountOnSuccess() {
		
		bank.transfer(currentAccountNumber, 1, correctPassword, savingsAccountNumber);			
		bankAsserts.assertBalance(currentAccountNumber,initialBalance-1);
	}
	
	@Test
	public void transferShouldIncreseBalanceInTargetAccountOnSuccess() {
		
		bank.transfer(currentAccountNumber, 1, correctPassword, savingsAccountNumber);			
		bankAsserts.assertBalance(savingsAccountNumber,initialBalance+1);
	}
	
	
	
	
	@Test
	public void transferShouldAllowOverDraftFromOverDraftAccount() {
		
		double od=1000;
		double amountTransferred=initialBalance+od;
		double finalBalance =  - od - od/100;
		
		bank.transfer(odAccountNumber, amountTransferred, correctPassword, savingsAccountNumber);
		bankAsserts.assertBalance(odAccountNumber, finalBalance);
		bankAsserts.assertBalance(savingsAccountNumber, initialBalance+amountTransferred);
		
	}
	
	

	@Test()
	public void canChangePasswordWithValidCurrentPassword() {
		var newPassword="newPassword";
	
		bank.changePassword(savingsAccountNumber, correctPassword, newPassword);
		var balance=bank.getBalance(savingsAccountNumber, newPassword);
		assertEquals(initialBalance,balance,0);
		
	}
	
	@Test (expected = InvalidCredentialsException.class)
	public void changePasswordFailsForInvalidCurrentPassword() {
		String newPassword="new password";
		bank.changePassword(savingsAccountNumber, "not"+correctPassword, newPassword);
		//how do I know password is actually changed
	}
	
	
	@Test (expected = InvalidAccountException.class)
	public void changePasswordFailsForInvalidAccountNumber() {
		String newPassword="new password";
		bank.changePassword(-1, "not"+correctPassword, newPassword);
		
		//how do I know password is actually changed
	}
	
	
	 
	

}
