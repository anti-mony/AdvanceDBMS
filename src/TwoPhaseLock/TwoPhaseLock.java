package TwoPhaseLock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by Pragya, Jaison and Sushant on 11 November 2016
 * Two Phase Locking Protocol, with Wait-Die Method
 * Reads Transactions from a file and processes them accordingly.
 */

public class TwoPhaseLock {

	/**
	 * Main method class which reads the input from file
	 * and call the function to process transactions.
	 */

	static HashMap<Integer, LockTransaction> mapTransaction = new HashMap<Integer, LockTransaction>(); //Hash map for Transaction table TT
	static HashMap<String, LockTable> mapLocks = new HashMap<String, LockTable>();  //Hash map for Lock table LT

	static Queue<Integer> priortyQ = new PriorityQueue<>(); //PriorityQueue to store the waiting transactions
	static String[] data = new String[20]; // Strings from the input file

	public static void main(String args[]) {

		TwoPhaseLock obj1 = new TwoPhaseLock();
		data = obj1.InputReader("transactions.txt");    //Reading the input file
		
		System.out.println("\nStarting Two Phase Locking with Wait and Die method for concurrency control \n");
		
		LockTransaction T1 = new LockTransaction();
		T1.ProcessTransaction();      //Start reading the transaction

	}

	private String[] InputReader(String inp) {
		
		/*
		 Reads the input file i.e. a set of transactions, to be fed to the Transaction Processor.
		 inp: String, input file name containing transactions.
		 return: String, the output file as a single character array, or a string.
		 */
		int chck = 0;
		Scanner S = new Scanner(System.in);
		System.out.println("Do you want to view the input file! 1/0 ");
		chck = S.nextInt();
		String[] myarray;
		myarray = new String[100];
		
		try {
			FileReader fis = new FileReader(inp);
			//FileReader fis =new FileReader("C:/Users/pragya/workspace/test/src/input.txt");
			BufferedReader reader = new BufferedReader(fis);
			
			int i = 0;
			String line = reader.readLine();
			if (chck == 1)
				System.out.println("Input file: \n");
			while (line != null) {
				if (chck == 1)
					System.out.println(line);
				myarray[i] = line;
				line = reader.readLine();
				i++;
			}
			reader.close();
		}
		catch (Exception e) {
			System.out.println("Error"+ e.toString());
		}
		return myarray;
	}
}