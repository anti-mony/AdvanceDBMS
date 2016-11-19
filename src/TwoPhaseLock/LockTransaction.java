/**
 * Created by Pragya, Jaison and Sushant on 11 November 2016
 * Two Phase Locking Protocol
 */

public class LockTransaction {
	
	/*
	 The fields to be stored in TT are, 
	 • Transaction_ID 
	 • Transaction_state (active, blocked/waiting, aborted/cancelled, committed) 
	 • Transaction_timestamp 
	 • List of items locked by the transaction
	 */
	
	public static int TS = 0; //count no of transaction
	public int transaction_timestamp; //transaction time stamp
	public String transaction_state; // transaction state
	public String items_locked; //count of items locked

	public String[] filedata = new String[100]; //store file data

	//Default constructor
	public LockTransaction() {

	}

	//Constructor to instantiate time stamp,state and no of items locked 
	public LockTransaction(String state, String items_locked) {
		this.transaction_timestamp = ++TS;
		this.transaction_state = state;
		this.items_locked = items_locked;
	}

	public void ProcessTransaction() {
		/*
		 The input file contains list of transactions,possible operations are:
		 b (begin transaction), r (read item), w (write item), e (end transaction).
		 of the form ' r1(X) ' where 1 is the transactionID, X is item name
		 */
		
		for (int i = 0; i < TwoPhaseLock.data.length; i++) {    // read data from input file and store in filedata[]
			filedata[i] = TwoPhaseLock.data[i];
		}
		int i = 0;
		while (filedata[i] != null) {
			
			switch (filedata[i].substring(0, 1))      // conditional switch on basis of the b,r,w,e 
			{
			case "b": 
				LockTransaction T = new LockTransaction("Active", "none");  //begin transaction 
				int tid = Integer.parseInt(filedata[i].substring(1,filedata[i].indexOf(";")));
				TwoPhaseLock.mapTransaction.put(tid, T);
				System.out.println("Start Transaction: T" + tid);
				
				break;

			case "r":
				tid = Integer.parseInt(filedata[i].substring(1,filedata[i].indexOf("(")).trim());
				LockTable L = new LockTable();
				L.Set_transid_RL(tid);
				String itemname = filedata[i].substring(
						filedata[i].indexOf("(") + 1, filedata[i].indexOf(")"));

				// check if itemname exists in LT- yes:check for read/write lock
				// No:insert into LT
				try{
					if (!TwoPhaseLock.mapLocks.containsKey(itemname)) {
						TwoPhaseLock.mapLocks.put(itemname, L);
						System.out.println("T" + tid + " has a read lock on item "+ itemname + '\n');
						TwoPhaseLock.mapTransaction.get(tid).setItems_locked(itemname); //item read locked
					} 
					else {
						for (String key : TwoPhaseLock.mapLocks.keySet()) {
	
							if (key.equals(itemname)) {
								
								if ((TwoPhaseLock.mapLocks.get(itemname).Get_transid_WL()) != 0) { //item has a write lock
									System.out.println("Item " + key + " is Writelocked and not available!" + '\n');
									enqueue(tid);
									TwoPhaseLock.mapTransaction.get(tid).setTrans_state("Blocked"); //state blocked
									TwoPhaseLock.mapLocks.get(itemname).Set_trans_waiting_write(tid);
								}
								if ((TwoPhaseLock.mapLocks.get(itemname).Get_transid_RL()) != 0) { //item had a read lock
									TwoPhaseLock.mapLocks.get(itemname).Set_transid_RL(tid);
									System.out.println("T" + tid+ " has a read lock on item "+ itemname + '\n');
									TwoPhaseLock.mapTransaction.get(tid).setItems_locked(itemname);
								}							
							}
	
						}
					}
	
					break;
				}catch(Exception e){ 
					if(e instanceof java.lang.NullPointerException){ //transaction not found
						System.out.println("TransactionID "+tid +" not found\n");
					}
					else
					System.out.println("\n Error!! "+ e.toString()); //any other error
				}
					
				

			case "w":
				String itemname1 = filedata[i].substring(filedata[i].indexOf("(") + 1, filedata[i].indexOf(")"));
				tid = Integer.parseInt(filedata[i].substring(1,filedata[i].indexOf("(")).trim());
				
				LockTable L1 = new LockTable();
				L1.Set_transid_WL(tid);
				try{
						if (!TwoPhaseLock.mapLocks.containsKey(itemname1)) { //item had a write lock
							TwoPhaseLock.mapLocks.put(itemname1, L1);
							System.out.println("T" + tid + " has a write lock on item "+ itemname1 + '\n');
						} else {
							for (String key : TwoPhaseLock.mapLocks.keySet()) {
								if (key.equals(itemname1)) {
									if ((TwoPhaseLock.mapLocks.get(itemname1).Get_transid_WL()) != 0) {
										int timestamp_requesting_trans = TwoPhaseLock.mapTransaction.get(tid).getTrans_timestamp();
										int timestamp_itemHolding_trans = TwoPhaseLock.mapLocks.get(itemname1).Get_transid_WL();
										if (timestamp_requesting_trans < timestamp_itemHolding_trans) {
											enqueue(tid);
											TwoPhaseLock.mapTransaction.get(tid).setTrans_state("Blocked");
											TwoPhaseLock.mapLocks.get(itemname1).Set_trans_waiting_write(tid);
										}
									} else {
										if ((TwoPhaseLock.mapLocks.get(itemname1)
												.Get_transid_RL()) != 0)
										// checking if read lock already exists
										{
											int len_transid_RL = (int) (Math.log10(TwoPhaseLock.mapLocks.get(itemname1).Get_transid_RL()) + 1);
											if (len_transid_RL < 2 && TwoPhaseLock.mapLocks.get(itemname1).Get_transid_RL() == tid) {
												System.out.println("T"+ tid + " is Upgrading readlock to writelock on item "+ itemname1 + '\n');
												TwoPhaseLock.mapLocks.get(itemname1).replace_transid_RL();
												TwoPhaseLock.mapLocks.get(itemname1).Set_transid_WL(tid);
												
											}
										}
		
									}
									// }
								}
							}
						}
						break;
					}catch(Exception e){
						if(e instanceof java.lang.NullPointerException){
							System.out.println("TransactionID "+tid +" not found\n"); //transaction not found
						}
						else
						System.out.println("\n Error!! "+ e.toString()); //any other error
					}
	
				case "e":
					tid = Integer.parseInt(filedata[i].substring(1, 2));
					try{
					if (TwoPhaseLock.mapTransaction.get(tid).getTrans_state().equals("Blocked")) {
						System.out.println("Transaction " + tid + " is Committed"
								+ '\n');
					}
					if (TwoPhaseLock.mapTransaction.get(tid).getTrans_state().equals("Aborted")) {
						System.out.println("Ignore!Transaction is Aborted");
					}
					if (TwoPhaseLock.mapTransaction.get(tid).getTrans_state().equals("Active")) {
	
						System.out.println("Transaction " + tid + " is Committed"
								+ '\n');
						release(tid);
						// Call release function to release all items held by
						// transaction
					}
	
					break;
				}catch(Exception e){
					if(e instanceof java.lang.NullPointerException){
						System.out.println("TransactionID "+tid +" not found\n"); //transaction not found
					}
					else
					System.out.println("\n Error!! "+ e.toString()); //any other error
				}
			}
			i++; 
		}
	}

