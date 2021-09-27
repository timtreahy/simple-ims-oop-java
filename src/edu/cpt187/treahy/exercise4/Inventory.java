package edu.cpt187.treahy.exercise4;


//Author:Tim Treahy
//Course:CPT 187
//Purpose: This is how we check what inventory is available. 
//Start Date:2/4/2021

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
	private final int RESET_VALUE = 0;

	private int[] itemIDs = new int[MAX_RECORDS];
	private String[] itemNames = new String[MAX_RECORDS];
	private double[] itemPrices = new double[MAX_RECORDS];
	private int[] inStockCounts = new int[MAX_RECORDS];

	private int itemSearchIndex = 0;
	private int recordCount = 0;

	private Random prizeGenerator = new Random();

	public Inventory () 
	{
	}

	////SET/////

	public void setReduceStock(int borrowedHowMany)
	{//takes away stock after it has been ordered
		inStockCounts[itemSearchIndex] = inStockCounts[itemSearchIndex] - borrowedHowMany; 
	}
	public void setLoadItems(String borrowedFileName)
	{

		try
		{//try to open file
			Scanner infile = new Scanner (new FileInputStream(borrowedFileName));
			recordCount = RESET_VALUE;
			while (infile.hasNext() == true && (recordCount < MAX_RECORDS))
			{//while there are still items to read in the file
				//read a record item by item
				itemIDs[recordCount] = infile.nextInt();
				itemNames[recordCount] = infile.next();
				itemPrices[recordCount] = infile.nextDouble();
				inStockCounts[recordCount] = infile.nextInt();
				recordCount ++;
			}
			infile.close();
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


	////GET////

	public int[] getInStockCounts()
	{//return in stock counts
		return inStockCounts;
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
	public int getSearchResults(int borrowedBorrowedTargetID)
	{//utilizes search logic
		int localIndex = 0;
		int localFound = NOT_FOUND;

		while (localIndex < recordCount)
		{//while we still have items to search
			if (borrowedBorrowedTargetID == itemIDs[localIndex])
			{//if the items match
				localFound = localIndex;
				localIndex = recordCount;
			}
			else
			{//keep searching
				localIndex++;
			}
		}
		return localFound;
	}

}//end class Inventory
