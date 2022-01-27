package in.conceptarchitect.banking;

import java.util.Iterator;

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


	public int openAccount(String name, String password, double amount, String type) {
		// TODO Auto-generated method stub
				
		int accountNumber= ++lastAccountNumber;
		accountCount++;
		if(type == "Savings") {
			var account = new SavingsAccount(accountNumber, name, password, amount, type);
			accounts[accountNumber]=account;
		}
		//var account=new BankAccount(accountNumber, name,password,amount);
		else if(type == "Current") {
			var account = new CurrentAccount(accountNumber, name, password, amount, type);
			accounts[accountNumber]=account;
		}
		
		else if(type == "Overdraft") {
			var account = new OverdraftAccount(accountNumber, name, password, amount, type);
			accounts[accountNumber]=account;
		}
		return accountNumber;
	}
	
	
	public double closeAccount(int accountNumber, String password) {
		// TODO Auto-generated method stub
		var account=getAccount(accountNumber,password);
		if(account==null)
			return -1;
		
		accounts[accountNumber]=null;
		accountCount--;
		return account.getBalance(password);
	}

	public int getAccountCount() {
		// TODO Auto-generated method stub
		return accountCount;
	}

	public BankAccount getAccount(int accountNumber, String password) {
		// TODO Auto-generated method stub
		if(accountNumber<1 || accountNumber>lastAccountNumber )
				return null;
		
		var account=accounts[accountNumber];
		if(account==null)
			return null;
		
		if(!account.authenticate(password))
			return null;
		
		return account;
	}

	public double deposit(int accountNumber, double amount) {
		// TODO Auto-generated method stub
		if(accountNumber<1 || accountNumber>lastAccountNumber||amount<=0 )
			return -1;
		var account=accounts[accountNumber];
		if(account==null)
			return -1;
		
		var result = account.deposit(amount);
		
		return result;
		
	}

	public Response withdraw(int accountNumber, double amount, String password) {
		// TODO Auto-generated method stub
		if(accountNumber<1 || accountNumber>lastAccountNumber || amount<=0)
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		var account=accounts[accountNumber];
		if(account==null)
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		if(account.getType() == "Overdraft")
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		Response result = account.withdraw(amount, password);
		
		return result;
	}

	public Response transfer(int accountNumber, int amount, String password, int targetAccount) {
		// TODO Auto-generated method stub
		if(accountNumber<1 || accountNumber>lastAccountNumber || amount<=0)
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		if(targetAccount<1 || targetAccount>lastAccountNumber )
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		var account1=accounts[accountNumber];
		if(account1==null)
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		var account2=accounts[accountNumber];
		if(account2==null)
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		if(account1.getType() == "Overdraft")
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		Response result = account1.moneyTransfer(account1, password, account2, amount);
		
		return result;
	}

	public void creaditIntrest(double rate) {
		// TODO Auto-generated method stub
		for(BankAccount a : accounts) {
			a.creditInterest(rate);
		}
	}

	public double getBalance(int accountNumber, String password) {
		// TODO Auto-generated method stub
		if(accountNumber<1 || accountNumber>lastAccountNumber )
			return -1;
		var account=accounts[accountNumber];
		if(account==null)
			return -1;
		
		if(!account.authenticate(password))
			return -1;
		
		return account.getBalance(password);
		
		
	}

	public void viewAllAccounts() {
		// TODO Auto-generated method stub
		for(BankAccount a:accounts) {
			a.showAccountDetails();
		}
	}

	

}















