import java.io.*;
import java.util.Random;

/**
 * Created by Sushant on 14-Aug-16.
 * Binary Search Tree
 * Insert, Search
 * Using File IO
 */

/* Structure of Node */
class bTreeNode{
    /*
    Node Class for Binary Search Tree

    data: integer, data to placed into the tree
    left: type-btreeNode, to refer to the next left node.
    right: type-btreeNode, to refer to the next right node.

    */
    int data;
    bTreeNode left;
    bTreeNode right;

    /*Constructor, adds value to a new node */
    public bTreeNode(int data) {
        this.data = data;
        /* Sets the next left and right node to NULL*/
        this.left = null;
        this.right = null;
    }
}

public class bTree {

    public static void main(String[] args) {
        /*
        Main function!
        Formation of Tree, Insertion of values
        and Search for data.
         */
        String in;
        String infile="inp.txt";
        String outFile="out.txt";
        bTreeNode root = new bTreeNode(50000); //Creating the root node.
        bTree tree = new bTree();
        tree.createInput(infile,100000,10000);  /* Creating Input from file */
        /* Inserting 10000 Integers into the Tree */
        try {
            FileReader f =new FileReader(infile);
            BufferedReader bR = new BufferedReader(f);
            while((in = bR.readLine()) != null){
                tree.insert(root,Integer.parseInt(in));
            }
        }
        catch(IOException E){
            System.out.println(E.toString());
        }

        System.out.println("Writing the tree to a file!");
        tree.printInOrder(root);  //Print the tree to stdout
        tree.printTree(root,outFile); /*printing the tree in inorder */
        tree.search(15,root); /*Searching for a number */
    }

    public void insert(bTreeNode node, int value) {
        /* Inserts a new node into the tree

            node: type-bTreeNode, generally root of the tree
            value: Integer, Value to be inserted into the tree

             returns: void
         */

        if (value < node.data) {
            if (node.left != null) {
                insert(node.left, value);
            } else {
//                System.out.println(value + " added");
                node.left = new bTreeNode(value);
            }
        } else if (value > node.data) {
            if (node.right != null) {
                insert(node.right, value);
            } else {
//                System.out.println(value + " added");
                node.right = new bTreeNode(value);
            }
        }
    }

    public void printInOrder(bTreeNode root){
        /*
        Display the tree in Inorder Format (Left, Root, Right) to System.out
        (Recursive Function)
        root: type-bTreeNode, root node of the tree

        returns: void
         */
        if (root != null) {
            printInOrder(root.left);
            System.out.println("Data: " +root.data);
            printInOrder(root.right);
        }
    }

    public void printInOrder(bTreeNode root, BufferedWriter bW) throws IOException{
        /*
        Display the tree in Inorder Format (Left, Root, Right)
        (Recursive Function)
        root: type-bTreeNode, root node of the tree
        bw: BufferedWriter, to write output to a file
        returns: void
         */
        if (root != null) {

            printInOrder(root.left,bW);
            bW.write(String.valueOf(root.data));
            bW.newLine();
            bW.flush();
            printInOrder(root.right,bW);
        }

    }

    public void search(int data, bTreeNode root) {
        /*
        Searches for a given element in the specified tree, via the root.

        data: Integer, the value to be searched for
        root: type-bTreeNode, The root of the tree in which the search is supposed to happen.

        returns: void.
         */

        if (root == null) {
            System.out.println("Tree Empty! or data not found");
        } else if (root.data == data) {
            System.out.println(data + " Found!!");
        } else if (data < root.data) {
            search(data, root.left);
        } else if (data > root.data) {
            search(data, root.right);
        }
    }

    public void createInput(String fileName,int maxNumber, int numberOfNodes){
        /*
        Function to generate input, i.e. integers to be inserted into the tree

        fileName: String, filename to store the input for the tree.
        maxNumber: int, maximum number to be inserted into the tree.
        numberOfNode: int, number of integers to be generated to be inserted in the tree.

        returns: void
        */
        Random r = new Random();
        try{
            FileWriter f = new FileWriter(fileName);
            BufferedWriter bW = new BufferedWriter(f);
            for(int i = 0;i<numberOfNodes;i++){
                bW.write(String.valueOf(r.nextInt(maxNumber)+1));
                bW.newLine();
                bW.flush();
            }
        }
        catch(IOException E ){
            System.out.println(E.toString());
        }
    }

    public void printTree(bTreeNode root, String outfile){
        /*
        prints the tree to a file

        root: bTreeNode, identifies the root to a file.
        outfile: String, name of the output file.

        returns: void
         */

        try{
            FileWriter fw = new FileWriter(outfile);
            BufferedWriter bR = new BufferedWriter(fw);
            printInOrder(root, bR);
        }
        catch (IOException E){
            System.out.println(E.toString());
        }
    }
}