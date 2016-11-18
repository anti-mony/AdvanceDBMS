/**
 * Created by Pragya, Jaison and Sushant on 11 November 2016
 * Two Phase Locking Protocol
 */

package TwoPhaseLock;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.io.*;

public class mains {
	public static HashMap<Integer, Transaction> transMap = new HashMap<Integer, Transaction>();
	public static HashMap<String, LockTable> lockMap = new HashMap<String, LockTable>();
	public static Queue<Integer> q = new PriorityQueue<Integer>();
	public static String[] data = new String[20];

	public static void main(String args[]) {

		mains obj1 = new mains();
		data = obj1.ReadFile();
		System.out.println("Implementation of Rigorous 2PL");
		Transaction T1 = new Transaction();
		T1.ReadTransactions();

	}

	public String[] ReadFile() {

		// reading file line by line in Java using BufferedReader
		//FileInputStream fis = null;
		//BufferedReader reader = null;
		String[] myarray;
		myarray = new String[100];
		try {
			FileReader fis =new FileReader("C:/Users/pragya/workspace/TwoPhaseLock/src/input.txt");
            BufferedReader reader = new BufferedReader(fis);
			
			int i = 0;
			String line = reader.readLine();
			

			while (line != null) {
				System.out.println("input : " + line);
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