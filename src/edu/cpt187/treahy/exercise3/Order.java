package edu.cpt187.treahy.exercise3;

//Author:Tim Treahy
//Course:CPT 187
//Purpose:This is the money aspect.
//Purpose: How should tax be calculated,
//Purpose: how does tax affect final price?
//Start Date:1/28/2021

public class Order 
{

	//initialize variables
	private final double TAX_RATE = .075;
	private int[] itemCounts;
	private int[] discountCounts;
	private int[] prizeCounts;
	private int discountType = 0;
	private String itemName = "";
	private double itemPrice = 0.0;
	private String discountName = "";
	private double discountRate = 0.0;
	private int howMany = 0;
	private int lastItemSelectedIndex = 0;
	private String prizeName = "";

	public Order ()
	{
	}

	////SET/////
	
	public void setLastItemSelectedIndex(char borrowedMenuSelection)
	{//set last item selected
		lastItemSelectedIndex = borrowedMenuSelection - 'A';
	}
	public void setItemName(String[] borrowedItemNames)
	{//set item name
		if (itemCounts==null)
		{//if the item array is empty, fill it
			itemCounts = new int [borrowedItemNames.length];
		}//set item name and increment item
		itemName = borrowedItemNames[lastItemSelectedIndex]; 
		itemCounts[lastItemSelectedIndex] ++;
	}
	public void setItemPrice(double[] borrowedItemPrices)
	{//set item price
		itemPrice = borrowedItemPrices[lastItemSelectedIndex];
	}
	public void setHowMany(String borrowedHowMany)
	{//set how many
		howMany = Integer.parseInt(borrowedHowMany);
	}
	public void setDiscountType(char borrowedMenuSelection)
	{//set discount type
		discountType = borrowedMenuSelection - 'A';
	}
	public void setDiscountName(String[] borrowedDiscountNames)
	{//set discount counts
		if (discountCounts == null)
		{//if the discount array is empty, fill it
			discountCounts = new int [borrowedDiscountNames.length];
		}//set discount name and increment discount
		discountName = borrowedDiscountNames[discountType]; 
		discountCounts[discountType] ++;
	}
	public void setDiscountRate(double[] borrowedDiscountRate)
	{//set discount rate
		discountRate = borrowedDiscountRate[discountType];
	}
	public void setPrizeName(String[] borrowedPrizeNames, int borrowedPrizeIndex)
	{//set prize name
		if (prizeCounts == null)
		{//if the prize array is empty, fill it
			prizeCounts = new int [borrowedPrizeNames.length];
		}//set prize name and increment prizes
		prizeName = borrowedPrizeNames[borrowedPrizeIndex]; 
		prizeCounts[borrowedPrizeIndex] ++;
	}
	public void setDecreaseInStock(Inventory borrowedInventoryObject)
	{//set decrease stock to remove items
		borrowedInventoryObject.setReduceStock(howMany, lastItemSelectedIndex);
	}

	////GET/////

	public int getInStockCount(int[] borrowedInStockCounts)
	{//return in stock counts
		return borrowedInStockCounts[lastItemSelectedIndex];
	}
	public String getItemName()
	{//return item name
		return itemName;
	}
	public double getItemPrice()
	{//return item price
		return itemPrice;
	}
	public int[] getItemCounts()
	{//return item counts
		return itemCounts;
	}
	public int getHowMany()
	{//return how many
		return howMany;
	}
	public String getDiscountName()
	{//return discount name
		return discountName;
	}
	public double getDiscountRate()
	{//return discount rate
		return discountRate;
	}
	public int[] getDiscountCounts()
	{//return discount count
		return discountCounts;
	}
	public double getDiscountAmt()
	{//return discount amount
		return (itemPrice * discountRate);
	}
	public double getDiscountPrice()
	{//return discount price
		return (itemPrice - getDiscountAmt());
	}
	public String getPrizeName()
	{//return prize name
		return prizeName;
	}
	public int[] getPrizeCounts()
	{//return prize count
		return prizeCounts;
	}
	public double getSubTotal()
	{//return sub total
		return (howMany * getDiscountPrice());
	}
	public double getTaxRate()
	{//return tax rate
		return TAX_RATE;
	}
	public double getTaxAmt()
	{//return tax amount
		return (TAX_RATE * getSubTotal());
	}
	public double getTotalCost()
	{//return total cost
		return (getSubTotal() + getTaxAmt());
	}
}//end Order class