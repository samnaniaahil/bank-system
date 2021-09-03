import java.util.*;
import java.io.*;
import java.time.*;

/**
   COPYRIGHT (C) 2020 Aahil Samnani. All rights reserved.
   Classes to handle transactions and store customer information.
   @author Aahil Samnani
   @version 1.0
 */

public class Bank {
	public static void main(String[] args) {
		int customerIndex = 0, customerCount = 0;
		int MAX_NUMBER_OF_CUSTOMERS = 50;
		Customer[] customers = new Customer[MAX_NUMBER_OF_CUSTOMERS];
		SavingAccount[] savingAccounts = new SavingAccount[MAX_NUMBER_OF_CUSTOMERS];
		ChequingAccount[] chequingAccounts = new ChequingAccount[MAX_NUMBER_OF_CUSTOMERS];
		CreditCard[] creditCards = new CreditCard[MAX_NUMBER_OF_CUSTOMERS];
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter name of file containing customer data: ");
		String inputFile = sc.nextLine();
			
		try { 
			String input="";
			FileReader file = new FileReader (inputFile);
			BufferedReader buffer = new BufferedReader (file);
			while ((input = buffer.readLine()) != null) {
				if (input.length() > 0) {
					String lastName = input;
					String firstName = buffer.readLine();	
					String sin = buffer.readLine();
					int birthYear = Integer.parseInt(buffer.readLine());
					int birthMonth = Integer.parseInt(buffer.readLine());
					int birthDay = Integer.parseInt(buffer.readLine());
					
					String balanceSaving = buffer.readLine();
					if (balanceSaving.equals("none")) {
						balanceSaving = "-1";
					}
					double savingBalance = Double.parseDouble(balanceSaving);
					
					String balanceChequing = buffer.readLine();
					if (balanceChequing.equals("none")) {
						balanceChequing = "-1";
					}
					double chequingBalance = Double.parseDouble(balanceChequing);
					
					String balanceCreditCard = buffer.readLine();
					if (balanceCreditCard.equals("none")) {
						balanceCreditCard = "1";
					}
					double creditCardBalance = Double.parseDouble(balanceCreditCard);
					
					Customer c = new Customer(firstName, lastName, sin, birthYear, birthMonth, birthDay);
					SavingAccount savingAccount = new SavingAccount(savingBalance, 0);
					ChequingAccount chequingAccount = new ChequingAccount(chequingBalance, 0);
					CreditCard creditCard = new CreditCard(creditCardBalance, 0);
					customers[customerIndex] = c;
					savingAccounts[customerIndex] = savingAccount;
					chequingAccounts[customerIndex] = chequingAccount;
					creditCards[customerIndex] = creditCard;
					
					customerCount++;
					customerIndex++;
				}
			}
			buffer.close();
			if (customerIndex > 0) {
				customerIndex--;
			}
			
		}
		
		catch (IOException iox) { 
			System.out.println("Problem reading " + inputFile); 
		}
	
		for (int z = 0; z <= customerIndex; z++) {
			String fileName = customers[z].getLastName() + customers[z].getSin() + ".txt";
			try {
				File myObj = new File(fileName);
				if (myObj.createNewFile()) {
			        System.out.println("File created: " + fileName);
			    } 
				else {
			        System.out.println(fileName + " found");
			    }
			}
			catch (IOException err) {
				System.out.println("Error creating file.");
			    err.printStackTrace();
			}
		}
		System.out.println("\nWelcome to the VP Bank.");
		System.out.println("-------------------");
		
		boolean exitProgram = false;
		while (!exitProgram) {
			System.out.println("\nPlease choose an action from the following:");
			System.out.println("\t1: Add a customer");
			System.out.println("\t2: Delete a customer");
			System.out.println("\t3: Sort customers by last name, first name");
			System.out.println("\t4: Sort customers by SIN");
			System.out.println("\t5: Display customer summary (name, SIN)");
			System.out.println("\t6: Find profile by last name, first name");
			System.out.println("\t7: Find profile by SIN");
			System.out.println("\t8: Quit");
			
			int userInput = sc.nextInt();

			if (userInput == 1) {
				System.out.println("Enter customer's first name:");
				sc.nextLine();
				String firstName = (sc.nextLine()).trim();
				System.out.println("Enter customer's last name:");
				String lastName = (sc.nextLine()).trim();
				System.out.println("Enter customer's SIN:");
				String sin = (sc.nextLine()).trim();
				System.out.println("Enter customer's birth month:");
				int birthMonth = sc.nextInt();
				System.out.println("Enter customer's birth day:");
				int birthDay = sc.nextInt();
				System.out.println("Enter customer's birth year:");
				int birthYear = sc.nextInt();
				
				boolean validAge = false;
				while (validAge == false) {

					System.out.println("Select an account/card to open [Chequing Account(1)|Saving Account(2)|Credit Card(3)]:");
					int account = sc.nextInt();
					
					LocalDate birth = LocalDate.of(birthYear, birthMonth, birthDay); 
					LocalDate current = LocalDate.now(); 
					Period diff = Period.between(birth, current);
					
					if ((diff.getYears() < 18) && (account == 1 || account == 3)) {
						System.out.println("Sorry, you must be 18 to open this type of account.");
					}
					
					else {
						System.out.println("Opening balance for the account/card?");
						double balance = sc.nextDouble();
						
						ChequingAccount chequingAccount = new ChequingAccount(-1, 0);
						SavingAccount savingAccount = new SavingAccount(-1, 0);
						CreditCard creditCard = new CreditCard(1, 0);						
						
						if (account == 1) {
							chequingAccount.setBalance(balance);							

						}
						else if (account == 2) {
							savingAccount.setBalance(balance);
							
						}
						else if (account == 3) {
							creditCard.setBalance(-balance);
								
						}
						
						Customer c = new Customer(firstName, lastName, sin, birthYear, birthMonth, birthDay);
						customerIndex++;
						customerCount++;
						
						customers[customerIndex] = c;
						chequingAccounts[customerIndex] = chequingAccount;
						savingAccounts[customerIndex] = savingAccount;
						creditCards[customerIndex] = creditCard;
						
						addCustomerToFile(c, savingAccounts, chequingAccounts, creditCards, customerIndex);
						
						if (account == 1) {
							addTransaction(c, "Open an account", "Chequing Account", LocalDate.now().toString(), chequingAccount.getBalance(), 0.0, chequingAccount.getBalance());
						}
						else if (account == 2) {
							addTransaction(c, "Open an account", "Saving Account", LocalDate.now().toString(), savingAccount.getBalance(), 0.0, savingAccount.getBalance());
						}
						else if (account == 3) {
							addTransaction(c, "Open an account", "Credit Card", LocalDate.now().toString(), creditCard.getBalance(), 0.0, creditCard.getBalance());
						}
						
						System.out.println("Customer successfully added!");
						validAge = true;
					}
				}
			}
			if (userInput == 2 && customerCount > 0) {
				System.out.println("Delete by name[1] or SIN[2]?");
				int input = sc.nextInt();
				if (input == 1) {
					System.out.println("Enter customer's last name:");
					sc.nextLine();
					String nameLast = sc.nextLine();
					System.out.println("Enter customer's first name:");
					String nameFirst = sc.nextLine();
					boolean nameExists = false;
					boolean isFound = false;
					for (int i = 0; i <= customerIndex && !isFound; i++) {
						nameExists = (customers[i].getLastName()).equals(nameLast) && 
								(customers[i].getFirstName()).equals(nameFirst);
						if (nameExists) {
							for (int j = i; j <= customerIndex; j++) {
								customers[j] = customers[j+1];
								savingAccounts[j] = savingAccounts[j+1];
								chequingAccounts[j] = chequingAccounts[j+1];
								creditCards[j] = creditCards[j+1];
							}
							customerIndex--;
							customerCount--;
							rewriteCustomerData(customers, savingAccounts, chequingAccounts, creditCards, 
									customerIndex);
							isFound = true;
						}
					}
					if (nameExists) {
						System.out.println("Customer successfully deleted!");
					}
					else {
						System.out.println("Customer not found. Try again.");
					}
				}
				else if (input == 2) {
					System.out.println("Enter customer's SIN:");
					sc.nextLine();
					String sinIn = sc.nextLine();
					boolean sinExists = false;
					boolean isFound = false;
					for (int i = 0; i <= customerIndex && !isFound; i++) {
						sinExists = (customers[i].getSin()).equals(sinIn);
						if (sinExists == true) {
							for (int j = i; j <= customerIndex; j++) {
								customers[j] = customers[j+1];
								savingAccounts[j] = savingAccounts[j+1];
								chequingAccounts[j] = chequingAccounts[j+1];
								creditCards[j] = creditCards[j+1];
							}							
							customerIndex--;
							customerCount--;
							rewriteCustomerData(customers, savingAccounts, chequingAccounts, creditCards, 
									customerIndex);
							isFound = true;
						}
					}
					if (sinExists) {
						System.out.println("Customer successfully deleted!");
					}
					else {
						System.out.println("Customer not found. Try again.");
					}
				}
			}
			
			if (userInput == 3 && customerCount > 0) {
				int x;
				int y;
				Customer temp;
				SavingAccount temp2;
				ChequingAccount temp3;
				CreditCard temp4;
				
				for (x = 0; x <= customerIndex; x++) {
					temp = customers[x];
					temp2 = savingAccounts[x];
					temp3 = chequingAccounts[x];
					temp4 = creditCards[x];
					y = x;
					while (y > 0 && customers[y-1].getLastName().compareTo(temp.getLastName()) > 0
							&& !((customers[y-1]).getLastName().compareTo(temp.getLastName()) == 0)) {
						customers[y] = customers[y-1];
						savingAccounts[y] = savingAccounts[y-1];
						chequingAccounts[y] = chequingAccounts[y-1];
						creditCards[y] = creditCards[y-1];
						y--;
					}
					if (y > 0 && customers[y-1].getLastName().compareTo(temp.getLastName()) == 0 
							&& customers[y-1].getFirstName().compareTo(temp.getFirstName()) > 0) {
						customers[y] = customers[y-1];
						savingAccounts[y] = savingAccounts[y-1];
						chequingAccounts[y] = chequingAccounts[y-1];
						creditCards[y] = creditCards[y-1];
						y--;
					}
					customers[y] = temp;
					savingAccounts[y] = temp2;
					chequingAccounts[y] = temp3;
					creditCards[y] = temp4;
				}
				System.out.println("Customers sorted successfully!");
			}
			
			if (userInput == 4 && customerCount > 0) {
				int a;
				int b;
				Customer temp;
				SavingAccount temp2;
				ChequingAccount temp3;
				CreditCard temp4;
				
				for (a = 0; a <= customerIndex; a++) {
					temp = customers[a];
					temp2 = savingAccounts[a];
					temp3 = chequingAccounts[a];
					temp4 = creditCards[a];
					b = a;
					while (b > 0 && Integer.parseInt(customers[b - 1].getSin()) > Integer.parseInt(temp.getSin())) {
						customers[b] = customers[b -1];
						savingAccounts[b] = savingAccounts[b-1];
						chequingAccounts[b] = chequingAccounts[b-1];
						creditCards[b] = creditCards[b-1];
						b--;
					}
					customers[b] = temp;
					savingAccounts[b] = temp2;
					chequingAccounts[b] = temp3;
					creditCards[b] = temp4;
				}
				System.out.println("Customers sorted successfully!");
			}
			
			if (userInput == 5) {
				System.out.println("Total number of customers: " + (customerIndex+1));
				for (int i = 0; i <= customerIndex; i++) {
					System.out.println(customers[i].toString());
				}
			}

			if (userInput == 6 && customerCount > 0) {
				System.out.println("Enter customer's last name:");
				sc.nextLine();
				String nameLast = sc.nextLine();
				System.out.println("Please enter customer's first name:");
				String nameFirst = sc.nextLine();
				boolean nameExists = false;
				boolean isFound = false;
				for (int i = 0; i <= customerIndex && !isFound; i++) {
					nameExists = (customers[i].getLastName()).equals(nameLast) && 
							(customers[i].getFirstName()).equals(nameFirst);
					if (nameExists) {
						Customer profile = customers[i];
						System.out.println("\nWelcome " + profile.getFirstName() + " " + profile.getLastName() + "!");	
						customerIndex = profileMenu(customers, savingAccounts, chequingAccounts, creditCards, profile, i, customerIndex);
						customerCount = customerIndex + 1;
						isFound = true;
					}
				}
				if (!nameExists) {
					System.out.println("Customer not found. Try again.");
				}
				
			}
			if (userInput == 7 && customerCount > 0) {
				System.out.println("Please enter customer's SIN:");
				sc.nextLine();
				String sinIn = sc.nextLine();
				boolean isFound = false;
				boolean sinExists = false;
				for (int i = 0; i <= customerIndex && !isFound; i++) {
					sinExists = (customers[i].getSin()).equals(sinIn);
					if (sinExists) {
						Customer profile = customers[i];
						System.out.println("\nWelcome " + profile.getFirstName() + " " + profile.getLastName() + "!");						
						customerIndex = profileMenu(customers, savingAccounts, chequingAccounts, creditCards, profile, i, customerIndex);
						isFound = true;
					}
				}
				if (!sinExists) {
					System.out.println("Customer not found. Try again.");
				}
			}
			
			if (userInput == 8) {
				rewriteCustomerData(customers, savingAccounts, chequingAccounts, creditCards, 
						customerIndex);
				exitProgram = true;
			}
		}
		sc.close();
	}
	
