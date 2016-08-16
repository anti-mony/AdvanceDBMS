import java.util.Random;

/**
 * Created by Sushant on 14-Aug-16.
 */
class bTreeNode{
    int data;
    bTreeNode left;
    bTreeNode right;

    public bTreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

}

public class bTree {

    public static void main(String[] args) {
        bTreeNode root = new bTreeNode(5000);
        bTree tree = new bTree();
        Random r = new Random();
        for(int i = 0;i<10000;i++){
            tree.insert(root,r.nextInt(10000)+1);
        }
        tree.insert(root,15);
        System.out.println("Printing out the bTree");
        tree.printInOrder(root);
        tree.search(15,root);
    }

    public void insert(bTreeNode node, int value) {
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
        if (root != null) {
            printInOrder(root.left);
            System.out.println("Data: " +root.data);
            printInOrder(root.right);
        }
    }

    public void search(int data, bTreeNode root){
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



