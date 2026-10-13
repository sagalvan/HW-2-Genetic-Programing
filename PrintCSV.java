import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrintCSV {
    public static void main(String[] args) {
        String csvFile = "test.csv"; 
        String line = "";
        

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                
                for (String cell : row) {
                    System.out.print(cell + ", ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tree a = new Tree();
        a.generate_individual(3);
        Tree b = a.newTree();
        a.print();
        b.print();           
        b.root.leftChild.data = '9';   
        a.print();              
        b.print();               
    }
}