	/**
	 * 
	 * @param customers array of customers' profiles
	 * @param savingAccounts array of customers' saving accounts
	 * @param chequingAccounts array of customers' chequing accounts
	 * @param creditCards array of customers' credit cards
	 * @param profile profile of customer accessing profile menu
	 * @param profileIndex index of customer accessing profile menu in customer array
	 * @param customerIndex index count of customers
	 * @return
	 */
	public static int profileMenu(Customer customers[], SavingAccount savingAccounts[], 
			ChequingAccount chequingAccounts[], CreditCard creditCards[], Customer profile, 
			int profileIndex, int customerIndex) {
		boolean returnToMainMenu = false;
		SavingAccount savingAccount = savingAccounts[profileIndex];
		ChequingAccount chequingAccount = chequingAccounts[profileIndex];
		CreditCard creditCard = creditCards[profileIndex];
		
		while (!returnToMainMenu) {
			Scanner sc = new Scanner(System.in);

			System.out.println("\nPROFILE MENU");
			System.out.println("-------------------");
			System.out.println("\t1: View account activity");
			System.out.println("\t2: Deposit");
			System.out.println("\t3: Withdraw");
			System.out.println("\t4: Process cheque");
			System.out.println("\t5: Process purchase");
			System.out.println("\t6: Pay bill");
			System.out.println("\t7: Transfer funds");
			System.out.println("\t8: Open account or issue card");
			System.out.println("\t9: Cancel account or card");
			System.out.println("\t10: Return to main menu");
			
			int userInput = sc.nextInt();
			
			if (userInput == 1) {
				String fileName = profile.getLastName() + profile.getSin() + ".txt";
				try {
					FileReader file;
					BufferedReader buffer;
					String input="";
					int transactionCount = 0, lineCount = 0;
					int maxTransactions = 5, maxLinesPerTransaction = 12;
					String[][] transactionArr = new String[maxTransactions][maxLinesPerTransaction];
					
					file = new FileReader(fileName);
					buffer = new BufferedReader(file);			
					
					System.out.println("Most recent transactions:");
					while (((input = buffer.readLine()) != null)) {
						if (input.length() == 0) {
							transactionCount++;
							lineCount = 0;
						}
						if (transactionCount == maxTransactions) {
							transactionCount = 0;
						}
						transactionArr[transactionCount][lineCount] = input;
						lineCount++;
					}
					if (transactionCount > 0) {
						for (int i = transactionCount; i < maxTransactions; i++) {
							for (int y = 0; y < maxLinesPerTransaction; y++) {
								if (transactionArr[i][y] != null) {
									System.out.println(transactionArr[i][y]);
								}
							}
						}
					}
					for (int i = 0; i < transactionCount; i++) {
						for (int y = 0; y < maxLinesPerTransaction; y++) {
							if (transactionArr[i][y] != null) {
								System.out.println(transactionArr[i][y]);
							}
						}
					}
					buffer.close();
				}
				catch (IOException iox) { 
					System.out.println("Problem reading " + fileName); 
				}
			}
			
			if (userInput == 2) {
				System.out.println("Would you like to deposit money into your chequing[1] or savings[2] account?");
				int userSelection = sc.nextInt();
				if (userSelection == 1 && chequingAccount.getBalance() == -1 || userSelection == 2 && savingAccount.getBalance() == -1) {
					System.out.println("The specified account doesn't exist.");
				}
				else {
					System.out.println("How much would you like to deposit?");
					double balance = sc.nextDouble();
					if (userSelection == 1) {
						chequingAccount.deposit(balance);
						addTransaction(profile, "Deposit", "Chequing Account", LocalDate.now().toString(), 
								chequingAccount.getBalance() - balance, balance, chequingAccount.getBalance());
						System.out.println("Deposit successful!");
					}
					else if (userSelection == 2){
						savingAccount.deposit(balance);
						addTransaction(profile, "Deposit", "Saving Account", LocalDate.now().toString(), 
								savingAccount.getBalance() - balance, balance, savingAccount.getBalance());
						System.out.println("Deposit successful!");
					}
				}
			}
			if (userInput == 3) {
				System.out.println("Would you like to withdraw money from your chequing[1], savings[2] or credit card[3] account?");
				int userSelection = sc.nextInt();
				
				if (userSelection == 1 && chequingAccount.getBalance() == -1 || userSelection == 2 && savingAccount.getBalance() == -1 
						|| userSelection == 3 && creditCard.getBalance() == 1) {
					System.out.println("The specified account doesn't exist.");
				}
				else {
					System.out.println("How much would you like to withdraw?");
					double balance = sc.nextDouble();
					if (userSelection == 1 && chequingAccount.getBalance() >= balance) {
						chequingAccount.withdraw(balance);
						addTransaction(profile, "Withdrawl", "Chequing Account", LocalDate.now().toString(), 
								chequingAccount.getBalance() + balance, balance, chequingAccount.getBalance());
						System.out.println("Withdrawal sucessful!");
					}
					else if (userSelection == 2 && savingAccount.getBalance() >= balance) {
						savingAccount.withdraw(balance);
						addTransaction(profile, "Withdrawl", "Saving Account", LocalDate.now().toString(), 
								savingAccount.getBalance() + balance, balance, savingAccount.getBalance());
						System.out.println("Withdrawal sucessful!");
					}
					else if (userSelection == 3) {
						creditCard.withdraw(balance);
						addTransaction(profile, "Withdrawl", "Credit Card", LocalDate.now().toString(), 
								creditCard.getBalance()+balance, balance, creditCard.getBalance());
						System.out.println("Withdrawal sucessful!");
					}
					else {
						System.out.println("Unfortunately, you don't have enough money in this account for the withdrawl.");
					}
				}
			}
			
			if (userInput == 4) {
				if (chequingAccount.getBalance() == -1) {
					System.out.println("You have not registered for a chequing account.\n");
				}				
				else {
					System.out.println("How much would you like to deposit?");
					double deposit = sc.nextDouble();
					if (chequingAccount.getBalance() < 1000) {
						chequingAccount.deposit(deposit-1);
						addTransaction(profile, "Deposit a cheque (+$1 transaction fee)", "Chequing", LocalDate.now().toString(), 
								chequingAccount.getBalance() - deposit + 1, deposit, chequingAccount.getBalance());
						System.out.println("Deposit successful! A $1.00 transaction fee for the cheque was charged as your "
								+ "balance before the deposit was lower than $1000.");
					}
					else {
						chequingAccount.deposit(deposit);
						addTransaction(profile, "Deposit a cheque", "Chequing", LocalDate.now().toString(), 
								chequingAccount.getBalance() - deposit, deposit, chequingAccount.getBalance());
						System.out.println("Deposit successful!");
					}
				}
			}
			
			if (userInput == 5) {
				System.out.println("Enter money spent on purchase: ");
				double purchase = sc.nextDouble();
				sc.nextLine();
				System.out.println("Which store/company was the purchase from?");
				String location = sc.nextLine();
				System.out.println("When did the purchase occur (mm/dd/yy)?");
				String purchaseDate = sc.nextLine();
				
				if (creditCard.getBalance() == 1) {
					System.out.println("You have not registered for a credit card account.");
				}
				else {
					creditCard.withdraw(purchase);
					addTransaction(profile, "purchase at " + location, "Credit Card", purchaseDate, 
							creditCard.getBalance() + purchase, purchase, creditCard.getBalance());
					System.out.println("Purchase successfully recorded!");
				}
				
			}
			if (userInput == 6) {
				System.out.println("Pay credit card bill using chequing[1] or savings[2] account?");
				int command = sc.nextInt();
				
				if (command == 1 && chequingAccount.getBalance() == -1 || command == 2 && savingAccount.getBalance() == -1) {
					System.out.println("The specified account doesn't exist.");
				}
				
				else {
					System.out.println("Your credit card bill is: $" + (-creditCard.getBalance()));
					System.out.println("How much would you like to pay off?");
					double payment = sc.nextDouble();
					
					if (creditCard.getBalance() + payment > 0) {
						System.out.println("Try again. Your credit card bill isn't that large.");
					}
					
					else if (command == 1 && chequingAccount.getBalance() >= payment) {
						chequingAccount.withdraw(payment);
						creditCard.deposit(payment);
						addTransaction(profile, "Pay credit card bill", "CtoCC", LocalDate.now().toString(), 
								chequingAccount.getBalance(), payment, creditCard.getBalance());
						System.out.println("Specified amount has been paid off your bill successfully!");
					}
					else if (command == 2 && savingAccount.getBalance() >= payment) {
						savingAccount.withdraw(payment);
						creditCard.deposit(payment);
						addTransaction(profile, "Pay credit card bill", "StoCC", LocalDate.now().toString(), 
								savingAccount.getBalance(), payment, creditCard.getBalance());
						System.out.println("Specified amount has been paid off your bill successfully!");
					}
					else {
						System.out.println("Unfortunately, you don't have enough money in this account to pay off the specified amount.");
					}
				}
			}
			
			if (userInput == 7) {
				System.out.println("Transfer funds from savings to chequing account[1] or chequing to savings account[2]?");
				int command = sc.nextInt();
				
				if (savingAccount.getBalance() == -1 || chequingAccount.getBalance() == -1) {
					System.out.println("Action not possible. Required accounts don't exist.");
				}
				else {
					System.out.println("How much would you like to transfer?");
					double transfer = sc.nextDouble();
					if (command == 1 && savingAccount.getBalance() >= transfer) {
						savingAccount.withdraw(transfer);
						chequingAccount.deposit(transfer);
						addTransaction(profile, "Fund Transfer", "StoC", LocalDate.now().toString(), 
								savingAccount.getBalance(), transfer, chequingAccount.getBalance());
						System.out.println("Fund transfered successfully!");
						
					}
					else if (command == 2 && chequingAccount.getBalance() >= transfer) {
						chequingAccount.withdraw(transfer);
						savingAccount.deposit(transfer);
						addTransaction(profile, "Fund Transfer", "CtoS", LocalDate.now().toString(), 
								chequingAccount.getBalance(), transfer, savingAccount.getBalance());
						System.out.println("Fund transfered successfully!");
					}
					else {
						System.out.println("Unfortunately, you don't have enough money in the specificed account for the transaction.");
					}
				}
			}
			
			if (userInput == 8) {				
				System.out.println("Select an account/card to open [Chequing Account(1)|Savings Account(2)|Credit Card(3)]?");
				int command = sc.nextInt();
				
				LocalDate birth = LocalDate.of(profile.getBirthYear(), profile.getBirthMonth(), profile.getBirthDay()); 
				LocalDate current = LocalDate.now(); 
				Period diff = Period.between(birth, current);
					
				if ((diff.getYears() < 18) && (command == 1 || command == 3)) {
					System.out.println("Sorry, you must be 18 to open this type of account/card.");
				}
				else {
					System.out.println("Opening balance for the account/card?");
					double balance = sc.nextDouble();
				
					if (command == 1 && chequingAccount.getBalance() == -1) {
						chequingAccount.setBalance(balance);
						addTransaction(profile, "Open an account", "Chequing Account", LocalDate.now().toString(), 
								balance, 0.0, balance);
						System.out.println("Account successfully created!");
					}
					else if (command == 2 && savingAccount.getBalance() == -1) {
						savingAccount.setBalance(balance);
						addTransaction(profile, "Open an account", "Saving Account", LocalDate.now().toString(), 
								balance, 0.0, balance);
						System.out.println("Account successfully created!");
					}
					else if (command == 3 && creditCard.getBalance() == 1) {
						creditCard.setBalance(-balance);
						addTransaction(profile, "Open an account", "Saving Account", LocalDate.now().toString(), 
								balance, 0.0, balance);
						System.out.println("Account successfully created!");
					}
					else {
						System.out.println("Specificed account already exists.");
					}
				}
			}
		
			
			if (userInput == 9) {
				System.out.println("Which account/card would you like to cancel [Chequing Account(1)|Savings Account(2)|Credit Card(3)]:");
				int command = sc.nextInt();
				
				if ((command == 1 && chequingAccount.getBalance() == -1) || (command == 2 && savingAccount.getBalance() == -1) 
						|| (command == 3 && creditCard.getBalance() == 1)) {
					System.out.println("Specified account doesn't exist.");
				}
				else if (command == 1) {
					addTransaction(profile, "Account Closure", "Chequing Account", LocalDate.now().toString(), 
							chequingAccount.getBalance(), 0.0, -1);
					chequingAccount.setBalance(-1);
					System.out.println("Account successfully closed!");
				}
				else if (command == 2) {
					addTransaction(profile, "Account Closure", "Saving Account", LocalDate.now().toString(), 
							savingAccount.getBalance(), 0.0, -1);
					savingAccount.setBalance(-1);
					System.out.println("Account successfully closed!");
				}
				else if (command == 3) {
					if (creditCard.getBalance() < 0) {
						System.out.println("Unfortunately, you can't close this account as you have outstanding balance.");
					}
					else {
						addTransaction(profile, "Account Closure", "Credit Card", LocalDate.now().toString(), 
								creditCard.getBalance(), 0.0, 1);
						creditCard.setBalance(1);
						System.out.println("Account successfully closed!");
					}
				}
				if (savingAccount.getBalance() == -1 && chequingAccount.getBalance() == -1 && 
						creditCard.getBalance() == 1) {
					for (int j = profileIndex; j <= customerIndex; j++) {
						customers[j] = customers[j + 1];
						savingAccounts[j] = savingAccounts[j + 1];
						chequingAccounts[j] = chequingAccounts[j + 1];
						creditCards[j] = creditCards[j + 1];
					}		
					customerIndex--;
					addTransaction(profile, "Profile Closure", "N/A", LocalDate.now().toString(),
							0.0, 0.0, 0.0);
					rewriteCustomerData(customers, savingAccounts, chequingAccounts, creditCards, 
							customerIndex);
					System.out.println("Profile deleted!");
					returnToMainMenu = true;
				}
			}
			
			if (userInput == 10) {
				returnToMainMenu = true;
			}

			sc.close();
		}
		return customerIndex;
	}
	
