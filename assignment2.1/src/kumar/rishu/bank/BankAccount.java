package kumar.rishu.bank;

public class BankAccount {
	String name ;
	int accountNumber ;
	int balance ;
	String password ;
	float interestRate;
	
	BankAccount(String name, int AccNo, String pass, int balance, float intrst){
		this.name = name;
		this.accountNumber = AccNo;
		this.balance = balance;
		this.interestRate = intrst;
		this.password = pass;
	}
	
	public void deposit(int amount) {
		if(amount<=0) {
			System.out.println("Invalid Amount");
			return;
		}
		else {
			this.balance += amount;
			System.out.println(amount+" credited Total balance is "+ this.balance);
		}
		return;
	}
	
	public void withdraw(int amount, String pass) {
		if(pass != this.password) {
			System.out.println("Not Authorised");
			return;
		}
		else {
			if(amount<=0 || amount > this.balance) {
				System.out.println("Invalid Amount");
				return;
			}
			else {
				this.balance -= amount;
				System.out.println("remaining balance is "+this.balance);
			}
		}
	}
	
	public void getName() {
		System.out.println("Name is"+ this.name);
	}
	
	public void getAccountNumber() {
		System.out.print("Account Number is"+ this.accountNumber);
	}
	
	public void getBalance() {
		System.out.println("balance is "+ this.balance);
	}
	
}
