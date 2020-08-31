package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE };

	private static final String[] PURCHASE_MENU = { "Feed Money", "Select Product", "Finish Transaction", "Back" }; // From
																														// Andy
	private static final String[] MONEY_MENU = { "$1 Bill", "$2 Bill", "$5 Bill", "$10 Bill", "Back" }; // From Andy

//	Transactions transaction = new Transactions();
	private Menu menu;
	Inventory inventory = new Inventory();
	Product product = new Product();
	Transactions money = new Transactions();
	BigDecimal converter;
	int totalCents;
	int quarters, dimes, nickels, pennies;
	int remaining1, remaining2;
	int change;
	int totalChange;
	
	public int getQuarters() {
		return quarters;
	}

	public int getDimes() {
		return dimes;
	}

	public int getNickels() {
		return nickels;
	}


	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws Exception {
		inventory.inventoryFile();

		while (true) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS); // Creating a new string called
																					// "choice" that calls method in
																					// Menu class

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {

				displayProductItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {

				processPurchaseMenuOptions(); // calls the processPurchaseMenuOptions method

			}
		}
	}

	public void processPurchaseMenuOptions() throws Exception {
		String purchaseMenuOption = "";

		while (!purchaseMenuOption.equals("Back")) { // While the menu option is NOT "Back",
			purchaseMenuOption = (String) menu.getChoiceFromOptions(PURCHASE_MENU); // Will display purchase menu

			if (purchaseMenuOption.equals("Feed Money")) { // If user selects "Feed Money",
				processMoneyFeed(); // we're calling the processMoneyFeed method below
			} else if (purchaseMenuOption.equals("Select Product")) { // If user selects "Select Product"
				chooseItem();

			} else if (purchaseMenuOption.equals("Finish Transaction")) {
				finishTransaction();
				purchaseMenuOption = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			} else if (purchaseMenuOption.equals("Back"))
				run();
		}
	}

	public void processMoneyFeed() throws Exception {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy" + " " + "hh:mm:ss");
		String feedOptions = "";
		String message = "";
//		Transactions money = new Transactions();
		Log.log(ft.format(date) + " Feed Money: ");
		
		while (!feedOptions.equals("Back")) { // While the user selection is NOT "Back"
			feedOptions = (String) menu.getChoiceFromOptions(MONEY_MENU); // Will display Money Menu
			if (feedOptions.equals("$1 Bill")) {// If user selects "$1 Bill"
				BigDecimal oneDollar = new BigDecimal(1.00);
				money.setMoneyInput(oneDollar);
				System.out.println("Current Money Provided $" + money.getMoneyInput());
				message = "$1.00 $" + money.getMoneyInput() + ".00 ";
				Log.log(message + "\n");
			} else if (feedOptions.equals("$2 Bill")) {
				BigDecimal twoDollar = new BigDecimal(2.00);
				money.setMoneyInput(twoDollar);
				System.out.println("Current Money Provided $" + money.getMoneyInput());
				message = "$2.00 $" + money.getMoneyInput() + ".00 ";
				Log.log(message);
			} else if (feedOptions.equals("$5 Bill")) {
				BigDecimal fiveDollar = new BigDecimal(5.00);
				money.setMoneyInput(fiveDollar);
				System.out.println("Current Money Provided $" + money.getMoneyInput());
				message = "$5.00 $" + money.getMoneyInput() + ".00 ";
				Log.log(message);
			} else if (feedOptions.equals("$10 Bill")) {
				BigDecimal tenDollar = new BigDecimal(10.00);
				money.setMoneyInput(tenDollar);
				System.out.println("Current Money Provided $" + money.getMoneyInput());
				message = "$10.00 $" + money.getMoneyInput() + ".00 ";
				Log.log(message);
			} else if (feedOptions.equals("Back")) {
				money.getMoneyInput();

			}
		}
	}

	public void displayProductItems() throws FileNotFoundException {
		inventory.displayItems();
	}
	
	public void chooseItem() throws Exception {
		inventory.inventArray();
		String[] newArray = inventory.inventArray();

		String itemNumber = "";

		itemNumber = (String) menu.getChoiceFromOptions(newArray);

		String[] selection = itemNumber.split("\\s+");

		money.purchaseAndDeduct(selection, inventory.productList);
		
		processPurchaseMenuOptions();
	}

	public void finishTransaction() throws IOException {
		// conversion to cent
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy" + " " + "hh:mm:ss");
		BigDecimal hunnit = new BigDecimal(100.00);
		converter = money.getMoneyInput().multiply(hunnit);
		// conversion to int
		change = converter.intValue();
		totalChange = change;
		while (change > 0) {
//.55 as example
			if (change >= 25) {
				quarters = change / 25;
				change = change % 25;
			
			}

			else if (change >= 10) {
				dimes = change / 10;
				change = change % 10;

			} else if (change >= 5) {
				nickels = change / 5;
				change = change % 5;
				
			}
		}
		double changeOwed = totalChange / 100;
		
		System.out.println("Your change is: $" + money.getMoneyInput()); //changed from totalChange
		System.out.println("Quarters: " + quarters + "\n" + "Dimes: " + dimes + "\n" + "Nickels: " + nickels);
		Log.log(ft.format(date) + " Give Change: $" + money.getMoneyInput() + " $" + money.resetMoney()+ "\n");
		
	}
	
	

	
	
	public static void main(String[] args) throws Exception {
		
		//Log newLog = new Log();
		//newLog.log();
		
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();

	}
}