	/**
	   Add transaction to customer's transaction file.
	   @param profile profile of customer accessing the profile menu
	   @param transactionDescription description of current transaction
	   @param transactionType type of current transaction 
	   @param date date of transaction
	   @param firstBalance balance before transaction of specified account 
	   or current balance of first account (if transaction involves 2 accounts)
	   @param transaction amount of current transaction
	   @param secondBalance balance before transaction of specified account 
	   or current balance of second account (if transaction involves 2 accounts)
	 */
	public static void addTransaction(Customer profile, String transactionDescription, String transactionType, String date, 
			double firstBalance, double transaction, double secondBalance) {
		String fileName = profile.getLastName() + profile.getSin() + ".txt";
		try { 
			FileWriter writer = new FileWriter(fileName, true);
			if (transactionDescription.equals("Pay credit card bill") || transactionDescription.equals("Fund Transfer") ||
					 transactionDescription.equals("Profile Closure")) {
				if (transactionType.equals("CtoCC")) {
					writer.write("Description: " + transactionDescription + "\n");
					writer.write("From: Chequing Account" + "\n");
					writer.write("To: Credit Card" + "\n");
					writer.write("Amount paid: " + transaction + "\n");
					writer.write("Date: " + date + "\n");
					writer.write("Account: Chequing" + "\n");
					writer.write("Opening Balance: " + (firstBalance+transaction) + "\n");
					writer.write("Ending Balance: " + firstBalance + "\n");
					writer.write("Account: Credit Card" + "\n");
					writer.write("Opening Balance: " + (secondBalance-transaction) + "\n");
					writer.write("Ending Balance: " + secondBalance + "\n\n");
				}
				else if (transactionType.equals("StoCC")){
					writer.write("Description: " + transactionDescription + "\n");
					writer.write("From: Saving Account" + "\n");
					writer.write("To: Credit Card" + "\n");
					writer.write("Amount paid: " + transaction + "\n");
					writer.write("Date: " + date + "\n");
					writer.write("Account: Saving"+ "\n");
					writer.write("Opening Balance: " + (firstBalance+transaction) + "\n");
					writer.write("Ending Balance: " + firstBalance + "\n");
					writer.write("Account: Credit Card" + "\n");
					writer.write("Opening Balance: " + (secondBalance-transaction) + "\n");
					writer.write("Ending Balance: " + secondBalance + "\n\n");
				}
				
				else if (transactionType.equals("StoC")) {
					writer.write("Description: " + transactionDescription + "\n");
					writer.write("From: Saving Account" + "\n");
					writer.write("To: Chequing Account" + "\n");
					writer.write("Amount paid: " + transaction + "\n");
					writer.write("Date: " + date + "\n");
					writer.write("Account: Saving" + "\n");
					writer.write("Opening Balance: " + (firstBalance+transaction) + "\n");
					writer.write("Ending Balance: " + firstBalance + "\n");
					writer.write("Account: Chequing" + "\n");
					writer.write("Opening Balance: " + (secondBalance-transaction) + "\n");
					writer.write("Ending Balance: " + secondBalance + "\n\n");
				}
				
				else if (transactionType.equals("CtoS")) {
					writer.write("Description: " + transactionDescription + "\n");
					writer.write("From: Chequing Account" + "\n");
					writer.write("To: Saving Account" + "\n");
					writer.write("Amount paid: " + transaction + "\n");
					writer.write("Date: " + date + "\n");
					writer.write("Account: Chequing" + "\n");
					writer.write("Opening Balance: " + (firstBalance+transaction) + "\n");
					writer.write("Ending Balance: " + firstBalance + "\n");
					writer.write("Account: Saving" + "\n");
					writer.write("Opening Balance: " + (secondBalance-transaction) + "\n");
					writer.write("Ending Balance: " + secondBalance + "\n\n");
				}
				
				else {
					writer.write("Description: " + transactionDescription + "\n");
					writer.write("Last name: " + profile.getLastName() + "\n");
					writer.write("First name: " + profile.getFirstName() + "\n");
					writer.write("Customer SIN: " + profile.getSin() + "\n");
					writer.write("Date: " + date + "\n\n");
				}
			}
			else {
				writer.write("Description: " + transactionDescription + "\n");
				writer.write("Account: " + transactionType + "\n");
				writer.write("Date: " + date + "\n");
				writer.write("Opening balance: " + firstBalance + "\n");
				writer.write("Amount of Transaction: " + transaction + "\n");
				writer.write("Ending Balance: " + secondBalance + "\n\n");
			}
			writer.close();
		}
		catch ( IOException iox ) { 	
			System.out.println("Problem writing " + fileName); 
		}
	}
	
