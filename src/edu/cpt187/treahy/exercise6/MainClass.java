//AUTHOR: Tim Treahy
//COURSE: CPT 187
//PURPOSE: The user loads items from the inventory, and
//the user is able to make purchases based on listed
//items. The user works their way through menus, and the
//system remembers what has been purchased.
//STARTDATE: 2/18/2021

package edu.cpt187.treahy.exercise6;

import java.util.Scanner;



public class MainClass {

	public static final char[] MENU_CHARS = {'A', 'B', 'Q'};
	public static final String[] MENU_OPTIONS = {"Login", "Create an Account", "Quit"};
	public static final char[] FILE_MENU_CHARS = {'A', 'B', 'R'};
	public static final String[] FILE_MENU_OPTIONS = {"Load Inventory", "Create Order", "Return to Main Menu"};
	public static final char[] SUB_MENU_CHARS = {'A', 'B', 'C', 'D'};
	public static final String INVENTORY_FILE_NAME = "MasterInventoryFile.dat";
	public static final String ACCOUNTS_FILE_NAME = "MasterUserFile.dat";

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
		//create new User Accounts from currentUser class
		UserAccounts currentUser = new UserAccounts(ACCOUNTS_FILE_NAME);
		//welcome banner
		displayWelcomeBanner();
		//menuSelection
		menuSelection = validateMenuSelection(input);

