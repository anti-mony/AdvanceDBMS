/**
 * Created by Pragya, Jaison and Sushant on 11 November 2016
 * Two Phase Locking Protocol
 */

package TwoPhaseLock;

public class LockTable
{
	public int transid_WL;
	public int transid_RL;	
	public int trans_waiting_read;
	public int trans_waiting_write;

	//Constructor
	public LockTable()
	{		
		this.transid_WL = 0;
		this.transid_RL = 0;
		this.trans_waiting_read = 0;
		this.trans_waiting_write = 0;		
	}

	// Getter Methods

	public int getTransid_WL() {
		return transid_WL;
	}

	public int getTransid_RL() {
		return transid_RL;
	}

	public int getTrans_waiting_read() {
		return trans_waiting_read;
	}

	public int getTrans_waiting_write() {
		return trans_waiting_write;
	}

	//Setter Methods
	public void Set_transid_WL(int x) {
		this.transid_WL = x;
	}
	public void Set_trans_waiting_read(int x) {
		String temporary;
		temporary = Integer.toString(this.trans_waiting_read) + Integer.toString(x);
		this.trans_waiting_read = Integer.parseInt(temporary);
	}
	
	public void Set_transid_RL(int x) {
		String temporary;
		temporary = Integer.toString(this.transid_RL) + Integer.toString(x);
		this.transid_RL = Integer.parseInt(temporary);
	}
	
	public void Set_trans_waiting_write(int x) {
		String temporary;
		temporary = Integer.toString(this.trans_waiting_write) + Integer.toString(x);
		this.trans_waiting_write = Integer.parseInt(temporary);
	}

	//Degrade/Removal Methods
	public void replace_transid_RL(){
		this.transid_RL = 0;
	}

	public void replace_transid_WL(){
		this.transid_WL = 0;
	}

	public void replace_transid_waiting_RL(){
		this.trans_waiting_read = 0;
	}

	public void replace_transid_waiting__WL() {
		this.trans_waiting_write= 0;
	}
}

