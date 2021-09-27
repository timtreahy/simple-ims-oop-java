package edu.cpt187.treahy.exercise3;

//Author:Tim Treahy
//Course:CPT 187
//Purpose: This organizes your order.
//Purpose: Do you receive a discount?
//Purpose: What items are you interested in?
//Start Date:1/28/2021

import java.util.Scanner;


public class MainClass {

	public static final char[] MENU_CHARS = {'A', 'B', 'Q'};
	public static final String[] MENU_OPTIONS = {"Load Inventory", "Create Order", "Quit"};
	public static final char[] SUB_MENU_CHARS = {'A', 'B', 'C', 'D'};

	public static void main(String[] args)
	{
		//initialize scanner object
		Scanner input = new Scanner (System.in);

		//initialize variables
		String userName = "";
		char menuSelection;

		//create new Order from order class
		Order currentOrder = new Order();
		//create new Inventory from inventory class
		Inventory currentInventory = new Inventory();
		//welcome banner
		displayWelcomeBanner();
		//userName
		userName = getUserName(input);
		//menuSelection
		menuSelection = validateMainMenu(input);

		//while not quit
		while (menuSelection != 'Q')
		{//while the user would like to continue running the program. Q represents quit
			if (menuSelection == 'A')
			{//"stock" the inventory, if this is not done first program will give error to user
				currentInventory.setInStockCounts();
				displayInventoryLoaded();
			}
			else
			{//access inventory and place order
				currentOrder.setLastItemSelectedIndex(validateItemMenu(input,currentInventory.getItemNames(),currentInventory.getItemPrices()));
				currentOrder.setItemName(currentInventory.getItemNames());
				currentOrder.setItemPrice(currentInventory.getItemPrices());
				currentOrder.setHowMany(validateHowMany(input));
				if (currentOrder.getInStockCount(currentInventory.getInStockCounts()) < currentOrder.getHowMany())
				{//if inventory is not sufficient for amount requested. will kick user back to main menu
					displayOutOfStock();
				}
				else
				{//user is ordering
					currentOrder.setDiscountType(validateDiscountMenu(input, currentInventory.getDiscountNames(), currentInventory.getDiscountRates()));
					currentOrder.setDiscountName(currentInventory.getDiscountNames());
					currentOrder.setDiscountRate(currentInventory.getDiscountRates());
					currentOrder.setDecreaseInStock(currentInventory);
					currentOrder.setPrizeName(currentInventory.getPrizeNames(), currentInventory.getRandomNumber());
					if (currentOrder.getDiscountRate() > 0.0)
					{//the user selected a discount option
						displayOrderReport(currentOrder.getItemName(), currentOrder.getItemPrice(), currentOrder.getHowMany(), currentOrder.getDiscountName(), currentOrder.getDiscountRate(), currentOrder.getDiscountAmt(), currentOrder.getDiscountPrice(), currentOrder.getSubTotal(), currentOrder.getTaxRate(), currentOrder.getTaxAmt(), currentOrder.getTotalCost(), currentOrder.getPrizeName());
					}
					else
					{//the user did not select a discount option
						displayOrderReport(currentOrder.getItemName(), currentOrder.getItemPrice(), currentOrder.getHowMany(), currentOrder.getSubTotal(), currentOrder.getTaxRate(), currentOrder.getTaxAmt(), currentOrder.getTotalCost(), currentOrder.getPrizeName());
					}//end of order report
				}//end of user ordering
			}//end of access inventory

			menuSelection = validateMainMenu(input); //end of loop validation
		}//end while

		if (currentOrder.getTotalCost() > 0.0)
		{//did the user successfully order, if so print receipt
			displayFinalReport(userName, currentInventory.getItemNames(), currentOrder.getItemCounts(), currentInventory.getDiscountNames(), currentOrder.getDiscountCounts(), currentInventory.getPrizeNames(), currentOrder.getPrizeCounts());
		}//end receipt

		displayFarewellMessage();

		//close scanner
		input.close();

	}//end main


	////////////
	//VOID method
	////////////

