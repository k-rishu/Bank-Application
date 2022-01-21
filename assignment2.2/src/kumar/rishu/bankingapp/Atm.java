package kumar.rishu.bankingapp;

import java.util.Scanner;
import kumar.rishu.bank.BankAccount;
import kumar.rishu.services.Input;

public class Atm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Input input = new Input();
		Scanner in = new Scanner(System.in);
		BankAccount a2 = new BankAccount("aman", 124, "aman@123", 23344, 12);
		BankAccount a3 = new BankAccount("vivek", 125, "vivek@123", 20000, 12);
		BankAccount a4 = new BankAccount("abhay", 123, "abhay@123", 20000, 12);
		BankAccount a5 = new BankAccount("ravi", 123, "ravi@123", 20000, 12);
		
		
		
		while(true) {
			homePage();
			int choice = input.readInt("Choice: ");
			switch(choice) {
			case 1:{
				int money = input.readInt("Enter Money you want to deposit");
				String response = a2.deposit(money);
				System.out.println(response);
			}
			break;
			case 2:{
				String pswd = input.readString("Enter Your password:");
				String newpswd = input.readString("Enter Your New password:");
				String response = a2.changePassword(pswd, newpswd);
				System.out.println(response);
			}
			break;
			case 3:{
				String pswd = input.readString("Enter Your password:");
				int money = input.readInt("Enter Money you want to Withdraw");
				String response = a2.withdraw(money, pswd);
				System.out.println(response);
			}
			break;
			case 4:{
				String pswd = input.readString("Enter Your password:");
				String response = a2.getBalance(pswd);
				System.out.println(response);
			}
			break;
			case 0:
				System.out.println("Thanks For Visiting !");
				System.exit(0); 
			}
		}
		
	}
	
	public static void homePage() {
		System.out.println("Select 1 Deposit");
		System.out.println("Select 2 for Change Password");
		System.out.println("Select 3 for Withdraw");
		System.out.println("Select 4 for Check Balance");
		System.out.println("Select 0 for EXIT");
	}

}
