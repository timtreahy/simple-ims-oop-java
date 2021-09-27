package edu.cpt187.treahy.exercise2;

//Author:Tim Treahy
//Course:CPT 187
//Purpose: A virtual environment for creating sod orders.
//Start Date:1/21/2021

import java.util.Scanner;

public class MainClass {

	public static final char[] MENU_CHARS = {'A', 'Q'};
	public static final String[] MENU_OPTIONS = {"Create Order", "Quit"};
	public static final char[] SUB_MENU_CHARS = {'A', 'B', 'C'};


	public static void main(String[] args)
	{
		//initialize scanner object
		Scanner input = new Scanner (System.in);

		//initialize variables
		String userName = "";
		char menuSelection = 'a';

		//create new Order from order class
		Order currentOrder = new Order();

		//welcome banner
		displayWelcomeBanner();
		//userName
		userName = getUserName(input);
		//menuSelection
		menuSelection = validateMainMenu(input);

		//while not quit
		while (menuSelection != 'Q')
		{
			currentOrder.setItemSelection(validateItemMenu(input, currentOrder.getItemNames(), currentOrder.getItemPrices()));
			currentOrder.setItemName();
			currentOrder.setItemPrice();
			currentOrder.setHowMany(validateHowMany(input));
			currentOrder.setDiscountType(validateDiscountMenu(input, currentOrder.getDiscountNames(), currentOrder.getDiscountRates()));
			currentOrder.setDiscountName();
			currentOrder.setDiscountRate();
			currentOrder.setPrizeName();
			displayOrderReport(currentOrder.getItemName(), currentOrder.getItemPrice(), currentOrder.getHowMany(), currentOrder.getDiscountName(), currentOrder.getDiscountRate(), currentOrder.getDiscountAmt(), currentOrder.getDiscountPrice(), currentOrder.getSubTotal(), currentOrder.getTaxRate(), currentOrder.getTaxAmt(), currentOrder.getTotalCost(), currentOrder.getPrizeName());
			menuSelection = validateMainMenu(input);
		}//end while

		if (currentOrder.getTotalCost() > 0.0)
		{
			displayFinalReport(userName, currentOrder.getItemNames(), currentOrder.getItemCounts(), currentOrder.getDiscountNames(), currentOrder.getDiscountCounts(), currentOrder.getPrizeNames(), currentOrder.getPrizeCounts());
		}//end display

		displayFarewellMessage();

		//close scanner
		input.close();

	}//end main


	////////////
	//VOID method
	////////////

	public static void displayWelcomeBanner()
	{
		System.out.printf("\n%s\n", "Welcome to Sod LLC.");
		System.out.printf("%s\n", "We hope you enjoy being an employee!");
		System.out.printf("%s\n", "Make your way through the order menus.");
		System.out.printf("%s\n", "Follow the prompts to move through the selection menus.");
		System.out.printf("%s\n", "Select to begin, select items, select a discount,");
		System.out.printf("%s\n", "then count how many.");
		System.out.printf("%s\n\n", "You'll be assigned a prize at the end.");

	}

	public static void displayFarewellMessage()
	{
		System.out.printf("\n%s\n\n", "It's been a pleasure.");
		System.out.printf("%s\n", "Quote of the day:");
		System.out.printf("%s\n", "No more gods and no more kings.");
		System.out.printf("%s\n", "We volunteer to be our own masters.");
	}

	public static void displayMainMenu()
	{
		int localIndex = 0;
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s\n", "MAIN MENU");
		//print each item under main menu
		while (localIndex < MENU_OPTIONS.length)
		{
			System.out.printf("%-4s%s\n", MENU_CHARS[localIndex], MENU_OPTIONS[localIndex]);
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
			System.out.printf("%-4s%-15s%s%-10.2f\n", SUB_MENU_CHARS[localIndex], borrowedItemNames[localIndex], "$ ", borrowedItemPrices[localIndex]);
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
			System.out.printf("%-4s%-15s%s%-10.1f\n", SUB_MENU_CHARS[localIndex], borrowedDiscountNames[localIndex], "% ", borrowedDiscountRates[localIndex]*100);
			localIndex++;
		}
		System.out.printf("%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "Enter your selection:");
	}//end discount menu

	public static void displayOrderReport(String borrowedItemName, double borrowedItemPrice, int borrowedHowMany, String borrowedDiscountName, double borrowedDiscountRate, double borrowedDiscountAmt, double borrowedDiscountPrice, double borrowedSubTotal, double borrowedTaxRate, double borrowedTax, double borrowedTotalCost, String borrowedPrizeName)
	{
		//Order Report
		System.out.printf("\n\n\n%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.printf("%-8s\n", "ORDER REPORT");
		System.out.printf("%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.printf("%-25s%2s%-17s\n", "Item Name", "", borrowedItemName);
		System.out.printf("%-26s%2s%15.2f\n\n", "Item Price", "$", borrowedItemPrice);
		System.out.printf("%-25s%2s%-17s\n", "Discount Name", "", borrowedDiscountName);
		System.out.printf("%-26s%15.1f%2s\n", "Discount Rate", (borrowedDiscountRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n", "Discount Amount", "$", borrowedDiscountAmt);
		System.out.printf("%-26s%2s%15.2f\n\n", "Discounted Price", "$", borrowedDiscountPrice);
		System.out.printf("%-26s%2s%15d\n\n", "Quantity", "", borrowedHowMany);
		System.out.printf("%-26s%2s%15.2f\n", "Subtotal", "$", borrowedSubTotal);
		System.out.printf("%-26s%15.1f%2s\n", "Tax Rate", (borrowedTaxRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n", "Tax Amount", "$", borrowedTax);
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
		while (localItemIndex < borrowedItemNames.length)
		{
			System.out.printf("%-35s%2s\n", borrowedItemNames[localItemIndex], borrowedItemCounts[localItemIndex]);
			localItemIndex++;
		}
		System.out.printf("\n%s\n", "Discount counts:");
		while (localDiscountIndex < borrowedDiscountNames.length)
		{
			System.out.printf("%-35s%2s\n", borrowedDiscountNames[localDiscountIndex], borrowedDiscountCounts[localDiscountIndex]);
			localDiscountIndex++;
		}
		System.out.printf("\n%s\n", "Prize counts:");
		while (localPrizeIndex < borrowedPrizeNames.length)
		{
			System.out.printf("%-35s%2s\n", borrowedPrizeNames[localPrizeIndex], borrowedPrizeCounts[localPrizeIndex]);
			localPrizeIndex++;
		}
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
		char localSelection = 'a';
		displayMainMenu();
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while (localSelection != MENU_CHARS[0] && localSelection != MENU_CHARS[1])
		{//start validation
			System.out.printf("%s", "Please enter a valid option.");
			displayMainMenu();
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//end validation
		return localSelection;
	}//end validateMainMenu


	public static char validateItemMenu(Scanner borrowedInput, String[] borrowedItemNames, double[] borrowedItemPrices)
	{
		char localSelection = 'a';
		displayItemMenu(borrowedItemNames, borrowedItemPrices);
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while (localSelection != SUB_MENU_CHARS[0] && localSelection != SUB_MENU_CHARS[1] && localSelection != SUB_MENU_CHARS[2])
		{//start validation
			System.out.printf("%s", "Please enter a valid option.");
			displayItemMenu(borrowedItemNames, borrowedItemPrices);
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//end validation
		return localSelection;
	}//end validateItemMenu

	//VR method
	public static String validateHowMany(Scanner borrowedInput)
	{
		int localHowMany = 0;
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
		char localSelection = 'a';
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