	public static void displayWelcomeBanner()
	{//display banner introducing user to program
		System.out.printf("\n%s\n", "Welcome to Sod LLC.");
		System.out.printf("%s\n", "We hope you enjoy being an employee!");
		System.out.printf("%-60s\n", "Make your way through the order menus.");
		System.out.printf("%-60s\n", "Follow the prompts to move through the selection menus.");
		System.out.printf("%-60s\n", "Select to begin, choose items, discounts, an amount...");
		System.out.printf("%s\n\n", "You may even win a prize at the end");
	}
	public static void displayFarewellMessage()
	{
		System.out.printf("\n%s\n\n", "It's been a pleasure.");
		System.out.printf("%s\n", "Quote of the day:");
		System.out.printf("%s\n", "What is the purpose of life?");
		System.out.printf("%s\n", "...");
		System.out.printf("%-60s", "Planting flowers to which the butterflies come.");
	}
	public static void displayMainMenu()
	{
		int localIndex = 0;
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s\n", "MAIN MENU");
		//print each item under main menu
		while (localIndex < MENU_OPTIONS.length)
		{
			System.out.printf("%s%s%s\n", MENU_CHARS[localIndex], " for ", MENU_OPTIONS[localIndex]);
			localIndex++;
		}
		System.out.printf("%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "Enter your selection:");
	}//end main menu
	public static void displayItemMenu(String[] borrowedItemNames, double[] borrowedItemPrices)
	{
		int localIndex = 0;
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s\n", "ITEM MENU");
		//print each item under item menu
		while (localIndex < borrowedItemNames.length)
		{
			System.out.printf("%s%s%-15s%s%-10.2f\n", SUB_MENU_CHARS[localIndex], " for ", borrowedItemNames[localIndex], "$ ", borrowedItemPrices[localIndex]);
			localIndex++;
		}
		System.out.printf("%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "Enter your selection:");
	}//end item menu
	public static void displayDiscountMenu(String[] borrowedDiscountNames, double[] borrowedDiscountRates)
	{
		int localIndex = 0;
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s\n", "DISCOUNT MENU");
		//print each item under discount menu
		while (localIndex < borrowedDiscountNames.length)
		{
			System.out.printf("%s%s%-15s%10.1f%s\n", SUB_MENU_CHARS[localIndex], " for ", borrowedDiscountNames[localIndex], borrowedDiscountRates[localIndex]*100, " %");
			localIndex++;
		}
		System.out.printf("%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "Enter your selection:");
	}//end discount menu


	/////PROGRAM ANNOUNCEMENTS//////

	public static void displayInventoryLoaded()
	{
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s\n", "INVENTORY LOADED REPORT");
		System.out.printf("%s\n", "Inventory in-stock counts have been processed");
		System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayOutOfStock()
	{
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s\n", "OUT OF STOCK ERROR");
		System.out.printf("%s\n", "The quantity entered is greater than the quantity in-stock");
		System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	//display order report if discount rate is not greater than 0
	public static void displayOrderReport(String borrowedItemName, double borrowedItemPrice, int borrowedHowMany, double borrowedSubTotal, double borrowedTaxRate, double borrowedTaxAmt, double borrowedTotalCost, String borrowedPrizeName)
	{
		//Order Report
		System.out.printf("\n\n\n%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.printf("%-8s\n", "ORDER REPORT");
		System.out.printf("%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.printf("%-25s%2s%-17s\n", "Item Name", "", borrowedItemName);
		System.out.printf("%-26s%2s%15.2f\n\n", "Item Price", "$", borrowedItemPrice);
		System.out.printf("%-26s%2s%15d\n\n", "Quantity", "", borrowedHowMany);
		System.out.printf("%-26s%2s%15.2f\n", "Subtotal", "$", borrowedSubTotal);
		System.out.printf("%-26s%17.1f%2s\n", "Tax Rate", (borrowedTaxRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n", "Tax Amount", "$", borrowedTaxAmt);
		System.out.printf("%-26s%2s%15.2f\n", "Order Total", "$", borrowedTotalCost);
		System.out.printf("%-25s%2s%-17s\n", "Prize", "", borrowedPrizeName);
		System.out.printf("%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
	}
	//display order report if the discount rate is greater than 0
	public static void displayOrderReport(String borrowedItemName, double borrowedItemPrice, int borrowedHowMany, String borrowedDiscountName, double borrowedDiscountRate, double borrowedDiscountAmt, double borrowedDiscountPrice, double borrowedSubTotal, double borrowedTaxRate, double borrowedTaxAmt, double borrowedTotalCost, String borrowedPrizeName)
	{
		//Order Report
		System.out.printf("\n\n\n%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.printf("%-8s\n", "ORDER REPORT");
		System.out.printf("%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.printf("%-25s%2s%-17s\n", "Item Name", "", borrowedItemName);
		System.out.printf("%-26s%2s%15.2f\n\n", "Item Price", "$", borrowedItemPrice);
		System.out.printf("%-25s%2s%-17s\n", "Discount Name", "", borrowedDiscountName);
		System.out.printf("%-26s%16.1f%3s\n", "Discount Rate", (borrowedDiscountRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n", "Discount Amount", "$", borrowedDiscountAmt);
		System.out.printf("%-26s%2s%15.2f\n\n", "Discounted Price", "$", borrowedDiscountPrice);
		System.out.printf("%-26s%2s%15d\n\n", "Quantity", "", borrowedHowMany);
		System.out.printf("%-26s%2s%15.2f\n", "Subtotal", "$", borrowedSubTotal);
		System.out.printf("%-26s%16.1f%3s\n", "Tax Rate", (borrowedTaxRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n", "Tax Amount", "$", borrowedTaxAmt);
		System.out.printf("%-26s%2s%15.2f\n", "Order Total", "$", borrowedTotalCost);
		System.out.printf("%-25s%2s%-17s\n", "Prize", "", borrowedPrizeName);
		System.out.printf("%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
	}
	public static void displayFinalReport(String userName, String[] borrowedItemNames, int[] borrowedItemCounts, String[] borrowedDiscountNames, int[] borrowedDiscountCounts, String[] borrowedPrizeNames, int[] borrowedPrizeCounts)
	{
		int localItemIndex = 0;
		int localDiscountIndex = 0;
		int localPrizeIndex = 0;
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "FINAL REPORT");
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%-34s%2s%-17s\n\n", "Employee Name", "", userName);

		System.out.printf("\n%s\n", "Item counts:");
		//while printing through final item counts
		while (localItemIndex < borrowedItemNames.length)
		{
			System.out.printf("%-35s%2s\n", borrowedItemNames[localItemIndex], borrowedItemCounts[localItemIndex]);
			localItemIndex++;
		}//end running through item counts
		//while printing through final discount counts
		System.out.printf("\n%s\n", "Discount counts:");
		while (localDiscountIndex < borrowedDiscountNames.length)
		{
			System.out.printf("%-35s%2s\n", borrowedDiscountNames[localDiscountIndex], borrowedDiscountCounts[localDiscountIndex]);
			localDiscountIndex++;
		}//end running through discount counts
		//while printing through final prize counts
		System.out.printf("\n%s\n", "Prize counts:");
		while (localPrizeIndex < borrowedPrizeNames.length)
		{
			System.out.printf("%-35s%2s\n", borrowedPrizeNames[localPrizeIndex], borrowedPrizeCounts[localPrizeIndex]);
			localPrizeIndex++;
		}//end running through final prize counts
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	////////////
	//VR method
	////////////

	public static String getUserName(Scanner borrowedInput)
	{
		System.out.printf("\n%s", "Name: ");
		return borrowedInput.next();
	}
	public static char validateMainMenu(Scanner borrowedInput)
	{
		char localSelection;
		displayMainMenu();
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while (localSelection != MENU_CHARS[0] && localSelection != MENU_CHARS[1] && localSelection != MENU_CHARS[2])
		{//start validation
			System.out.printf("%s", "Please enter a valid option.");
			displayMainMenu();
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//end validation
		return localSelection;
	}//end validateMainMenu
	public static char validateItemMenu(Scanner borrowedInput, String[] borrowedItemNames, double[] borrowedItemPrices)
	{
		char localSelection;
		displayItemMenu(borrowedItemNames, borrowedItemPrices);
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while (localSelection != SUB_MENU_CHARS[0] && localSelection != SUB_MENU_CHARS[1] && localSelection != SUB_MENU_CHARS[2] && localSelection != SUB_MENU_CHARS[3])
		{//start validation
			System.out.printf("%s", "Please enter a valid option.");
			displayItemMenu(borrowedItemNames, borrowedItemPrices);
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//end validation
		return localSelection;
	}//end validateItemMenu
	public static String validateHowMany(Scanner borrowedInput)
	{
		int localHowMany;
		System.out.printf("\n%s\n", "How many are you ordering? ");
		localHowMany = borrowedInput.nextInt();
		while (localHowMany <= 0)
		{
			System.out.printf("%s\n", "You'll need to type a non-zero positive number. ");
			localHowMany = borrowedInput.nextInt();
		}
		return String.valueOf(localHowMany);
	}
	public static char validateDiscountMenu(Scanner borrowedInput, String[] borrowedDiscountNames, double[] borrowedDiscountRates)
	{
		char localSelection;
		displayDiscountMenu(borrowedDiscountNames, borrowedDiscountRates);
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while (localSelection != SUB_MENU_CHARS[0] && localSelection != SUB_MENU_CHARS[1] && localSelection != SUB_MENU_CHARS[2])
		{//start validation
			System.out.printf("%s", "Please enter a valid option.");
			displayDiscountMenu(borrowedDiscountNames, borrowedDiscountRates);
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//end validation
		return localSelection;
	}
}//end class

