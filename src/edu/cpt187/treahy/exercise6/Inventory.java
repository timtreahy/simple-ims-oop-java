//AUTHOR: Tim Treahy
//COURSE: CPT 187
//PURPOSE: The user will be able to look into
//inventories and make requests. We will organize
//and sort the inventory.
//STARTDATE: 2/18/2021

package edu.cpt187.treahy.exercise6;

import java.util.Random;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;


public class Inventory 
{
	//initialize variables

	private final String[] DISCOUNT_NAMES = {"Member", "Senior", "No Discount"};
	private final double[] DISCOUNT_RATES = {0.15, 0.25, 0.0};
	private final String[] PRIZE_NAMES = {"A Wizard of Earthsea", "Atlas Shrugged", "Jitterbug Perfume"};

	private final int MAX_RECORDS = 35;
	private final int NOT_FOUND = -1;
	private final int ONE = 1;
	private final int RESET_VALUE = 0;

	private int[] itemIDs = new int[MAX_RECORDS];
	private String[] itemNames = new String[MAX_RECORDS];
	private double[] itemPrices = new double[MAX_RECORDS];
	private int[] orderQuantities = new int[MAX_RECORDS];
	private double[] orderTotals = new double[MAX_RECORDS];
	private int[] inStockCounts = new int[MAX_RECORDS];

	private int itemSearchIndex = 0;
	private int recordCount = 0;

	private Random prizeGenerator = new Random();

	public Inventory () 
	{//start inventory constructor
	}//end inventory 

	////SET/////

	public void setReduceStock(int borrowedHowMany)
	{//takes away stock after it has been ordered
		inStockCounts[itemSearchIndex] = inStockCounts[itemSearchIndex] - borrowedHowMany; 
	}
	public void setLoadItems(String borrowedFileName)
	{//load items from a file		
		try
		{//try to open file
			recordCount = RESET_VALUE;
			Scanner infile = new Scanner (new FileInputStream(borrowedFileName));
			while (infile.hasNext() == true && (recordCount < MAX_RECORDS))
			{//while there are still items to read in the file
				//read a record item by item
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next();
				itemPrices[recordCount] = infile.nextDouble();
				inStockCounts[recordCount] = infile.nextInt();
				recordCount ++;
			}
			//close file
			infile.close();
			//sort items by bubble sort
			setBubbleSort();
		}//end try
		catch (IOException ex)
		{//identify if there is an exception
			recordCount = NOT_FOUND;
		}
	}//finish set load items

