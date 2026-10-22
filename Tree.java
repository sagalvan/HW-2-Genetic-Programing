/**
 * A class for Tree objects and their functions. For Dataset 1.
 * 
 * @author Mali White, Samantha Galvan
 */
import java.util.Random;

class Node {
    char data;
    Node leftChild;
    Node rightChild;

    /**
     * Constructs a Tree node with specified data.
     * 
     * @param data  a character data.
     */
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
    

    /**
     * Constructs an empty tree.
     */
    public Tree(){
        this.root = null;
        this.depth = 0;   
    }

 
    /**
     * Generates a tree/individual with a specified depth
     * 
     * @param depth
     */
    public void generate_individual(int depth) {
        this.depth = depth;
        root = buildNode(depth);

    }


    /**
     * Calculates the depth of this Tree.
     * 
     * @return  Integer depth of the Tree
     */
    public int depth() {
        return depth(root);
    }


    /**
     * Helper function for depth() to calculate the depth of a Tree.
     * 
     * @param node  Root note of a tree.
     * @return      Integer depth of the Tree
     */
    private int depth(Node node) {

        if (node == null || node.leftChild == null) {
            return 0; 
        }  
        return 1 + Math.max(depth(node.leftChild), depth(node.rightChild));
    }


    /**
     * Calculates the number of nodes that are in a Tree.
     * 
     * @return  Integer number of nodes in the Tree.
     */
    public int size() {
        return size(root);
    } 


    /**
     * Helper function for size() to calculate the number of nodes in a Tree.
     * 
     * @param node  Root node of a Tree.
     * @return      Integer number of nodes in the Tree.
     */
    private int size(Node node) {
        if (node == null) return 0;
        return 1 + size(node.leftChild) + size(node.rightChild);
    }
  

    /**
     * Adds a node to tree -> terminal values at leaf nodes and functions at everywhere else.
     * 
     * @param depth Depth of the Tree.
     * @return      
     */
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


    /**
     * Randomly selects a character from the function set.
     * 
     * @return  Selected function character.
     */
    private char randomFunction(){
        return functions[random.nextInt(functions.length)];
    }


    /**
     * Randomly selects a character from the terminal set.
     * 
     * @return  Selected terminal character
     */ 
    private char randomTerminal(){
        return terminals[random.nextInt(terminals.length)];
    }


    /**
     * Prints the function represented by a Tree.
     */
    public void print(){
        printNode(root);
        System.out.println();
    }

    /**
     * Helper function to print().
     * 
     * @param node  Root node of the Tree.
     */
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

  
    /**
     * Builds a copy of a subtree starting at the passed in node.
     * 
     * @param node  Node to begin copied subtree
     * @return      Copied subtree.
     */
    private Node buildCopy(Node node) {
        if (node == null) {
            return null;
    }

        Node newNode = new Node(node.data);
        newNode.leftChild  = buildCopy(node.leftChild);
        newNode.rightChild = buildCopy(node.rightChild);
        return newNode;
    }

    
    /**
     * Evaluates the function represented by the Tree with a given x value.
     * 
     * @param variable  A given x value.
     * @return          The calculated value.
     */
    public double evaluate(double variable) {
        return evaluate(variable, root);
    }


    /**
     * A recursive helper function for evaluate(variable).
     * 
     * @param variable  A given x value.
     * @param node      Root node of the Tree.
     * @return          The calculated value.
     */
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



    /**
     * A helper function to mutate() and crossover(). Traverses down from the starting node to a random node in the Tree.
     * 
     * @param start Starting node.
     * @return      Selected random node.
     */
    private Node pickNode(Node start) {
        // picks the max of 2 randomly generated values to try and avoid bias of lower depths
        int randDepth = Math.max(random.nextInt(depth(start) + 1), random.nextInt(depth(start) + 1));
        Node current = start;

        for (int i = 0; i < randDepth; i++) {
            // reached a leaf, can't go deeper
            if (current.leftChild == null) {      
                break;
            }
            // 1/2 of the time goes left and 1/2 of the time goes right
            if (random.nextInt(2) == 0) {
                current = current.leftChild;
            } 
            else {
                current = current.rightChild;
            }
        }
        return current;
    }


    /**
     * Preforms a mutation on a given Tree. Does not edit the original Tree.
     * 
     * @return  Resulting mutated Tree from the original.
     */
    public Tree mutateNode(){

        // make a copy, so the original is never touched
        Tree temp = new Tree();
        temp.root = buildCopy(root);

        Node current = pickNode(temp.root);

        // changes the data of the randomly selected node
        char newData;
        // at a leaf, swap for a different terminal value
        if (current.leftChild == null) {          
            do {
                newData = randomTerminal();
            } 
            while (newData == current.data);
        // at an operator, swap for a different operator value
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

        
    /**
     * Preoforms a crossover with this Tree and another given Tree. Does not edit the original Tree.
     * 
     * @param other Other Tree that will be used in crossover.\
     * @return      Resulting crossover Tree. 
     */
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
}

