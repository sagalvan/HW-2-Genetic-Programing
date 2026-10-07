import javax.swing.tree.TreeNode;

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
    Node root;
    

    public Tree(){
        this.root = null;
    }

    // generate tree/individual with a specified max depth
    public void generate_individual(int depth) {

        if (depth <= 1) {
        return;
        }

        // Always create a left child to guarantee the required depth
        node.left = new TreeNode(); 
        //randomizing what the value in the node will be not sure about this logic
        // Randomly decide 50/50 chance whether to create a right child

        if (Math.random() < 0.5) {
            node.right = new TreeNode();
        }

        //Left child will always be created 
        generateTree(node.left, depth - 1);

        if (node.right != null) {
            generateTree(node.right, depth - 1);
        }
        }

     


    }

    // Add node to tree
    public void add_node() {

    } 

    // Mutate, which happens in lower level nodes
    public Tree mutate(){
        return null;
    }

    // Crossover, prevent a corssover occuring often in the higher levels of a tree.
    public Tree crossover(Tree tree_2) {
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
