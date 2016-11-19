
/**
 * Created by Pragya, Jaison and Sushant on 11 November 2016
 * Two Phase Locking Protocol
 */

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.io.*;




/** Class with main method to read the input from file
 * and call the function for excecution of Transactions
 */
public class TwoPhaseLock {
	
	public static HashMap<Integer, LockTransaction> mapTransaction = new HashMap<Integer, LockTransaction>(); //Hash map for Transaction table TT
	public static HashMap<String, LockTable> mapLocks = new HashMap<String, LockTable>();  //Hash map for Lock table LT
	
	public static Queue<Integer> priortyQ = new PriorityQueue<Integer>(); //PriorityQueue to store the waiting transactions
	public static String[] data = new String[20]; // Strings from the input file

	public static void main(String args[]) {

		TwoPhaseLock obj1 = new TwoPhaseLock();
		data = obj1.InputReader();    //Reading the input file
		
		System.out.println("\nStarting Two Phase Locking with Wait and Die method for concurrency control \n");
		
		LockTransaction T1 = new LockTransaction();
		T1.ProcessTransaction();      //Start reading the transaction

	}

	public String[] InputReader() {
		
		/*
		 Opens the input file and reads line by line
		 and stores the contents in myarray
		 */
		
		String[] myarray;
		myarray = new String[100];
		
		try {
			
			FileReader fis =new FileReader("C:/Users/pragya/workspace/test/src/input.txt");
            BufferedReader reader = new BufferedReader(fis);
			
			int i = 0;
			String line = reader.readLine();
			System.out.println("Input file: \n");
			while (line != null) {
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