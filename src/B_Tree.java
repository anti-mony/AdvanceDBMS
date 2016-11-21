import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Sushant,Pragya and Jaison on 15-Sep-16.
 * B-Tree
 * Insertion and Search (integers)
 */

class BNode {
    private static int t;  //determining the order of tree
    private BNode parent;  //parent of current node.
    int count; // number of values in node
    int key[];  // array of values
    BNode child[]; //array to store references
    boolean leaf; //leaf check

    BNode(int t, BNode parent) {
        BNode.t = t;  //assigning size
        this.parent = parent; //assigning parent
        key = new int[2 * t - 1];  // Max Size can be 2B -1
        child = new BNode[2 * t]; // Refrence Array
        leaf = true; // Default Leaf
        count = 0; //Default number of values 0.
    }

    int getValue(int index) {
        return key[index];
    }

    BNode getChild(int index) {
        return child[index];
    }
}

class BTree {

    private static int order; // Order of Tree
    BNode root;  //Root Node

    BTree(int order) {
        BTree.order = order;
        root = new BNode(order, null);   //null because root node has no parent
    }

    private int search(BNode root, int key) throws IndexOutOfBoundsException {
        /*
        Search, searches for a given key, in a given node and all it's keys.
        root: BNode, refers to the node where we begin start
        key: int, the value to be searched
        return: BNode, the node with key
         */
        int i = 0;
        while (i < root.count && key > root.key[i]) // Stops where the key is found
        {
            i++;
        }

        if (i <= root.count && key == root.key[i]) { //marks where the key is found
            return root.key[i];
        }

        if (root.leaf) { //if root is a leaf node, no need for further search
            return -1;
        } else {
            return search(root.getChild(i), key);
        }
    }

    private void split(BNode x, int i, BNode y) {
        /*
            If the bucket is full, we need to split it to insert new items
            x: BNode, Node after which split is to be made
            y: BNode, Node before which split is to be made
            i: int, where in the node the split is to be done.
        */
        BNode z = new BNode(order, null);

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


    private void nonfullInsert(BNode x, int key) {
        /*
        if the node is not full, we an directly insert keys w/o splitting
        x: BNode, root of tree in which key is inserted
        key: int, value to be inserted
        */

        if (search(x, key) > 0) {
            return;
        }

        int i = x.count;
        if (x.leaf) {
            while (i >= 1 && key < x.key[i - 1]) // to find the appropriate place to put the key
            {
                x.key[i] = x.key[i - 1];//push everything one up

                i--;
            }

            x.key[i] = key; //adding value to node.
            x.count++; //updating the node updates.

        } else {
            int j = 0;
            while (j < x.count && key > x.key[j]) //Recursing to get to the right place.
            {
                j++;
            }

            if (x.child[j].count == order * 2 - 1) {
                split(x, j, x.child[j]);  //splitting is full
                if (key > x.key[j]) {
                    j++;
                }
            }
            nonfullInsert(x.child[j], key);
        }
    }

    void insert(BTree t, int key) {
        /*
            Actual function, that inserts data into the specifies tree.
            t: BTree, To which the value has to be inserted.
            key: int, value to be inserted.
        */
        BNode r = t.root;

        if (r.count == 2 * order - 1)//if is full
        {
            BNode s = new BNode(order, null);//new node
            t.root = s;
            s.leaf = false;
            s.count = 0;
            s.child[0] = r;
            split(s, 0, r);
            nonfullInsert(s, key);
        } else
            nonfullInsert(r, key);
    }

    void print(BNode n, BufferedWriter bW) throws IOException {

        for (int i = 0; i < n.count; i++) {
            bW.write(String.valueOf(n.getValue(i)) + "  ");
            System.out.print(n.getValue(i) + " ");// Printing out the value of node
        }

        if (!n.leaf) // if the node is not a leaf
        {
            /*
            Printing the tree in pre-order, i.e. from left to right
            */
            for (int j = 0; j <= n.count; j++) {
                if (n.getChild(j) != null) {
                    bW.newLine();
                    System.out.println();
                    print(n.getChild(j), bW);
                }
                }
        }
    }

    public void SearchPrintNode(BTree T, int x) throws IOException {
        int temp = -1;
        temp = search(T.root, x);
        if (temp < 0) {
            System.out.println("The Key does not exist in this tree");
        } else {
            System.out.println();
            System.out.println("Integer Found : " + temp);
        }
    }
}

public class B_Tree {
    public static void main(String[] args) {
        inputHelp iH = new inputHelp();  //Helper Class to take input from user
        String in;
        BTree tree = new BTree(iH.orderInput());//  B-Tree Tree with order  N is created.
        int menu = 0, val, sval;
        try {
            FileReader f = new FileReader("inp.txt");        //Reading input from File
            BufferedReader bR = new BufferedReader(f);
            FileWriter fw = new FileWriter("output.txt");
            BufferedWriter bW = new BufferedWriter(fw);
            while ((in = bR.readLine()) != null) {
                tree.insert(tree, Integer.parseInt(in));        //Inserting the values into the BTree
            }

            while (menu != -1) {
                System.out.println(" What do you want to do! ");
                System.out.println("1. Insert");
                System.out.println("2. Print Tree");
                System.out.println("3. Search");
                System.out.println("4. Enter -1 to exit");
                menu = inputHelp.inp.nextInt();
                switch (menu) {
                    case 1:
                        System.out.println("Enter the value you want to insert");
                        val = inputHelp.inp.nextInt();
                        tree.insert(tree, val);
                        System.out.println("Successfully Inserted");
                        break;
                    case 2:
                        tree.print(tree.root, bW);
                        break;
                    case 3:
                        System.out.println("Enter the value you want to insert");
                        sval = inputHelp.inp.nextInt();
                        tree.SearchPrintNode(tree, sval);  //Printing the Tree to console
                        break;
                    case -1:
                        break;
                    default:
                        System.out.println("Please enter a valid input");
                }
            }
        } catch (IOException E) {
            System.out.println(E.toString());
        }
    }
}

class inputHelp {
    static Scanner inp = new Scanner(System.in);
    int orderInput() {
        /*
        * To take order of tree as input
        * No, parameters
        * returns: int, order of B-Tree
        */
        int number;
        try {
            while (true) {
                try {
                    System.out.println("Enter the order (>=128) of tree");
                    number = inp.nextInt();                         //Asking for order (integer)
                    if (number < 128)
                        throw new orderTooSmallException("Enter a number strictly greater than 128");          //Order should not be less than 100
                    return number;
                } catch (InputMismatchException E) {         //Checking for integer input
                    System.out.println(E.toString());
                    System.out.print(" --> Please Enter an integer <--");
                    inp.nextInt();
                } catch (orderTooSmallException E) {        // Order too small
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
        /**
        Custom Exception to check for inputs less than or equal to 2
        */
        orderTooSmallException(String s) {
            super(s);
        }
    }
}
