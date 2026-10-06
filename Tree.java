public class Tree {
    class Node {
        char data;
        Node leftChild;
        Node rightChild;

        public Node(char data, Node leftChild, Node rightChild) {
            this.data = data;
            this.leftChild = null;
            this.rightChild = null;
        }
    }

    Node root;
    

    public Tree(int depth){
        this.root = null;
    }

    // generate tree/individual with a specified max depth

    // Add node to tree 

    // Mutate, which happens in lower level nodes

    // Crossover, prevent a corssover occuring often in the higher levels of a tree.

    // Printing a readable version of this tree (for debugging & presentation purposes)

    // Functions to consider
    // - Searching with a specified depth: find a node at this depth
    // - Keeping track of depth when creating an individual
}
