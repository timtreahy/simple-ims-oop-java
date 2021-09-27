package edu.cpt187.treahy.exercise2;

import java.util.Random;

public class Order {

	private final String[] ITEM_NAMES =  {"Premium Sod", "Special Sod", "Basic Sod"};
	private final double[] ITEM_PRICES = {7.95, 5.95, 1.95};
	private final String[] DISCOUNT_NAMES = {"Member", "Senior", "No Discount"};
	private final double[] DISCOUNT_RATES = {0.15, 0.25, 0.0};
	private final String[] PRIZE_NAMES = {"The Prophet", "Do Not Say That We Have Nothing", "Reincarnation Blues"};
	private final double TAX_RATE = .075;

	private int[] itemCounts = new int[ITEM_NAMES.length];
	private int[] discountCounts;// = [DISCOUNT_NAMES.length];
	private int[] prizeCounts = new int[PRIZE_NAMES.length];
	private int itemSelection = 0;
	private int discountType = 0;
	private String itemName = "";
	private double itemPrice = 0.0;
	private String discountName = "";
	private double discountRate = 0.0;
	private int howMany = 0;
	private Random prizeGenerator = new Random();
	private String prizeName = "";

	public Order ()
	{
	}

	//SET methods
	public void setItemSelection(char borrowedMenuSelection)
	{//calculation for itemSelection as int
		itemSelection = borrowedMenuSelection-'A';
	}

	public void setItemName()
	{//selection structure for itemName, increments selection for item counts as well
		if (itemCounts==null)
		{//if the item array is empty, fill it
			itemCounts = new int [ITEM_NAMES.length];
		}//set item name and increment item
		itemName = ITEM_NAMES[itemSelection]; 
		itemCounts[itemSelection] ++;	
	}

	public void setItemPrice()
	{//selection structure for itemSelection to set itemPrice
		itemPrice = ITEM_PRICES[itemSelection];
	}

	public void setHowMany(String borrowedHowMany)
	{//set value for howMany
		howMany = Integer.parseInt(borrowedHowMany);
	}

	public void setDiscountType(char borrowedMenuSelection)
	{//calculation for discountType as int
		discountType = borrowedMenuSelection - 'A';
	}

	public void setDiscountName()
	{//set value for discountName and increment discountCount
		if (discountCounts == null)
		{//if the discount array is empty, fill it
			discountCounts = new int [DISCOUNT_NAMES.length];
		}//set discount name and increment discount
		discountName = DISCOUNT_NAMES[discountType]; 
		discountCounts[discountType] ++;	
	}

	public void setDiscountRate()
	{//set value for discountRate based on discountType
		discountRate = DISCOUNT_RATES[discountType];
	}

	public void setPrizeName()
	{//use random number to assign a prize, set prize name and increment prize count
		int localIndex = 0;
		localIndex = getRandomNumber();
		prizeName = PRIZE_NAMES[localIndex];
		prizeCounts[localIndex]++;
	}


	//GET methods

	//this method will return the itemName
	public String getItemName()
	{
		return itemName;
	}
	//this method will return the itemPrice
	public double getItemPrice()
	{
		return itemPrice;
	}
	//this method will return the howMany
	public int getHowMany()
	{
		return howMany;
	}
	//this method will return the discountName
	public String getDiscountName()
	{
		return discountName;
	}
	//this method will return the discountRate
	public double getDiscountRate()
	{
		return discountRate;
	}
	//this method will return the discountAmt
	public double getDiscountAmt()
	{
		return (itemPrice * discountRate);
	}
	//this method will return the discountPrice
	public double getDiscountPrice()
	{
		return (itemPrice - getDiscountAmt());
	}
	//this method will return the subTotal
	public double getSubTotal()
	{
		return (howMany * getDiscountPrice());
	}
	//this method will return the taxRate
	public double getTaxRate()
	{
		return TAX_RATE;
	}
	//this method will return the taxAmt
	public double getTaxAmt()
	{
		return (TAX_RATE * getSubTotal());
	}
	//this method will return the totalCost
	public double getTotalCost()
	{
		return (getSubTotal() + getTaxAmt());
	}
	//this method will return the itemNames
	public String[] getItemNames()
	{
		return ITEM_NAMES;
	}
	//this method will return the itemPrices
	public double[] getItemPrices()
	{
		return ITEM_PRICES;
	}
	//this method will return the discountNames
	public String[] getDiscountNames()
	{
		return DISCOUNT_NAMES;
	}
	//this method will return the discountRates
	public double[] getDiscountRates()
	{
		return DISCOUNT_RATES;
	}
	//this method will return the itemCounts
	public int[] getItemCounts()
	{
		return itemCounts;
	}
	//this method will return the discountCounts
	public int[] getDiscountCounts()
	{
		return discountCounts;
	}
	//this method will return the prizeCounts
	public int[] getPrizeCounts()
	{
		return prizeCounts;
	}
	//this method will return the prizeName
	public String getPrizeName()
	{
		return prizeName;
	}
	//this method will return the prizeNames list
	public String[] getPrizeNames()
	{
		return PRIZE_NAMES;
	}
	//this method will return a random number between 0 and the size of the prize list
	public int getRandomNumber()
	{
		return prizeGenerator.nextInt(PRIZE_NAMES.length);
	}

}//end class Order
