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

    static Random random = new Random();
    static char[] functions = {'+', '-', '*', '/'};
    static char[] terminals = {'x', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    

    public Tree(){
        this.root = null;
    }

    // generate tree/individual with a specified depth
    public void generate_individual(int depth) {
        
        root = buildNode(depth);

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
        if (node == null) return;
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

