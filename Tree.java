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
    Node root;
    int depth;

    static Random random = new Random();
    static char[] functions = {'+', '-', '*', '/'};
    static char[] terminals = {'x', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    

    public Tree(){
        this.root = null;
        this.depth = 0;   
     }

    // generate tree/individual with a specified depth
    public void generate_individual(int depth) {
        this.depth = depth;
        root = buildNode(depth);

     }

    public int depth(Node node) {

        if (node == null || node.leftChild == null) {
            return 0; 
        }  
        return 1 + Math.max(depth(node.leftChild), depth(node.rightChild));
    }

    
    public int depth() {
        return depth(root);
    }

    // Add node to tree -> terminal values at leaf nodes and functions at everywhere else

    private Node buildNode(int depth) {

        double chanceRight = Math.random();
        if (depth == 0 || chanceRight < 0.3){
            return new Node (randomTerminal());
        }

        //if not this node is an operation, so it must have 2 children
        Node node = new Node(randomFunction());
        node.leftChild = buildNode(depth - 1);
        node.rightChild = buildNode(depth - 1);
        return node;



    }


    //Randomly picks function
    private char randomFunction(){
        return functions[random.nextInt(functions.length)];
    }

    //Random picks terminal node 
    private char randomTerminal(){
        return terminals[random.nextInt(terminals.length)];
    }


    //Print out the finished tree

    public void print(){
    printNode(root);
    System.out.println();
    }

    private void printNode(Node node) {
        if (node == null) 
            return;
        if (node.leftChild == null) {      // leaf
            System.out.print(node.data);
        } else {                           // operator
            System.out.print("(");
            printNode(node.leftChild);
            System.out.print(" " + node.data + " ");
            printNode(node.rightChild);
            System.out.print(")");
        }
    }

    // Builds an  copy of a subtree starting at the passed in node
    private Node buildCopy(Node node) {
        if (node == null) {
            return null;
    }

        Node newNode = new Node(node.data);
        newNode.leftChild  = buildCopy(node.leftChild);
        newNode.rightChild = buildCopy(node.rightChild);
        return newNode;
    }

    //Plug in the value of x to evaluate the trees output
    public double evaluate(double variable, Node node){
        if (node.data == '+') {
            return evaluate(variable, node.leftChild) + evaluate(variable, node.rightChild);
        }
        else if (node.data == '-') {
            return evaluate(variable, node.leftChild) - evaluate(variable, node.rightChild);
        }
        else if (node.data == '*') {
            return evaluate(variable, node.leftChild) * evaluate(variable, node.rightChild);
        }
        else if (node.data == '/') {
            return evaluate(variable, node.leftChild) / evaluate(variable, node.rightChild);
        }
        else if (node.data == 'x') {
            return variable;
        }
        else {
            return node.data - '0';
        }
    }

    //can call evaluate with just the variable for the GP for simplicity purposes
    public double evaluate(double variable) {
        return evaluate(variable, root);
    }

    /// Walks down from start to a random node (called in mutate and crossover)
    private Node pickNode(Node start) {
        ///picks the max of 2 randomly generated values to try and avoid bias of lower depths
        int randDepth = Math.max(random.nextInt(depth(start) + 1), random.nextInt(depth(start) + 1));
        Node current = start;

        for (int i = 0; i < randDepth; i++) {
            /// reached a leaf, can't go deeper
            if (current.leftChild == null) {      
                break;
            }
            ///1/2 of the time goes left and 1/2 of the time goes right
            if (random.nextInt(2) == 0) {
                current = current.leftChild;
            } 
            else {
                current = current.rightChild;
            }
        }
        return current;
    }


    /// Performs a mutation
    public Tree mutateNode(){

        ///make a copy, so the original is never touched
        Tree temp = new Tree();
        temp.root = buildCopy(root);

        Node current = pickNode(temp.root);

        // /changes the data of the randomly selected node
        char newData;
        /// at a leaf, swap for a different terminal value
        if (current.leftChild == null) {          
            do {
                newData = randomTerminal();
            } 
            while (newData == current.data);
        /// at an operator, swap for a different operator value
        } 
        else {                                  
            do {
                newData = randomFunction();
            } 
            while (newData == current.data);
        }
        current.data = newData;

        return temp;
    }

        

    // Crossover, prevent a corssover occuring often in the higher levels of a tree.
    public Tree crossover(Tree other) {
        ///make a copy of the original tree
        Tree temp = new Tree();
        temp.root = buildCopy(root);

        ///pick a random node in the copy where the mutation will occur
        Node current = pickNode(temp.root);

        ///pick a random node in the other tree where the subtree we are taking will start
        Node otherCurrent = pickNode(other.root);

        ///copy the new subtree and then overwrite the chosen node in the original copy with it
        Node otherCurrentCopy = buildCopy(otherCurrent);

        current.data = otherCurrentCopy.data;
        current.leftChild = otherCurrentCopy.leftChild;
        current.rightChild = otherCurrentCopy.rightChild;

        return temp;
    }

    // public static boolean isEqual(Tree I1, Tree I2) {
    //     return isEqualHelper(I1.root, I2.root);
    // }

    // private static boolean isEqualHelper(Node node1, Node node2) {
    //     if (node1 == null && node2 == null) {
    //         return true;
    //     }

    //     if (node1 == null || node2 == null) {
    //         return false;
    //     }     
        
    //     if (node1.data != node2.data) {
    //         return false;
    //     }
    //     return isEqualHelper(node1.leftChild, node2.leftChild) && isEqualHelper(node1.rightChild, node2.rightChild);
    // }


    // Functions to consider
    // - Searching with a specified depth: find a node at this depth
    // public Node search(int depth) {
    //     return null;
    // }
    // - Keeping track of depth when creating an individual

    public static void main(String[] args) {
    Tree t = new Tree();
    t.generate_individual(3);
    t.print();
    }
}

