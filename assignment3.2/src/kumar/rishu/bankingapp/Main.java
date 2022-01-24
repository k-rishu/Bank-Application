package kumar.rishu.bankingapp;
import java.util.Scanner;

import kumar.rishu.bank.Bank;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		BankAccount person1 = new BankAccount("rishu", 0001, "rishu@123", 2000, 6);
//		person1.getBalance("rishu@123");
//		person1.withdraw(300, "rishu@123");
//		person1.deposit(500);
//		person1.getName();
//		person1.getAccountNumber();
		
		Scanner sc = new Scanner(System.in);
		Bank icici = new Bank("ICICI", 12);
		icici.openAccount("Rishu", "rishu@123", 50000);
		icici.openAccount("Abhay", "abhay@123", 50000);
		while(true) {
			bankHomePage();
			int choice = sc.nextInt();
			switch(choice) {
			case 1:
				icici.login();
				break;
			case 2: {
				System.out.println("Thanks For Visiting !");
				System.exit(0);
				}
				break;
			default:
				System.out.println("Invalid Input!");
				break;
			
			
			}
		}
	}
	public static void bankHomePage() {
		System.out.println("Welcome"+"\n"+"Press 1 for Login: "+"\1n"+"Press 2 to Exit");
	}
	

}
