/**
 * A class for Tree objects and their functions. For Dataset 2.
 * 
 * @author Mali White, Samantha Galvan
 */
import java.util.Random;

class Node2 {
    double data;
    Node2 leftChild;
    Node2 rightChild;

    /**
     * Constructs a Tree node with specified data.
     * 
     * @param data  a character data.
     */
    public Node2(double data) {
        this.data = data;
        this.leftChild = null;
        this.rightChild = null;
    }
}

public class Tree2 {
    Node2 root;
    int depth;

    static Random random = new Random();
    static char[] functions = {'+', '-', '*', '/'};
    static int range = 9;
    static char[] terminals = {'x', 'y', 'z', 'R'};
    

    /**
     * Constructs an empty tree.
     */
    public Tree2(){
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
    private int depth(Node2 node) {

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
    private int size(Node2 node) {
        if (node == null) return 0;
        return 1 + size(node.leftChild) + size(node.rightChild);
    }
  

    /**
     * Adds a node to tree -> terminal values at leaf nodes and functions at everywhere else.
     * 
     * @param depth Depth of the Tree.
     * @return      
     */
    private Node2 buildNode(int depth) {
        double chanceRight = Math.random();

        if (depth == 0 || chanceRight < 0.3){
            return new Node2(randomTerminal());
        }

        //if not this node is an operation, so it must have 2 children
        Node2 node = new Node2(randomFunction());
        node.leftChild = buildNode(depth - 1);
        node.rightChild = buildNode(depth - 1);
        return node;
    }


    /**
     * Randomly selects a character from the function set.
     * 
     * @return  Selected function character.
     */
    private double randomFunction(){
        char function = functions[random.nextInt(functions.length)];
        double result = function;
        return result;
    }


    /**
     * Randomly selects a character from the terminal set.
     * 
     * @return  Selected terminal character
     */ 
    private double randomTerminal(){
        char terminal = terminals[random.nextInt(terminals.length)];
        double result = terminal;
        if (terminal == 'R') { // Terminal is a real number
            Random random = new Random();
            double randomDouble = range * random.nextDouble();
            return randomDouble;
        }
        return result;
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
    private void printNode(Node2 node) {
        if (node == null) 
            return;
        if (node.leftChild == null) {      // leaf
            if (node.data == 120.0 || node.data == 121.0 || node.data == 122.0) {
                System.out.print((char)node.data);
            }
            else {
                System.out.print(node.data);
            }
        } else {                           // operator
            System.out.print("(");
            printNode(node.leftChild);
            System.out.print(" " + (char)node.data + " ");
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
    private Node2 buildCopy(Node2 node) {
        if (node == null) {
            return null;
    }

        Node2 newNode = new Node2(node.data);
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
    public double evaluate(double x, double y, double z) {
        return evaluate(x, y, z, root);
    }


    /**
     * A recursive helper function for evaluate(variable).
     * 
     * @param variable  A given x value.
     * @param node      Root node of the Tree.
     * @return          The calculated value.
     */
    public double evaluate(double x, double y, double z, Node2 node){
        if (node.data == 43.0) { // '+'
            return evaluate(x, y, z, node.leftChild) + evaluate(x, y, z, node.rightChild);
        }
        else if (node.data == 45.0) { // '-'
            return evaluate(x, y, z, node.leftChild) - evaluate(x, y, z, node.rightChild);
        }
        else if (node.data == 42.0) {// '*'
            return evaluate(x, y, z, node.leftChild) * evaluate(x, y, z, node.rightChild);
        }
        else if (node.data == 47.0) {// '/'
            return evaluate(x, y, z, node.leftChild) / evaluate(x, y, z, node.rightChild);
        }
        else if (node.data == 120.0) {
            return x;
        }
        else if (node.data == 121.0) {
            return y;
        }
        else if (node.data == 122.0) {
            return z;
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
    private Node2 pickNode(Node2 start) {
        // picks the max of 2 randomly generated values to try and avoid bias of lower depths
        int randDepth = Math.max(random.nextInt(depth(start) + 1), random.nextInt(depth(start) + 1));
        Node2 current = start;

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
    public Tree2 mutateNode(){

        // make a copy, so the original is never touched
        Tree2 temp = new Tree2();
        temp.root = buildCopy(root);

        Node2 current = pickNode(temp.root);

        // changes the data of the randomly selected node
        double newData;
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
    public Tree2 crossover(Tree2 other) {
        ///make a copy of the original tree
        Tree2 temp = new Tree2();
        temp.root = buildCopy(root);

        ///pick a random node in the copy where the mutation will occur
        Node2 current = pickNode(temp.root);

        ///pick a random node in the other tree where the subtree we are taking will start
        Node2 otherCurrent = pickNode(other.root);

        ///copy the new subtree and then overwrite the chosen node in the original copy with it
        Node2 otherCurrentCopy = buildCopy(otherCurrent);

        current.data = otherCurrentCopy.data;
        current.leftChild = otherCurrentCopy.leftChild;
        current.rightChild = otherCurrentCopy.rightChild;

        return temp;
    }
}

