//AUTHOR: Tim Treahy
//COURSE: CPT 187
//PURPOSE: Retrieve data concerning item names,
//item ids, discounts, etc. Then calculate taxes,
//totals, etc.
//STARTDATE: 2/11/2021

package edu.cpt187.treahy.exercise5;

public class Order
{
	//initialize variables
	private final double TAX_RATE = .075;
	private int discountType = 0;
	private int itemID = 0;
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

	public void setLastItemSelectedIndex(int borrowedSearchIndex)
	{//set last item selected
		lastItemSelectedIndex = borrowedSearchIndex;
		//lastItemSelectedIndex = borrowedSearchIndex - 'A';
	}
	public void setItemID(int[] borrowedItemIDs)
	{
		itemID = borrowedItemIDs[lastItemSelectedIndex];
	}
	public void setItemName(String[] borrowedItemNames)
	{//set item name
		itemName = borrowedItemNames[lastItemSelectedIndex]; 
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
		discountName = borrowedDiscountNames[discountType]; 
	}
	public void setDiscountRate(double[] borrowedDiscountRate)
	{//set discount rate
		discountRate = borrowedDiscountRate[discountType];
	}
	public void setPrizeName(String[] borrowedPrizeNames, int borrowedPrizeIndex)
	{//set prize name
		prizeName = borrowedPrizeNames[borrowedPrizeIndex]; 
	}
	public void setDecreaseInStock(Inventory borrowedInventoryObject)
	{//set decrease stock to remove items
		borrowedInventoryObject.setReduceStock(howMany);
	}

	////GET/////

	public int getInStockCount(int[] borrowedInStockCounts)
	{//return in stock counts
		return borrowedInStockCounts[lastItemSelectedIndex];
	}
	public int getItemID()
	{
		return itemID;
	}
	public String getItemName()
	{//return item name
		return itemName;
	}
	public double getItemPrice()
	{//return item price
		return itemPrice;
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