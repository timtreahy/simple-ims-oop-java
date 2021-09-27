//AUTHOR: Tim Treahy
//COURSE: CPT 187
//PURPOSE: The user loads items from the inventory, and
//the user is able to make purchases based on listed
//items. The user works their way through menus, and the
//system remembers what has been purchased.
//STARTDATE: 2/11/2021

package edu.cpt187.treahy.exercise5;

import java.util.Scanner;



public class MainClass {

	public static final char[] MENU_CHARS = {'A', 'B', 'Q'};
	public static final String[] MENU_OPTIONS = {"Load Inventory", "Create Order", "Quit"};
	public static final char[] SUB_MENU_CHARS = {'A', 'B', 'C', 'D'};
	public static final String INVENTORY_FILE_NAME = "MasterOrderFile.dat";
	
	public static void main(String[] args)
	{
		
		//initialize variables
		String userName = "";
		char menuSelection;
		
		//initialize scanner object
		Scanner input = new Scanner (System.in);
		//create new Order from order class
		Order currentOrder = new Order();
		//create new Inventory from inventory class
		Inventory currentInventory = new Inventory();
		//create new Write Order from write order class
		WriteOrder orders = new WriteOrder(INVENTORY_FILE_NAME);
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
				currentInventory.setLoadItems(getFileName(input));
				if (currentInventory.getRecordCount() <= 0)
				{//no records observed
					displayNotOpen();
				}
				else
				{//show how many records observed
					displayRecordReport(currentInventory.getRecordCount());
				}
			}//end if menuSelection == 'A'
			else
			{//access inventory and place order
				currentInventory.setSearchIndex(validateSearchValue(input));
				if (currentInventory.getItemSearchIndex() < 0)
				{//you searched for an item that doesnt exist
					displayNotFound();
				}
				else
				{
					currentOrder.setLastItemSelectedIndex(currentInventory.getItemSearchIndex());
					currentOrder.setItemID(currentInventory.getItemIDs());
					currentOrder.setItemName(currentInventory.getItemNames());
					currentOrder.setItemPrice(currentInventory.getItemPrices());
					currentOrder.setHowMany(validateHowMany(input));
					if (currentOrder.getInStockCount(currentInventory.getInStockCounts()) < currentOrder.getHowMany())
					{//if inventory is not sufficient for amount requested. will kick user back to main menu
						displayOutOfStock();
					}
					else
					{//user is ordering
						currentOrder.setDiscountType(
								validateDiscountMenu(
										input, 
										currentInventory.getDiscountNames(), 
										currentInventory.getDiscountRates()));
						currentOrder.setDiscountName(currentInventory.getDiscountNames());
						currentOrder.setDiscountRate(currentInventory.getDiscountRates());
						currentOrder.setDecreaseInStock(currentInventory);
						currentOrder.setPrizeName(currentInventory.getPrizeNames(),	currentInventory.getRandomNumber());
						orders.setWriteOrder(
								currentOrder.getItemID(), 
								currentOrder.getItemName(), 
								currentOrder.getItemPrice(), 
								currentOrder.getHowMany(), 
								currentOrder.getTotalCost());						
						if (currentOrder.getDiscountRate() > 0.0)
						{//the user selected a discount option
							displayOrderReport
							(
									userName,
									currentOrder.getItemName(), 
									currentOrder.getItemPrice(), 
									currentOrder.getHowMany(), 
									currentOrder.getDiscountName(), 
									currentOrder.getDiscountRate(), 
									currentOrder.getDiscountAmt(), 
									currentOrder.getDiscountPrice(), 
									currentOrder.getSubTotal(), 
									currentOrder.getTaxRate(), 
									currentOrder.getTaxAmt(), 
									currentOrder.getTotalCost(), 
									currentOrder.getPrizeName(),
									currentOrder.getInStockCount(currentInventory.getInStockCounts())
									);
						}
						else
						{//the user did not select a discount option
							displayOrderReport
							(
									userName,
									currentOrder.getItemName(), 
									currentOrder.getItemPrice(), 
									currentOrder.getHowMany(),  
									currentOrder.getSubTotal(), 
									currentOrder.getTaxRate(), 
									currentOrder.getTaxAmt(), 
									currentOrder.getTotalCost(), 
									currentOrder.getPrizeName(),
									currentOrder.getInStockCount(currentInventory.getInStockCounts())
									);
						}//end of order report
					}//end of user ordering
				}//end of access inventory
			}
			menuSelection = validateMainMenu(input); //end of loop validation

		}//end while

		if (orders.getRecordCount() > 0)
		{//did the user successfully order, if so print receipt
			currentInventory.setLoadItems(orders.getFileName(), orders.getRecordCount());
			displayFinalReport
			(
					currentInventory.getItemIDs(),
					currentInventory.getItemNames(),
					currentInventory.getItemPrices(),
					currentInventory.getOrderQuantities(),
					currentInventory.getOrderTotals(),
					currentInventory.getRecordCount(),
					currentInventory.getGrandTotal());
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
			System.out.printf("%-4s%-15s%10.1f%s\n", SUB_MENU_CHARS[localIndex], borrowedDiscountNames[localIndex], borrowedDiscountRates[localIndex]*100, " %");
			localIndex++;
		}
		System.out.printf("%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "Enter your selection:");
	}//end discount menu


	/////PROGRAM ANNOUNCEMENTS//////
	public static void displayRecordReport(int borrowedRecordCount)
	{
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s\n", "RECORD REPORT");
		System.out.printf("%d%s\n", borrowedRecordCount, " records processed.");
		System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayOutOfStock()
	{
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s\n", "OUT OF STOCK ERROR");
		System.out.printf("%s\n", "The quantity entered is greater than the quantity in-stock");
		System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayNotOpen()
	{
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s\n", "FILE ERROR");
		System.out.printf("%s\n", "The file named was not found or could not be opened");
		System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	}
	public static void displayNotFound()
	{
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s\n", "NOT FOUND ERROR");
		System.out.printf("%s\n", "The search value entered was not found");
		System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

	}
	//display order report if discount rate is not greater than 0
	public static void displayOrderReport
	(
			String userName,
			String borrowedItemName, 
			double borrowedItemPrice, 
			int borrowedHowMany, 
			double borrowedSubTotal, 
			double borrowedTaxRate, 
			double borrowedTaxAmt, 
			double borrowedTotalCost, 
			String borrowedPrizeName,
			int borrowedInStockCount
			)
	{
		//Order Report
		System.out.printf("\n\n\n%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.printf("%-8s\n", "ORDER REPORT");
		System.out.printf("%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
		System.out.printf("%-25s%2s%-17s\n", "Item Name", "", borrowedItemName);
		System.out.printf("%-26s%2s%15.2f\n\n", "Item Price", "$", borrowedItemPrice);
		System.out.printf("%-26s%2s%15d\n\n", "Quantity", "", borrowedHowMany);
		System.out.printf("%-26s%2s%15.2f\n", "Subtotal", "$", borrowedSubTotal);
		System.out.printf("%-26s%16.1f%3s\n", "Tax Rate", (borrowedTaxRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n", "Tax Amount", "$", borrowedTaxAmt);
		System.out.printf("%-26s%2s%15.2f\n", "Order Total", "$", borrowedTotalCost);
		System.out.printf("%-25s%2s%-17s\n", "Prize", "", borrowedPrizeName);
		System.out.printf("%s%d%s%s", "Buy more now: Only ", borrowedInStockCount, borrowedItemName, "s left in-stock!");
		System.out.printf("%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
	}
	//display order report if the discount rate is greater than 0
	public static void displayOrderReport
	(
			String userName,
			String borrowedItemName, 
			double borrowedItemPrice, 
			int borrowedHowMany, 
			String borrowedDiscountName, 
			double borrowedDiscountRate, 
			double borrowedDiscountAmt, 
			double borrowedDiscountPrice, 
			double borrowedSubTotal, 
			double borrowedTaxRate, 
			double borrowedTaxAmt, 
			double borrowedTotalCost, 
			String borrowedPrizeName,
			int borrowedInStockCount
			)
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
		System.out.printf("%s%-2d%s%s\n", "Buy more now: Only ", borrowedInStockCount, borrowedItemName, "s left in-stock!");
		System.out.printf("%-60s\n", "~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~ ~~~~");
	}
	public static void displayFinalReport
	(
			int[] borrowedItemIDs,
			String[] borrowedItemNames,
			double[] borrowedItemPrices,
			int[] borrowedOrderQuantities,
			double[] borrowedOrderTotals,
			int borrowedRecordCount,
			double borrowedGrandTotal)
	{
		int localIndex = 0;
		
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "FINAL REPORT");
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%-5s%-25s%-8s%-3s%-8s", "ID", "NAME", "PRICE", "QTY", "TOTAL");
		while (localIndex < borrowedItemIDs.length)
		{
			System.out.printf("\n%-5d%-25s%s%-7.2f%-3d%s%4.2f", borrowedItemIDs[localIndex], borrowedItemNames[localIndex], "$ ", borrowedItemPrices[localIndex], borrowedOrderQuantities[localIndex], "$ ", borrowedOrderTotals[localIndex]);
			localIndex++;
		}
		System.out.printf("\n\n%s", "GRAND TOTAL");
		System.out.printf("\n%s%7.2f", "$", borrowedGrandTotal);
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
	public static String getFileName(Scanner borrowedInput)
	{
		String localInput = "";	
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s", "Enter the file name with extension (i.e. file.txt): ");
		localInput = borrowedInput.next();
		return localInput;
	}
	public static int validateSearchValue(Scanner borrowedInput)
	{
		int localSearchValue = 0;

		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s", "Enter the search value: ");
		localSearchValue = borrowedInput.nextInt();
		while (localSearchValue < 0)
		{//enter a number great than 0
			System.out.printf("%s\n", "You must enter a number greater than ZERO");
			localSearchValue = borrowedInput.nextInt();
		}
		return localSearchValue;
	}
	public static char validateMainMenu(Scanner borrowedInput)
	{
		char localSelection = 'a';
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