	//get method locked item
	public String getItems_locked() {

		return this.items_locked;
	}

	// get method for transaction state
	public String getTrans_state() {

		return this.transaction_state;
	}

	//get method for timestamp
	public int getTrans_timestamp() {

		return this.transaction_timestamp;
	}
	
	//set method for item lock
	public void setItems_locked(String item) {
		if (this.items_locked.equals("none")) {
			this.items_locked = item;
		} else
			this.items_locked = this.items_locked + item;
	}

	//set method for transaction state
	public void setTrans_state(String state) {

		this.transaction_state = state;
	}

	//enqueue in the priority queue 
	public void enqueue(int tid) {
		TwoPhaseLock.priortyQ.add(tid);
	}

	//dequeue from the priority queue
	public void dequeue(int tid) {
		TwoPhaseLock.priortyQ.remove(tid);
	}

	//release locks from the transactions and Commit the transaction
	public void release(int tid) {
		String items_locked = TwoPhaseLock.mapTransaction.get(tid).getItems_locked();

		if (items_locked.equals("none")) {
			System.out.println("All items released");
		} else {
			TwoPhaseLock.mapTransaction.get(tid).replaceItems_locked();
			TwoPhaseLock.mapTransaction.get(tid).setItems_locked("none");
			TwoPhaseLock.mapTransaction.get(tid).setTrans_state("Committed");
			System.out.println("All items released by the transaction T" + tid);
			TwoPhaseLock.priortyQ.remove(tid);
		}
	}

	//No lock on the item
	private void replaceItems_locked() {
		// TODO Auto-generated method stub
		this.items_locked = "";
	}
}
