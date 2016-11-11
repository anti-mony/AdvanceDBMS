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

public class mains {
	static HashMap<Integer, Transaction> transMap = new HashMap<>();
	static HashMap<String, LockTable> lockMap = new HashMap<>();
	static Queue<Integer> q = new PriorityQueue<>();
	static String[] data = new String[20];

	public static void main(String args[]) {

		mains obj1 = new mains();
		data = obj1.ReadFile();
		System.out.println("Implementation of Rigorous 2PL");
		Transaction T1 = new Transaction();
		T1.ReadTransactions();

	}

	private String[] ReadFile() {

		// reading file line by line in Java using BufferedReader
		FileInputStream fis;
		BufferedReader reader;
		String[] myarray;
		myarray = new String[20];
		try {
			fis = new FileInputStream("input.txt");
			reader = new BufferedReader(new InputStreamReader(fis));
			int i = 0;
			String line = reader.readLine();

			while (line != null) {
				myarray[i] = line;
				line = reader.readLine();
				i++;
			}
			reader.close();
		}

		catch (Exception e) {
			System.out.println("Error");
		}
		return myarray;
	}
}