		//start of while loop for menuSelection != Q
		while (menuSelection != 'Q')
		{
			currentUser.setUserAccountArrays();
			userName = getUserName(input);
			//start of selection structure for menuSelection != A
			if (menuSelection != 'A')
			{
				currentUser.setSearchedIndex(userName);
				//start of selection structure for currentUser.getSearchedIndex() >= 0
				if (currentUser.getSearchedIndex() >= 0)
				{
					displayAccountResults(userName);
				}//END OF if (currentUser.getSearchedIndex() >= 0)
				else
				{
					currentUser.setWriteOneRecord(userName,  getPassword(input));
					displayAccountResults();
				}//END OF else ! (currentUser.getSearchedIndex() >= 0)
			}//END OF if (menuSelection != A)
			else
			{
				currentUser.setSearchedIndex(userName, getPassword(input));
				//start of selection structure for currentUser.getSearchedIndex() < 0
				if (currentUser.getSearchedIndex() < 0)
				{
					displayLoginError();
				}//END OF if (currentUser.getSearchedIndex() < 0), go to menuValidation
				else
				{
					menuSelection = validateFileSelection(input);
					//start of while loop for menuSelection != R
					while (menuSelection != 'R')
					{
						//start of selection structure for menuSelection == A
						if (menuSelection == 'A')
						{
							currentInventory.setLoadItems(getFileName(input));
							//start of selection structure for currentInventory.getRecordCount <= 0
							if (currentInventory.getRecordCount() <= 0)
							{
								displayFileError();
							}//END OF if (currentInventory.getRecordCount <= 0), go to validateFileSelection
							else
							{
								displayRecordReport(currentInventory.getRecordCount());
							}//END OF else ! (currentInventory.getRecordCount <= 0), go to validateFileSelection
						}//END OF if (menuSelection == 'A'), go to validateFileSelection
						else
						{
							currentInventory.setSearchIndex(validateSearchValue(input));
							//start of if currentInventory.getItemSearchIndex() < 0
							if (currentInventory.getItemSearchIndex() < 0)
							{
								displayNotFound();
							}//END OF if (currentInventory.getItemSearchIndex() < 0), go to validateFileSelection
							else
							{
								currentOrder.setLastItemSelectedIndex(currentInventory.getItemSearchIndex());
								currentOrder.setItemID(currentInventory.getItemIDs());
								currentOrder.setItemPrice(currentInventory.getItemPrices());
								currentOrder.setItemName(currentInventory.getItemNames());
								currentOrder.setHowMany(validateHowMany(input));
								//start of if inStockCount is available == howMany
								if (currentOrder.getInStockCount(currentInventory.getInStockCounts()) < currentOrder.getHowMany())
								{
									displayOutOfStock();
								}//END OF if inStockCount is available != howMany, go to validateFileSelection
								else
								{
									currentOrder.setDiscountType(
											validateDiscountMenu(
													input, 
													currentInventory.getDiscountNames(), 
													currentInventory.getDiscountRates()));
									currentOrder.setDiscountName(currentInventory.getDiscountNames());
									currentOrder.setDiscountRate(currentInventory.getDiscountRates());
									currentOrder.setDecreaseInStock(currentInventory);
									currentOrder.setPrizeName(
											currentInventory.getPrizeNames(),
											currentInventory.getRandomNumber());
									orders.setWriteOrder(
											currentOrder.getItemID(),
											currentOrder.getItemName(),
											currentOrder.getItemPrice(),
											currentOrder.getHowMany(),
											currentOrder.getTotalCost());
									//start of if currentOrder.getDiscountRate() > 0.0
									if (currentOrder.getDiscountRate() > 0.0)
									{
										displayOrderReport(
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
												currentOrder.getInStockCount(
														currentInventory.getInStockCounts()));	
									}//END OF if currentOrder.getDiscountRate() > 0.0
									else
									{
										displayOrderReport(
												userName,
												currentOrder.getItemName(),
												currentOrder.getItemPrice(),
												currentOrder.getHowMany(),
												currentOrder.getSubTotal(),
												currentOrder.getTaxRate(),
												currentOrder.getTaxAmt(),
												currentOrder.getTotalCost(),
												currentOrder.getPrizeName(),
												currentOrder.getInStockCount(
														currentInventory.getInStockCounts()));
									}//END OF else ! currentOrder.getDiscountRate > 0.0
								}//END OF else ! inStockCount is available == howMany
							}//END OF else ! (currentInventory.getItemSearchIndex() < 0)
						}//END OF else ! (menuSelection == A), go to validateFileSelection
						menuSelection = validateFileSelection(input);
					}//END OF while loop for menuSelection != R
				}//END OF else ! (currentUser.getSearchedIndex() < 0)
			}//END OF else ! (menuSelection != A), go to validateMenuSelection
			menuSelection = validateMenuSelection(input);
		}//END OF while (menuSelection != Q)
		currentInventory.setLoadItems(orders.getFileName(), orders.getRecordCount());
		//start of if orders.getRecordCount > 0
		if (orders.getRecordCount() > 0)
		{
			displayFinalReport(
					currentInventory.getItemIDs(),
					currentInventory.getItemNames(),
					currentInventory.getItemPrices(),
					currentInventory.getOrderQuantities(),
					currentInventory.getOrderTotals(),
					currentInventory.getRecordCount(),
					currentInventory.getGrandTotal());
		}//END OF if order.getRecordCount > 0
		displayFarewellMessage();
	}//END OF main
	
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
	
	//start displayFileMenu
	public static void displayFileMenu()
	{
		int localIndex = 0;
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s\n", "FILE MENU");
		//print each item under main menu
		while (localIndex < FILE_MENU_OPTIONS.length)
		{
			System.out.printf("%s%s%s\n", FILE_MENU_CHARS[localIndex], " for ", FILE_MENU_OPTIONS[localIndex]);
			localIndex++;
		}
		System.out.printf("%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "Enter your selection:");
	}//END OF displayFileMenu
	
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
		System.out.printf("\n%s", "Please make your selection here:");
	}//end discount menu


	/////PROGRAM ANNOUNCEMENTS//////
	public static void displayAccountResults()
	{
			System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.printf("%s\n", "ACCOUNT RESULTS");
			System.out.printf("%s\n", "New account created");
			System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");	
	}
	public static void displayAccountResults(String userName)
	{
		System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("%s\n", "ACCOUNT RESULTS");
		System.out.printf("%s%s%s\n", "Account not created: username, ", userName, ", already exists");
		System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	public static void displayLoginError()
	{
			System.out.printf("\n%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.printf("%s\n", "LOGIN ERROR");
			System.out.printf("%s\n", "Username and/or Password is incorrect");
			System.out.printf("%s\n", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");	
	}
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
	public static void displayFileError()
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
		System.out.printf("%-25s%2s%-17s\n\n", "Customer Name:", "", userName);
		System.out.printf("%-25s%2s%-17s\n", "Item Name:", "", borrowedItemName);
		System.out.printf("%-26s%2s%15.2f\n\n", "Item Price:", "$", borrowedItemPrice);
		System.out.printf("%-26s%2s%15d\n\n", "Quantity:", "", borrowedHowMany);
		System.out.printf("%-26s%2s%15.2f\n", "Subtotal:", "$", borrowedSubTotal);
		System.out.printf("%-26s%17.1f%2s\n", "Tax Rate:", (borrowedTaxRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n\n", "Tax Amount:", "$", borrowedTaxAmt);
		System.out.printf("%-26s%2s%15.2f\n\n", "Order Total:", "$", borrowedTotalCost);
		System.out.printf("%-25s%2s%-17s\n\n", "Prize:", "", borrowedPrizeName);
		System.out.printf("%s%d%s%s%s", "Buy more now: Only ", borrowedInStockCount, "", borrowedItemName, "s left in-stock!");
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
		System.out.printf("%-25s%2s%-17s\n\n", "Customer Name:", "", userName);
		System.out.printf("%-25s%2s%-17s\n", "Item Name:", "", borrowedItemName);
		System.out.printf("%-26s%2s%15.2f\n\n", "Item Price:", "$", borrowedItemPrice);
		System.out.printf("%-25s%2s%-17s\n", "Discount Name:", "", borrowedDiscountName);
		System.out.printf("%-26s%17.1f%2s\n", "Discount Rate:", (borrowedDiscountRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n", "Discount Amount:", "$", borrowedDiscountAmt);
		System.out.printf("%-26s%2s%15.2f\n\n", "Discounted Price:", "$", borrowedDiscountPrice);
		System.out.printf("%-26s%2s%15d\n\n", "Quantity:", "", borrowedHowMany);
		System.out.printf("%-26s%2s%15.2f\n", "Subtotal:", "$", borrowedSubTotal);
		System.out.printf("%-26s%17.1f%2s\n", "Tax Rate:", (borrowedTaxRate*100), "%");
		System.out.printf("%-26s%2s%15.2f\n", "Tax Amount:", "$", borrowedTaxAmt);
		System.out.printf("%-26s%2s%15.2f\n", "Order Total:", "$", borrowedTotalCost);
		System.out.printf("%-25s%2s%-17s\n", "Prize:", "", borrowedPrizeName);
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
		System.out.printf("\n%-5s%-25s%-8s%-4s%-8s", "ID", "NAME", "PRICE", "QTY", "TOTAL");
		while (localIndex < borrowedRecordCount)
		{
			System.out.printf("\n%-5d%-25s%s%-7.2f%-3d%s%4.2f", 
					borrowedItemIDs[localIndex], 
					borrowedItemNames[localIndex], 
					"$ ", 
					borrowedItemPrices[localIndex],
					borrowedOrderQuantities[localIndex], 
					"$ ", 
					borrowedOrderTotals[localIndex]);
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
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "Enter your username: ");
		return borrowedInput.next();
	}
	public static String getPassword(Scanner borrowedInput)
	{
		System.out.printf("\n%s", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.printf("\n%s", "Enter your password: ");
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
	public static char validateMenuSelection(Scanner borrowedInput)
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
	}//end validateMenuSelection
	public static char validateFileSelection(Scanner borrowedInput)
	{
		char localSelection = 'a';
		displayFileMenu();
		localSelection = borrowedInput.next().toUpperCase().charAt(0);
		while (localSelection != FILE_MENU_CHARS[0] && localSelection != FILE_MENU_CHARS[1] && localSelection != FILE_MENU_CHARS[2])
		{//start validation
			System.out.printf("%s", "Please enter a valid option.");
			displayFileMenu();
			localSelection = borrowedInput.next().toUpperCase().charAt(0);
		}//end validation
		return localSelection;
	}//end validateFileSelection
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



