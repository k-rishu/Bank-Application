package in.conceptarchitect.banking;

import in.conceptarchitect.utils.Input;
import in.conceptarchitect.utils.encryption.Encrypt;

public class BankAccount {
	
	private String type;
	private String name; //account holder name
	private int accountNumber;
	
	protected double balance;
	private String password;
	
	
	
	public BankAccount(int accountNumber, String name,String password, double amount) {
		this.accountNumber=accountNumber;
		this.name=name;
		//this.password=password;
		setPassword(password);
		balance=amount;  //this keyword is option here
		
				
	}
	
	public double deposit(double deposit) {
		
		if(deposit>0) {
			balance+=deposit;
			//System.out.println("Amount is deposited");
			return balance;
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
		if(amount>getMaxWithdrawableAmount(password))
			return new Response(ResponseStatus.INSUFFICIENT_FUNDS,"Insufficient Funds");
		
		balance-=amount;
		return new Response(ResponseStatus.SUCCESS,null);
	}
	
	
	
	public double getMaxWithdrawableAmount(String password) {
		// TODO Auto-generated method stub
		return balance;
	}

	public void creditInterest(double interestRate) {
		//credits one month interest to the account
		balance+= (balance*interestRate/1200);
	}
	
	
	public String getName() {return name;}	
	public void setName(String name) {this.name=name;}
	
	public int getAccountNumber() {return accountNumber;}

	// can't change the bank account
	//public void setAccountNumber(int accountNumber) {this.accountNumber=accountNumber; }
	
	

	public double getBalance(String password) {
		if(!this.authenticate(password))
			return -1;
		return balance;
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
	
	public String getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	
	public String showAccountDetails() {
		return "Name: "+getName()+"\n"+
			   "Account No: "+getAccountNumber()+"\n";
			   
	}

	public String info() {
		//System.out.println(accountNumber+"\t"+name+"\t"+balance);
		return String.format("%d\t%s\t%f\n",accountNumber,name,balance);
	}
	
	
	public Response moneyTransfer(BankAccount fromAcc, String password, BankAccount toAcc, int amount) {
		// TODO Auto-generated method stub
		if(!fromAcc.authenticate(password))
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Password");
		
		if(fromAcc.balance > amount ) {
			//fromAcc.balance -= amount;
			fromAcc.withdraw(amount, password);
			//toAcc.balance += amount;
			toAcc.deposit(amount);
			return new Response(ResponseStatus.SUCCESS,"Transfered Succesfully");
		}
		else
			return new Response(ResponseStatus.INSUFFICIENT_FUNDS,"Insufficient Funds");
	}
	
	
	

}