	//OVERLOADED setLoadItems including borrowedSize from master file
	public void setLoadItems(String borrowedFileName, int borrowedSize)
	{//load items from a master file
		try
		{//try to open file
			recordCount = RESET_VALUE;
			Scanner infile = new Scanner (new FileInputStream(borrowedFileName));
			while (infile.hasNext() == true && (recordCount < MAX_RECORDS && borrowedSize < MAX_RECORDS))
			{//while there are still items to read in the file
				//read a record item by item
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next();
				itemPrices[recordCount] = infile.nextDouble();
				orderQuantities[recordCount] = infile.nextInt();
				orderTotals[recordCount] = infile.nextDouble();
				recordCount ++;
			}
			//close file
			infile.close();
			//sort items by bubble sort
			setBubbleSort();
		}//end try
		catch (IOException ex)
		{//identify if there is an exception
			recordCount = NOT_FOUND;
		}
	}//finish set load items

	
	public void setSearchIndex(int borrowedTargetID)
	{//what value are we checking against
		itemSearchIndex = getSearchResults(borrowedTargetID);
	}
	public void setBubbleSort()
	{
		{//start setBubbleSort
					
			int localLast = RESET_VALUE;
			int localIndex = RESET_VALUE;
			boolean swapValues = false;

			//assign last to last element index position 
			localLast = recordCount - ONE;
			
			while (localLast > RESET_VALUE)
			{//while array size is greater than 0

				localIndex = RESET_VALUE;
				swapValues = false;

				while (localIndex < localLast)
				{//until index reaches last element of the array
					if (itemIDs[localIndex] > itemIDs[localIndex + ONE])
					{//determine if the array id needs to be swapped
						setSwapArrayElements(localIndex);
						swapValues = true;
					}//end array swap 
					localIndex++;
				}//end reach last element of array


				if (swapValues == false)
				{//after array is sorted
					localLast = RESET_VALUE;
				}//end array sort
				else
				{//decrement last
					localLast--;
				}//end decrement last
			}//end loop for array size > 0
		}
	}
	public void setSwapArrayElements(int borrowedIndex)
	{//start setSwapArrayElements
								
				int localID = RESET_VALUE;
				String localName = ""; 
				double localPrice = RESET_VALUE; 
				int localInStockCount = RESET_VALUE;
				int localQuantity = RESET_VALUE; 
				double localOrderTotals = RESET_VALUE;
				
				//swap ids
				localID = itemIDs[borrowedIndex];
				itemIDs[borrowedIndex] = itemIDs[borrowedIndex + ONE];
				itemIDs[borrowedIndex + ONE] = localID;
				
				//swap names 
				localName = itemNames[borrowedIndex];
				itemNames[borrowedIndex] = itemNames[borrowedIndex + ONE];
				itemNames[borrowedIndex + ONE] = localName; 

				//swap prices 
				localPrice = itemPrices[borrowedIndex];
				itemPrices[borrowedIndex] = itemPrices[borrowedIndex + ONE];
				itemPrices[borrowedIndex + ONE] = localPrice; 

				//swap in stock
				localInStockCount = inStockCounts[borrowedIndex];
				inStockCounts[borrowedIndex] = inStockCounts[borrowedIndex + ONE];
				inStockCounts[borrowedIndex + ONE] = localInStockCount; 

				//swap quantities
				localQuantity = orderQuantities[borrowedIndex];
				orderQuantities[borrowedIndex] = orderQuantities[borrowedIndex + ONE];
				orderQuantities[borrowedIndex + ONE] = localQuantity; 

				//swap order totals 
				localOrderTotals = orderTotals[borrowedIndex];
				orderTotals[borrowedIndex] = orderTotals[borrowedIndex + ONE];
				orderTotals[borrowedIndex + ONE] = localOrderTotals; 
	}
	

	////GET////

	public int[] getInStockCounts()
	{//return in stock counts
		return inStockCounts;
	}
	public int[] getItemIDs()
	{//return item names
		return itemIDs;
	}
	public String[] getItemNames()
	{//return item names
		return itemNames;
	}
	public double[] getItemPrices()
	{//return item prices
		return itemPrices;
	}
	public String[] getDiscountNames()
	{//return discount names
		return DISCOUNT_NAMES;
	}
	public double[] getDiscountRates()
	{//return discount rates
		return DISCOUNT_RATES;
	}
	public int[] getOrderQuantities()
	{
		return orderQuantities;
	}
	public double[] getOrderTotals()
	{
		return orderTotals;
	}
	public String[] getPrizeNames()
	{//return prize names
		return PRIZE_NAMES;
	}
	public int getRandomNumber()
	{//return a random number seeded to prize names
		return prizeGenerator.nextInt(PRIZE_NAMES.length);
	}
	public int getMaxRecords()
	{//return max records
		return MAX_RECORDS;
	}
	public int getItemSearchIndex()
	{//return item search index
		return itemSearchIndex;
	}
	public int getRecordCount()
	{//return record count
		return recordCount;
	}
	public double getGrandTotal()
	{
		int localIndex = RESET_VALUE;
		double localGrandTotal = RESET_VALUE;
		while (localIndex < orderTotals.length)
		{
			localGrandTotal = localGrandTotal + orderTotals[localIndex];
			localIndex ++;
		}
		return localGrandTotal;
	}
	public int getSearchResults(int borrowedBorrowedTargetID)
	{//utilizes search logic
		
		int localFirst = RESET_VALUE;
		int localMid = RESET_VALUE;
		int localLast = RESET_VALUE;
		boolean localFound = false;
		
		localLast = recordCount - ONE;
		while (localFirst <= localLast && localFound == false)
		{
			localMid = (localFirst + localLast) / 2;
			if (itemIDs[localMid] == borrowedBorrowedTargetID)
			{
				localFound = true;
			}
			else
			{
				if (itemIDs[localMid] < borrowedBorrowedTargetID)
				{
					localFirst = localMid + ONE;
				}
				else
				{
					localLast = localMid - ONE;
				}
			}
		}
		if (localFound == false)
		{
			localMid = NOT_FOUND;
		}
		return localMid;
	}
}//end class Inventory
