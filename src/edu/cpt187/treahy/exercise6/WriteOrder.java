//AUTHOR: Tim Treahy
//COURSE: CPT 187
//PURPOSE: Once you've decided that you want to purchase
//something, this will help finalize the purchase.
//This works like a memory that you can access later.
//STARTDATE: 2/18/2021

package edu.cpt187.treahy.exercise6;

import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WriteOrder {

	private boolean fileFoundFlag = false;
	private String masterFileName = "";
	private int recordCount = 0;
	
	public WriteOrder(String borrowedFileName)
	{
		masterFileName = borrowedFileName;
	}
	public void setWriteOrder(
			int borrowedItemID, 
			String borrowedItemName, 
			double borrowedItemPrice, 
			int borrowedQuantity,
			double borrowedOrderCost)
	{//start saving inventory orders

				try
				{//try to write to file
					PrintWriter filePW = new PrintWriter(
							new FileWriter(masterFileName, true));

					//write a record
					filePW.printf("%n%d\t%s\t%f\t%d\t%.2f%n", 
							borrowedItemID, 
							borrowedItemName, 
							borrowedItemPrice, 
							borrowedQuantity,
							borrowedOrderCost);
					fileFoundFlag = true;
					//close File
					filePW.close();
					//increment recordCount 
					recordCount++; 

				}//end try

				catch (IOException ex) 
				{//begin error catch
					fileFoundFlag = false;
				}//end error catch
	}
	
	public boolean getFileFoundFlag()
	{
		return fileFoundFlag;
	}
	public String getFileName()
	{
		return masterFileName;
	}
	public int getRecordCount()
	{
		return recordCount;
	}
}