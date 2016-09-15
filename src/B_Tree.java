import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Sushant,Pragya and Jaison on 15-Sep-16.
 * B-Tree
 * Insertion and Search (integers)
 */
class BNode {
    static int t;  //variable to determine order of tree
    protected BNode parent;  //parent of current node.
    int count; // number of keys in node
    int key[];  // array of key values
    BNode child[]; //array of references
    boolean leaf; //is node a leaf or not

    BNode(int t, BNode parent) {
        BNode.t = t;  //assign size
        this.parent = parent; //assign parent
        key = new int[2 * t - 1];  // array of proper size
        child = new BNode[2 * t]; // array of refs proper size
        leaf = true; // everynode is leaf at first;
        count = 0; //until we add keys later.
    }

    public int getValue(int index) {
        return key[index];
    }

    public BNode getChild(int index) {
        return child[index];
    }
}

class BTree {

    static int order; // order of tree
    BNode root;  //every tree has at least a root node

    public BTree(int order) {
        BTree.order = order;
        root = new BNode(order, null);
    }

    public BNode search(BNode root, int key) {
        int i = 0;//we always want to start searching the 0th index of node.

        while (i < root.count && key > root.key[i])//keep incrementing
        //in node while key >
        //current value.
        {
            i++;
        }

        if (i <= root.count && key == root.key[i])//obviously if key is in node
        //we went to return node.
        {
            return root;
        }

        if (root.leaf)//since we've already checked root
        //if it is leaf we don't have anything else to check
        {
            return null;
        } else//else if it is not leave recurse down through ith child
        {
            return search(root.getChild(i), key);
        }
    }

    public void split(BNode x, int i, BNode y) {
        BNode z = new BNode(order, null);//gotta have extra node if we are
        //to split.

        z.leaf = y.leaf;//set boolean to same as y

        z.count = order - 1;//this is updated size

        for (int j = 0; j < order - 1; j++) {
            z.key[j] = y.key[j + order]; //copy end of y into front of z

        }
        if (!y.leaf)//if not leaf we have to reassign child nodes.
        {
            for (int k = 0; k < order; k++) {
                z.child[k] = y.child[k + order]; //reassing child of y
            }
        }

        y.count = order - 1; //new size of y

        for (int j = x.count; j > i; j--)//if we push key into x we have
        {                 //to rearrange child nodes

            x.child[j + 1] = x.child[j]; //shift children of x
        }

        x.child[i + 1] = z; //reassign i+1 child of x

        for (int j = x.count; j > i; j--) {
            x.key[j + 1] = x.key[j]; // shift keys
        }

        x.key[i] = y.key[order - 1];//finally push value up into root.
        y.key[order - 1] = 0; //erase value where we pushed from

        for (int j = 0; j < order - 1; j++) {
            y.key[j + order] = 0; //'delete' old values
        }
        x.count++;  //increase count of keys in x
    }


    public void nonfullInsert(BNode x, int key) {
        if (search(x, key) != null) {
            return;
        }
        int i = x.count; //i is number of keys in node x
        if (x.leaf) {
            while (i >= 1 && key < x.key[i - 1])//here find spot to put key.
            {
                x.key[i] = x.key[i - 1];//shift values to make room

                i--;//decrement
            }

            x.key[i] = key;//finally assign value to node
            x.count++; //increment count of keys in this node now.

        } else {
            int j = 0;
            while (j < x.count && key > x.key[j])//find spot to recurse
            {                         //on correct child
                j++;
            }

            if (x.child[j].count == order * 2 - 1) {
                split(x, j, x.child[j]);//call split on node x's ith child
                if (key > x.key[j]) {
                    j++;
                }
            }
            nonfullInsert(x.child[j], key);//recurse
        }
    }

    void insert(BTree t, int key) {
        BNode r = t.root;//this method finds the node to be inserted as
        if (search(r, key) != null) {
            return;
        }

        //it goes through this starting at root node.
        if (r.count == 2 * order - 1)//if is full
        {
            BNode s = new BNode(order, null);//new node
            t.root = s;    //\
            s.leaf = false;//  \
            //   > this is to initialize node.
            s.count = 0;   //  /
            s.child[0] = r;///
            split(s, 0, r);//split root
            nonfullInsert(s, key); //call insert method
        } else
            nonfullInsert(r, key);//if its not full just insert it
    }


    void print(BNode n) {
        for (int i = 0; i < n.count; i++) {
            System.out.println(n.getValue(i) + " ");//this part prints root node
        }

        if (!n.leaf)//this is called when root is not leaf;
        {
            for (int j = 0; j <= n.count; j++)//in this loop we recurse
            {                  //to print out tree in
                if (n.getChild(j) != null) //preorder fashion.
                {              //going from left most
                    System.out.println();      //child to right most
                    print(n.getChild(j));     //child.
                }
            }
        }
    }

    public void SearchPrintNode(BTree T, int x) {
        BNode temp;
        temp = search(T.root, x);
        if (temp == null) {
            System.out.println("The Key does not exist in this tree");
        } else {
            print(temp);
        }
    }
}

public class B_Tree {
    public static void main(String[] args) {
        inputHelp iH = new inputHelp();
        String in;
        BTree tree = new BTree(iH.orderInput());//  B-Tree Tree with order  N is created.

        try {
            FileReader f = new FileReader("inp.txt");
            BufferedReader bR = new BufferedReader(f);
            while ((in = bR.readLine()) != null) {
                tree.insert(tree, Integer.parseInt(in));
            }
        } catch (IOException E) {
            System.out.println(E.toString());
        }
        tree.print(tree.root);
    }
}

class inputHelp {

    int orderInput() {
        int number;
        try (Scanner inp = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.println("Enter the order of tree");
                    number = inp.nextInt();
                    if (number <= 2)
                        throw new orderTooSmallException("Enter a number greater than 2");
                    return number;
                } catch (InputMismatchException E) {
                    System.out.println(E.toString());
                    System.out.print(" --> Please Enter an integer <--");
                    inp.next();
                } catch (orderTooSmallException E) {
                    System.out.println(E.toString());
                }
            }
        } catch (Exception E) {
            System.out.println(E.toString());
            System.out.println("Scanner fail, retrying");
            number = orderInput();
        }
        return number;
    }

    private class orderTooSmallException extends Exception {
        orderTooSmallException(String s) {
            super(s);
        }
    }
}
