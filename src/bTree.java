import java.util.Random;

/**
 * Created by Sushant on 14-Aug-16.
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

        //Creating the root node.
        bTreeNode root = new bTreeNode(500000);
        bTree tree = new bTree();
        Random r = new Random();
        /* Inserting 10 Million Integers into the Tree */
        for(int i = 0;i<10000000;i++){
            tree.insert(root,r.nextInt(100000000)+1);
        }
        tree.insert(root,15);
        System.out.println("Printing out the bTree");
        /*printing the tree in inorder */
        tree.printInOrder(root);
        /*Searching for a number */
        tree.search(15,root);
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
        Display the tree in Inorder Format (Left, Root, Right)
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

    public void search(int data, bTreeNode root){
        /*
        Searches for a given element in the specified tree, via the root.

        data: Integer, the value to be searched for
        root: type-bTreeNode, The root of the tree in which the search is supposed to happen.

        returns: void.
         */

        if(root == null){
            System.out.println("Tree Empty! or data not found");
        }
        else if(root.data == data){
            System.out.println(data+" Found!!");
        }
        else if(data < root.data){
            search(data,root.left);
        }
        else if(data > root.data){
            search(data,root.right);
        }
    }
}



