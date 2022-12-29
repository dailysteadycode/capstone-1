package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_HIDDEN_SALES_REPORT = "(Hidden) Sales Report";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_HIDDEN_SALES_REPORT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };
	private static final String[] DOLLAR_OPTIONS = {"$1.00", "$5.00", "$10.00", "$20.00"};

	private Menu menu;
	private boolean mainMenu = true;
	private double balance = 0;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public TreeMap<String, Item> items = new TreeMap<>();

	public void run() {
		//read items from input file
		readInputFile();

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				displayItems("all");
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				purchaseMenu();
			} else if (choice.equals(MAIN_MENU_OPTION_HIDDEN_SALES_REPORT)) {
				// sales report
				System.out.println("Sales Report Saved!");
				SalesReport.report(items);
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				// do exit
				break;
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

	public void readInputFile() {
		File inputFile = new File("vendingmachine.csv");

		try(Scanner inputData = new Scanner(inputFile)) {
			while(inputData.hasNext()) {
				Item item = null;
				String[] itemFactors = inputData.nextLine().split("\\|");
				if (itemFactors[3].equals("Chip")) {
					item = new Chip(itemFactors[1], Double.parseDouble(itemFactors[2]), itemFactors[3]);
				} else if (itemFactors[3].equals("Candy")) {
					item = new Candy(itemFactors[1], Double.parseDouble(itemFactors[2]), itemFactors[3]);
				} else if (itemFactors[3].equals("Drink")) {
					item = new Drink(itemFactors[1], Double.parseDouble(itemFactors[2]), itemFactors[3]);
				} else if (itemFactors[3].equals("Gum")) {
					item = new Gum(itemFactors[1], Double.parseDouble(itemFactors[2]), itemFactors[3]);
				}
				items.put(itemFactors[0], item);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}

	}

	public void displayItems(String option) {
		for(Map.Entry<String, Item> item : items.entrySet())
			if (option.toLowerCase().equals("all")) {
				if (item.getValue().getQty() == 0) {
					System.out.println(item.toString() + "- SOLD OUT");
				} else {
					System.out.println(item.toString());
				}
			} else if (option.toLowerCase().equals("available")) {
				if (item.getValue().getQty() > 0) {
					System.out.println(item.toString());
				}
			}
	}

	private void purchaseMenu() {
		while (true) {
			if (balance > 0) System.out.println("Current Money Provided: $" + String.format("%.2f", balance));

			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
			if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
				// Feed money
				feedMoney();
			} else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
				// Select product
				selectProduct();
			} else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				// finish transaction
				finishTransaction();
				break;
			}
		}
	}

	private void feedMoney() {
		String dollar = (String) menu.getChoiceFromOptions(DOLLAR_OPTIONS);
		if (dollar.equals("$1.00") || dollar.equals("$5.00") ||
				dollar.equals("$10.00") || dollar.equals("$20.00")) {
			// display vending machine items
			balance += Double.parseDouble(dollar.substring(1,dollar.length()));
		}
		TELog.log("FEED MONEY: " + dollar + dollarFormat(balance));
	}

	private void selectProduct() {
		displayItems("available");

		System.out.println("Please enter a slot what you want to get!");
		Scanner scanner = new Scanner(System.in);

		String slot = scanner.nextLine().toUpperCase();

		if (!items.containsKey(slot)) {
			System.out.println("Slot code doesn't exist!");
			return;
		}
		// Selected Item
		Item selectedItem = items.get(slot);
		// Sold Out
		if (selectedItem.getQty() == 0) {
			System.out.println(selectedItem.getName() + " is sold out!");
			return;
		}
		// Balance Low
		if (selectedItem.getPrice() > balance) {
			System.out.println("Balance is low");
			return;
		}
		// sold product
		sold(slot, selectedItem);
	}

	public void sold(String slot, Item item) {
		balance -= item.getPrice();
		item.setQty(item.getQty() - 1);

		String itemType = item.getType().toLowerCase();

		item.printSound();

		TELog.log(item.getName() + " " + slot + dollarFormat(item.getPrice()) + dollarFormat(balance));
	}

	public void finishTransaction() {
		System.out.println("Change : " + dollarFormat(balance));
		double newBalance = 0;
		TELog.log("GIVE CHANGE:" + dollarFormat(balance) + dollarFormat(newBalance));
		balance = newBalance;
	}

	public static String dollarFormat(double dollar) {
		return " $"+String.format("%.2f", dollar);
	}
}
