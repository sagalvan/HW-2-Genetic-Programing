import java.io.*;
import java.util.*;

public class PrintCSV {

    static List<double[]> loadCSV(String path) {

        List<double[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {

                String[] cells = line.split(",");
                double[] row = new double[cells.length];
                for (int i = 0; i < cells.length; i++) {
                    row[i] = Double.parseDouble(cells[i].trim());
                }
                rows.add(row);
            }
        } 
        catch (IOException e) {

            e.printStackTrace();
        }
        return rows;
    }

    public static void main(String[] args) {

        List<double[]> rows = loadCSV("dataset1.csv");
        System.out.println("Total points: " + rows.size());

        Collections.shuffle(rows, new Random(67));

        List<double[]> test  = rows.subList(0, 5000);
        List<double[]> train = rows.subList(5000, rows.size());

        System.out.println("Train: " + train.size() + ", Test: " + test.size());


        double[] trainX = new double[train.size()];
        double[] trainY = new double[train.size()];
    

        for (int i = 0; i < train.size(); i++) {
            trainX[i] = train.get(i)[0];   // which column is x?
            trainY[i] = train.get(i)[1];   // which column is f(x)?
        }
        
        Tree t = new Tree();
        t.generate_individual(2);
        t.print();
        System.out.println(GP_Algorithm.fitness(t, trainX, trainY));
    }
}
