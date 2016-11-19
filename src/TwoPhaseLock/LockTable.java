package TwoPhaseLock;

class LockTable
{
	private int transid_WL;
	private int transid_RL;
	private int trans_waiting_read;
	private int trans_waiting_write;

	//Constructor
	LockTable()
	{		
		this.transid_WL = 0;
		this.transid_RL = 0;
		this.trans_waiting_read = 0;
		this.trans_waiting_write = 0;		
	}


	//........................Get.............................................
	int Get_transid_RL() {
		
			return this.transid_RL;
		}

	int Get_transid_WL() {
		
			return this.transid_WL;
		}

	int Get_trans_waiting_read() {
		
			return this.trans_waiting_read;
		}

	int Get_trans_waiting_write() {
		
			return this.trans_waiting_write;
		}
	//..........................Set.............................................
	void Set_transid_WL(int x) {
		
			this.transid_WL=x;
		}
	public void Set_trans_waiting_read(int x) {
		
		String y;
		y = Integer.toString(this.trans_waiting_read) + Integer.toString(x);
		this.trans_waiting_read = Integer.parseInt(y);
		}

	void Set_transid_RL(int x) {
		
		String y;
		y = Integer.toString(this.transid_RL) + Integer.toString(x);
		this.transid_RL = Integer.parseInt(y);
		}

	void Set_trans_waiting_write(int x) {
		
		String y;
		y = Integer.toString(this.trans_waiting_write) + Integer.toString(x);
		this.trans_waiting_write= Integer.parseInt(y);
		}
	//.....................Remove..............................................
	void replace_transid_RL()
	{
		this.transid_RL = 0;
	}
	public void replace_transid_WL()
	{
		this.transid_WL = 0;
	}
	public void replace_transid_waiting_RL()
	{
		this.trans_waiting_read = 0;
	}
	public void replace_transid_waiting__WL()
	{
		this.trans_waiting_write= 0;
	}
}
