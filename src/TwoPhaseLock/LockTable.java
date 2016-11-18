/**
 * Created by Pragya, Jaison and Sushant on 11 November 2016
 * Two Phase Locking Protocol
 */

package TwoPhaseLock;

class LockTable
{
    int transid_WL;
    int transid_RL;
    int trans_waiting_read;
    int trans_waiting_write;

	//Constructor
    LockTable() {
		this.transid_WL = 0;
		this.transid_RL = 0;
		this.trans_waiting_read = 0;
		this.trans_waiting_write = 0;		
	}

	// Getter Methods

    int getTransid_WL() {
        return transid_WL;
	}

    int getTransid_RL() {
        return transid_RL;
	}

    int getTrans_waiting_read() {
        return trans_waiting_read;
	}

    int getTrans_waiting_write() {
        return trans_waiting_write;
	}

	//Setter Methods
    void Set_transid_WL(int x) {
        this.transid_WL = x;
	}

    void Set_trans_waiting_read(int x) {
        String temporary;
		temporary = Integer.toString(this.trans_waiting_read) + Integer.toString(x);
		this.trans_waiting_read = Integer.parseInt(temporary);
	}

    void Set_transid_RL(int x) {
        String temporary;
		temporary = Integer.toString(this.transid_RL) + Integer.toString(x);
		this.transid_RL = Integer.parseInt(temporary);
	}

    void Set_trans_waiting_write(int x) {
        String temporary;
		temporary = Integer.toString(this.trans_waiting_write) + Integer.toString(x);
		this.trans_waiting_write = Integer.parseInt(temporary);
	}

	//Degrade/Removal Methods
    void replace_transid_RL() {
        this.transid_RL = 0;
	}

    void replace_transid_WL() {
        this.transid_WL = 0;
	}

    void replace_transid_waiting_RL() {
        this.trans_waiting_read = 0;
	}

    void replace_transid_waiting__WL() {
        this.trans_waiting_write= 0;
	}
}

