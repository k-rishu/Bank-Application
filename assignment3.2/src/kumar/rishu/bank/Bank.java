package kumar.rishu.bank;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Bank {

	private String name;
	private static int interest;
	private List<BankAccount> bankAccounts;
	Scanner sc = new Scanner(System.in);
	
	public Bank(String name, int interest) {
		this.name=name;
		Bank.interest = interest;
		bankAccounts = new ArrayList<>();
	}
	
	
	public int openAccount(String name, String password, int ammount) {
		bankAccounts.add(new BankAccount(name, password, ammount));
		int accountNo = bankAccounts.get(bankAccounts.size()-1).getAccountNumber();
		return accountNo;
	}
	
	public BankAccount isValid(int acno, String pass) {
		for(BankAccount account : bankAccounts) {
			if(account.getAccountNumber() == acno && account.checkPassword(pass))
				return account;
		}
		return null;
	}
	
	public BankAccount getAccount(int acno) {
		for(BankAccount a: bankAccounts) {
			if(a.getAccountNumber() == acno)
				return a;
		}
		return null;
	}
	
	public void creditInterest(int acNo) {
		BankAccount a = getAccount(acNo);
		int p = a.knowBalance();
		int newBalance = p+ (p*interest/100);
		a.setBalance(newBalance);
	}
	
	
	public void login() {
		System.out.println("Please Enter your Account No:");
		int accountNo = sc.nextInt();
		System.out.println("Please Enter your password");
		String pass = sc.next();
		BankAccount thisAccount = isValid(accountNo, pass);
		boolean exit = true;
		while(exit) {
			bankHomeScreen();
			int choice = sc.nextInt();
			switch(choice) {
			
			case 1:
				System.out.println(thisAccount.showAccountDetails(pass));
				break;
			case 2:{
				System.out.println("Please Enter the amount to be Deposited: ");
				int depositAmount = sc.nextInt();
				System.out.println(thisAccount.deposit(depositAmount));
				}
				break;
			case 3:{
				System.out.println("Enter the withdrawal amount: ");
				int withdrawalAmount = sc.nextInt();
				System.out.println(thisAccount.withdraw(withdrawalAmount, pass));
				}
				break;
			case 4:{
				System.out.println("Please enter the account number of the receiver: ");
				int acNo = sc.nextInt();
				BankAccount toAccount = getAccount(acNo);
				System.out.println("Enter the Amount");
				int transferAmount = sc.nextInt();
				System.out.println(thisAccount.moneyTransfer(thisAccount, toAccount, transferAmount));
				}
				break;
			case 5:{
				System.out.println("Thanks For Visiting !");
				exit = false;
				}
			default:
				System.out.println("Invalid Input!");
				break;
			}
		}	
	}
	
	
	public void bankHomeScreen() {
		System.out.println("Press number to continue for"+"\n"+"\t"+"1.Account Information"
				+"\n"+"\t2.Deposit Money"+"\n"
				+"\t3.Withdraw Money"+"\n"
				+"\t4.Money Transfer"+"\n"
				+"\t5.Exit");
	}
}
