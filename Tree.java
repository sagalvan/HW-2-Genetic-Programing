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