	/** 
	   Add customer to data file.
	   @param profile profile of customer accessing profile menu
	   @param savingAccounts array of customers' saving accounts 
	   @param chequingAccounts array of customers' chequing accounts
	   @param creditCards array of customers' credit cards
	   @param profileIndex index of customer accessing profile menu in array of customers 
	 */
	public static void addCustomerToFile(Customer profile, SavingAccount savingAccounts[], 
			ChequingAccount chequingAccounts[], CreditCard creditCards[], int profileIndex) {
		SavingAccount savingAccount = savingAccounts[profileIndex];
		ChequingAccount chequingAccount = chequingAccounts[profileIndex];
		CreditCard creditCard = creditCards[profileIndex];
		
		String fileName = "dataFile.txt";
		try { 
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(profile.getLastName() + "\n");
			writer.write(profile.getFirstName() + "\n");
			writer.write(profile.getSin() + "\n");
			writer.write(profile.getBirthYear() + "\n");
			writer.write(profile.getBirthMonth() + "\n");
			writer.write(profile.getBirthDay() + "\n");
			if (savingAccount.getBalance() == -1) {
				writer.write("none\n");
			}
			else {
				writer.write(savingAccount.getBalance()+"\n");
			}
			if (chequingAccount.getBalance() == -1) {
				writer.write("none\n");
			}
			else {
				writer.write(chequingAccount.getBalance()+"\n");
			}
			if (creditCard.getBalance() == 1) {
				writer.write("none\n\n");
			}
			else {
				writer.write(creditCard.getBalance()+"\n\n");
			}
			writer.close();
		}
		catch ( IOException iox ) { 	
			System.out.println("Problem writing " + fileName); 
		}
	}
	
	/** 
	 * Rewrite data file to update customer data.
	 * @param customers array of customers' profiles
	 * @param savingAccounts array of customers' saving accounts
	 * @param chequingAccounts array of customers' chequing accounts
	 * @param creditCards array of customers' credit card accounts
	 * @param customerIndex index count of customers
	 */
	public static void rewriteCustomerData(Customer customers[], SavingAccount savingAccounts[], 
			ChequingAccount chequingAccounts[], CreditCard creditCards[], int customerIndex) {
		String fileName = "dataFile.txt";
				
		try {
			FileWriter writer = new FileWriter(fileName, false);
			for (int i = 0; i <= customerIndex; i++) {
				addCustomerToFile(customers[i], savingAccounts, chequingAccounts, creditCards, i);
			}
			writer.close();
		}
		catch (IOException iox) { 	
			System.out.println("Problem writing " + fileName); 
		}		
	}
}
