package in.conceptarchitect.banking;

import in.conceptarchitect.utils.Input;
import in.conceptarchitect.utils.encryption.Encrypt;

public class BankAccount {
	
	
	private String name; //account holder name
	private int accountNumber;
	static private double interestRate;
	private double balance;
	private String password;
	private String type;
	
	static {
		interestRate=12;
	}
	
	public BankAccount(int accountNumber, String name,String password, double amount, String type) {
		this.accountNumber=accountNumber;
		this.name=name;
		//this.password=password;
		setPassword(password);
		balance=amount;  //this keyword is option here
		this.type = type;
				
	}
	
	public int deposit(int amount) {
		
		if(amount>0) {
			balance+=amount;
			//System.out.println("Amount is deposited");
			return (int) balance;
		} else {
			//System.out.println("Invalid amount. Deposit Failed");
			return -1;
		}		
	}
	
	
	
	public Response withdraw(double amount,String password) {
		
		if(!this.authenticate(password))
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Credentials");
		if(amount<0)
			return new Response(ResponseStatus.INVALID_AMOUNT,"Enter Positive Amount");
		if(amount>balance)
			return new Response(ResponseStatus.INSUFFICIENT_FUNDS,"Insufficient Funds");
		
		this.balance -= amount;
		return new Response(ResponseStatus.SUCCESS,null);
	}
	
	
	public void creditInterest() {
		//credits one month interest to the account
		balance+= (balance*interestRate/1200);
	}
	
	
	public String getName() {return name;}	
	public void setName(String name) {this.name=name;}
	
	public int getAccountNumber() {return accountNumber;}

	// can't change the bank account
	//public void setAccountNumber(int accountNumber) {this.accountNumber=accountNumber; }
	
	
	public double getInterestRate() {
		return interestRate;
	}

	public static void setInterestRate(double interestRate) {
		BankAccount.interestRate = interestRate;
	}

	public double getBalance(String password) {
		if(authenticate(password)) {
			return balance;
		}
		else return -1;
		
	}

//  You can't change the balance directly
//	public void setBalance(double balance) {
//		this.balance = balance;
//	}


//  You shouldn't allow get password
//  Perhaps get password may not make any sense	
//	public String getPassword() {
//		return password;
//	}
//
	private void setPassword(String password) {
		this.password =Encrypt.instance.encrypt( password,10);
		
	}
	
	public boolean authenticate(String password) {
		return Encrypt.instance.match(this.password, password, 10);
	}
	
	
	public boolean changePassword(String oldPassword, String newPassword) {
		if(this.authenticate(oldPassword)) {
			setPassword(newPassword);
			return true;
		} else {
			return false;
		}
	}
	
	
	

	public String info() {
		//System.out.println(accountNumber+"\t"+name+"\t"+balance);
		return String.format("%d\t%s\t%f\t%f\n",accountNumber,name,balance,interestRate);
	}

	public double deposit(double amount) {
		// TODO Auto-generated method stub
		this.balance+=amount;
		return this.balance;
		
	}
//	public void setBalance(double amount) {
//		this.balance = amount;
//	}

	public Response moneyTransfer(BankAccount fromAcc, String password, BankAccount toAcc, int amount) {
		// TODO Auto-generated method stub
		if(!fromAcc.authenticate(password))
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Password");
		
		if(fromAcc.balance > amount ) {
			fromAcc.balance -= amount;
			toAcc.balance += amount;
			return new Response(ResponseStatus.SUCCESS,"Transfered Succesfully");
		}
		else
			return new Response(ResponseStatus.INSUFFICIENT_FUNDS,"Insufficient Funds");
	}

	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	
	public String showAccountDetails() {
		return "Name: "+getName()+"\n"+
			   "Account No: "+getAccountNumber()+"\n";
			   
	}
	

}
