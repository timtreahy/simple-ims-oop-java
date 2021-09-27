//AUTHOR: Tim Treahy
//COURSE: CPT 187
//PURPOSE: User Accounts will keep track of user accounts and passwords.
//Users will login using a username and password.
//STARTDATE: 2/18/2021

package edu.cpt187.treahy.exercise6;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.FileInputStream;


public class UserAccounts {
	private final int NOT_FOUND = -1;
	private final int RESET_VALUE = 0;
	private final int MAXIMUM_RECORDS = 50;
	private String[] userNames = new String[MAXIMUM_RECORDS];
	private String[] passwords = new String[MAXIMUM_RECORDS];
	private String masterFileName = "";
	private int recordCount = 0;
	private int searchedIndex = 0;
	
	public UserAccounts(String borrowedFileName)
	{
		masterFileName = borrowedFileName;
	}//END OF the non-default constructor UserAccounts
	
	//this setter sets UserAccountArrays
	public void setUserAccountArrays()
	{//Retrieve data from users file and assigns records to arrays
		
		//Reset local RecordCount
		recordCount = RESET_VALUE;

		try
		{//open file / extract data
			Scanner infile = new Scanner(new FileInputStream(masterFileName));

			while (infile.hasNext() == true && recordCount < MAXIMUM_RECORDS) 
			{//loop through file and assign each element to the specific array
				userNames[recordCount] = infile.next();
				passwords[recordCount] = infile.next(); 
				
				recordCount++;
			}//end while loop

			//Close File
			infile.close();

		}//End Try

		//Error catch
		catch (IOException ex)
		{// assign record count to -1 if file not found
			
			recordCount = NOT_FOUND;
		}//End Catch

	}//END OF set UserAccountArrays
	
	//this setter sets SearchedIndex
	public void setSearchedIndex(String borrowedUserName)
	{
		searchedIndex = getSeqSearch(borrowedUserName);
	}//END OF set SearchedIndex
	
	//overloaded set SearchedIndex
	public void setSearchedIndex(String borrowedUserName, String borrowedPassword)
	{
		searchedIndex = getSeqSearch(borrowedUserName);	
		//start of if username is not found
		if (searchedIndex >= RESET_VALUE && getPasswordMatch(borrowedPassword) == false)
		{
			searchedIndex = NOT_FOUND;
		}//END OF if username is not found
	}//END OF overloaded set SearchedIndex 
	
	//this setter sets WriteOneRecord
	public void setWriteOneRecord(String borrowedUserName,String borrowedPassword)
	{
		try
		{
			PrintWriter filePW = new PrintWriter(new FileWriter(masterFileName, true));
			filePW.printf("%n%s\t%s", borrowedUserName, borrowedPassword);
			filePW.close();
			recordCount++;
		}
		catch (IOException ex)
		{
			System.out.println("There was a problem writing the records");
		}//end catch
	}//END OF set WriteOneRecord
	
	//this getter gets Sequential Search
	public int getSeqSearch(String borrowedBorrowedUserName)
	{//recursively search the file for the subject entered by user
		//utilizes search logic
		int localIndex = 0;
		int localFound = NOT_FOUND;

		while (localIndex < recordCount)
		{//while we still have items to search
			if (userNames[localIndex].equalsIgnoreCase(borrowedBorrowedUserName))
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
	}//END OF get SeqSearch
	
	//this getter gets Password Match
	public boolean getPasswordMatch(String borrowedBorrowedPassword)
	{
		return borrowedBorrowedPassword.equals(passwords[searchedIndex]); 
	}//END OF get PasswordMatch
	
	//this getter gets FileName
	public String getFileName()
	{
		return masterFileName;
	}//END OF getFileName
	
	//this getter gets MaximumRecords
	public int getMaximumRecords()
	{
		return MAXIMUM_RECORDS;
	}//END OF getMaximumRecords
	
	//this getter gets RecordCount
	public int getRecordCount()
	{
		return recordCount;
	}//END OF getRecordCount
	
	//this getter gets SearchedIndex
	public int getSearchedIndex()
	{
		return searchedIndex;
	}//END OF getSearchedIndex
}
