 Two Phase Locking Protocol 

This program implements the behaviour of the two-phase locking(2PL) protocol for concurenncy control, with the wound wait method for dealing with deadlock.


The input is the file which has each line as a single transaction operation. The opssible operations are: b(begin transaction), r(read item) and e(end transation). Each operation will be followed by an integer transaction id and data item between parentheses.

 Usage
 
Add he set of transactions to the file "transactions.txt", in the source directory. 
Complile TwoPhaseLock.java using javac or any other java IDE. 
Run the TwoPhaseLock class.
** No Arguments Passed via command line ** 

 Design and Implementation

1.Data structures keep of transactions(transaction table) : transaction id, transaction timestamp, transaction state (active,blocked,aborted), list of items locked by the transaction
and
lock(lock table) : item name, lock state(read lock or write lock),transaction id for the transaction holding the lock,list of transaction ids for transactions waiting for the item to be unlocked
 
2.A transaction is created in the transaction table when a begin operation(b) is processes with active state.
 
3.Before processing a read opration, appropriate read lock(X) is processed and lock table is updated. If the item X is already locked by a conflicting write lock, the transaction is either :(i) blocked or (ii)aborted if wound-wait protocol processes so.If item is already locked by a non-conflicting read lock, transaction is added to the list of transaction that hold read lock on the requested item.
 
4.Before processing a write operation, appropriate write lock is processed.If the item is already locked by a conflicting read or write lock the trasaction is either(i)blocked,waiting for transaction to resume (ii)aborted, subsequent operations are ignored.If item is already locked by a non-conflicting read lock, transaction is added to the list of transaction that hold write lock on the requested item.
 
5.If the transaction reaches its end(e) operation successfully,it should be committed.The process of commiting releases any lock it hold and state changes to commited. 