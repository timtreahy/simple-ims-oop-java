package edu.cpt187.treahy.exercise3;

//Author:Tim Treahy
//Course:CPT 187
//Purpose: This is your pantry!
//Purpose: What items are available,
//Purpose: what prices are they?
//Start Date:1/28/2021

import java.util.Random;

public class Inventory 
{
	//initialize variables
	private final String[] ITEM_NAMES =  {"Premium Sod", "Special Sod", "Basic Sod", "Economic Sod"};
	private final double[] ITEM_PRICES = {7.95, 5.95, 3.95, 1.95};
	private final int[] STARTING_INSTOCK_COUNTS = {10, 18, 8, 3};
	private final String[] DISCOUNT_NAMES = {"Member", "Senior", "No Discount"};
	private final double[] DISCOUNT_RATES = {0.15, 0.25, 0.0};
	private final String[] PRIZE_NAMES = {"A Wizard of Earthsea", "Atlas Shrugged", "Jitterbug Perfume"};	private int[] inStockCounts = new int[ITEM_NAMES.length];
	private Random prizeGenerator = new Random();

	public Inventory () 
	{
	}

	////SET/////

	public void setReduceStock(int borrowedHowMany, int borrowedLastItemSelectedIndex)
	{//takes away stock after it has been ordered
		inStockCounts[borrowedLastItemSelectedIndex] = inStockCounts[borrowedLastItemSelectedIndex] - borrowedHowMany; 
	}
	public void setInStockCounts()
	{//set in stock counts
		inStockCounts = STARTING_INSTOCK_COUNTS;
	}

	////GET////

	public int[] getInStockCounts()
	{//return in stock counts
		return inStockCounts;
	}
	public String[] getItemNames()
	{//return item names
		return ITEM_NAMES;
	}
	public double[] getItemPrices()
	{//return item prices
		return ITEM_PRICES;
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
}//end class Inventory
