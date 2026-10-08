import javax.swing.tree.TreeNode;
import java.util.Random;


class Node {
        char data;
        Node leftChild;
        Node rightChild;

        public Node(char data) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }
    }

public class Tree {
    
    public Node root;
    public int depth;
    

    public Tree(){
        this.root = null;
        this.depth = 0;
    }

    // generate tree/individual with a specified max depth
    public Tree generate_individual(int depth) {

        // if (depth <= 1) {
        // return;
        // }

        // // Always create a left child to guarantee the required depth
        // node.left = new TreeNode(); 
        // //randomizing what the value in the node will be not sure about this logic
        // // Randomly decide 50/50 chance whether to create a right child

        // if (Math.random() < 0.5) {
        //     node.right = new TreeNode();
        // }

        // //Left child will always be created 
        // generateTree(node.left, depth - 1);

        // if (node.right != null) {
        //     generateTree(node.right, depth - 1);
        // }
        // }

        // initialize tree with Tree()
        //
        // while(this.depth != depth) {
        //      Keep adding nodes to our tree
        // }
        // return tree
     
        return null;

    }

    // Add node to tree
    public void add_node(char data) {
        // if data is not in our function set or variable set, return error
        // otherwise:
        // Find a leaf node to add to.
        // if root is null, add the node to the root
        if (root == null) {
            root = new Node(data);
        }
        // if the root is not null
        else{
            Node current = root;
            while(current.leftChild != null){
            }
        }
        //      current node = root
        //      while the left node is not null || the right node is not null
        //          if (left node == null)
        //              current.left = new Node(data)
        //          else if (right node == null)
        //              current.left = new Node(data)
        
    } 

    // Mutate, which happens in lower level nodes
    public Tree mutate(){
        return null;
    }

    // Crossover, prevent a corssover occuring often in the higher levels of a tree.
    public Tree crossover(Tree other) {
        Random rand = new Random();
        // find breakpoint of tree 1 (parent node)
        // get a random depth to find a node at
        // save the root of this tree for our new tree
        // save the node parent 
        Tree temp = new Tree();
        temp.root = this.root;

        int rand_depth = rand.nextInt(this.depth + 1);

        Node parent = temp.root;
        for (int i = 0; i < rand_depth; i++) {
            int choice = rand.nextInt(2);
            if (choice == 0) {
                if (parent.leftChild != null){
                    parent = parent.leftChild;
                }
            }
            else if (choice == 1) {
                if (parent.rightChild != null){
                    parent = parent.rightChild;
                }
            }
        }

        // find breakpoint of tree 2 (child node)
        // get a random depth to find a node at
        int rand_depth2 = rand.nextInt(this.depth + 1);

        Node child = other.root;
        for (int i = 0; i < rand_depth2; i++) {
            int choice = rand.nextInt(2);
            if (choice == 0) {
                if(child.leftChild != null) {
                    child = child.leftChild;
                }
            }
            else if (choice == 1) {
                if(child.rightChild != null){
                    child = child.rightChild;
                }
            }
        }
        
        // connect child node to parent node
        parent.leftChild = child;
        return null;
    }

    // Printing a readable version of this tree (for debugging & presentation purposes)
    public void print() {

    }

    // Functions to consider
    // - Searching with a specified depth: find a node at this depth
    // public Node search(int depth) {
    //     return null;
    // }
    // - Keeping track of depth when creating an individual
}
