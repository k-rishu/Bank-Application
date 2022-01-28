package in.conceptarchitect.banking;

import in.conceptarchitect.banking.exceptions.InsufficientBalanceException;
import in.conceptarchitect.banking.exceptions.InvalidAccountException;
import in.conceptarchitect.banking.exceptions.InvalidAccountTypeException;

public class Bank {

	
	private String name;
	private double rate;
	

	public Bank(String name, double rate) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.rate=rate;
	}

	public String getName() { 
		// TODO Auto-generated method stub
		return name;
	}

	public double getRate() {
		// TODO Auto-generated method stub
		return rate;
	}
	
	int accountCount=0;	
	int lastAccountNumber=0;
	BankAccount [] accounts=new BankAccount[100];


	public int openAccount(String accountType,String name, String password,  double amount) {
		// TODO Auto-generated method stub
		
		BankAccount account=null;
		if(accountType.equals("savings"))
				account= new SavingsAccount(0, name,password,amount);
		else if(accountType.equals("current"))
				account=new CurrentAccount(0,name,password,amount);
		else if(accountType.equals("overdraft"))
				account=new OverdraftAccount(0, name, password, amount);
		
		if(account==null)
				throw new InvalidAccountTypeException(0,"Invalid Account Type :"+accountType);
		
		return addAccount(account);
	}

	private int addAccount(BankAccount account) {
		int accountNumber= ++lastAccountNumber;
		accountCount++;
		account.setAccountNumber(accountNumber);
		accounts[accountNumber]=account;
		return accountNumber;
	}
	
	private BankAccount getAccount(int accountNumber) {
		if(accountNumber<1 || accountNumber>lastAccountNumber || accounts[accountNumber]==null)
			throw new InvalidAccountException(accountNumber);

		//it either returns a valid value or throws an exception
		return accounts[accountNumber];
	}
	
	public BankAccount getAccount(int accountNumber, String password) {
		var account=getAccount(accountNumber);
		account.authenticate(password);
		return account;
	}
	
	
	public double closeAccount(int accountNumber, String password) {
		// TODO Auto-generated method stub
		var account=getAccount(accountNumber,password);
		
		if(account.getBalance()<0)
			throw new InsufficientBalanceException(accountNumber,-account.getBalance());
		
		accounts[accountNumber]=null;
		accountCount--;
		return account.getBalance();
	}

	public int getAccountCount() {
		// TODO Auto-generated method stub
		return accountCount;
	}

	

	public void deposit(int accountNumber, double amount) {
		// TODO Auto-generated method stub
		var account=getAccount(accountNumber);		
		account.deposit(amount);
		
	}

	public void withdraw(int accountNumber, double amount, String password) {
		// TODO Auto-generated method stub
		var account=getAccount(accountNumber);
		account.withdraw(amount, password);
		
	}

	public void transfer(int accountNumber, double amount, String password, int targetAccount) {
		// TODO Auto-generated method stub
		var source= getAccount(accountNumber);
		var target=getAccount(targetAccount);
			
		source.withdraw(amount, password);
		target.deposit(amount);
	}

	public void creditInterest() {
		// TODO Auto-generated method stub
		for(var i=1;i<=lastAccountNumber;i++) {
			var account=accounts[i]; //may be null for closed account
			if(account!=null)
				account.creditInterest(rate);
		}
	}

	public double getBalance(int accountNumber, String password) {
		// TODO Auto-generated method stub
		var account=getAccount(accountNumber);
		account.authenticate(password);
		return account.getBalance();
		
	}

	
	public void changePassword(int accountNumber, String currentPassword, String newPassword) {
		var account=getAccount(accountNumber);
		account.changePassword(currentPassword, newPassword);
		
	}

	public String[] getAllAccountsInfo() {
		// TODO Auto-generated method stub
		String [] info= new String [ accountCount];
		var x=0;
		
		for(var account : accounts) {
			if(account!=null) {
				
				info[x]=String.format("%d\t%s\t%f", account.getAccountNumber(),account.getName(),account.getBalance());
				x++;
			}
		}
		
		return info;
		
	}
	 

}















