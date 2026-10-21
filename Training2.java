import java.io.*;
import java.util.*;

public class Training2 {
    /**
     * Loads CSV data from a specified CSV file.
     * 
     * @param path  CSV filename.
     * @return      Rows of data.
     */
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


    /**
     * Method that trains the Genetic Programing Algorithm with a given training dataset.
     * Additionally creates a CSV file with the resulting fitneses from all runs.
     * 
     * @param numRuns        Number of runs to train algorithm
     * @param populationSize Size of the population
     * @param maxDepth       Maximum depth of individuals
     * @param trainX         X values of the traning data
     * @param trainY         Y values of the training data
     * @return               The tree result with the lowest fitness in the runs            
     */
    static Tree2 trainAlgorithm(
        int numRuns, 
        int populationSize, 
        int maxDepth, 
        double[] trainX, 
        double[] trainY,
        double[] trainZ,
        double[] trainFX
    ) {
        GP_Algorithm2 algorithm = new GP_Algorithm2();

        List<List<String>> data = new ArrayList<>();

        Double minFitness = -1.0;
        Tree2 minResult = null;
        for (int run = 0; run < numRuns; run++) {
            List<String> row = new ArrayList<>();

            Tree2 result = algorithm.GPAlgorithm2(populationSize, maxDepth, trainX, trainY, trainZ, trainFX);
            Double fitness = GP_Algorithm2.fitness(result, trainX, trainY, trainZ, trainFX);

            if (fitness < minFitness || minFitness == -1.0) {
                minFitness = fitness;
                minResult = result;
            }
            
            row.add(Integer.toString(run));
            row.add(Double.toString(fitness));
            data.add(row);
        }

        String csvFile = "result.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {
            for (List<String> row : data) {
                writer.append(String.join(",", row));
                writer.append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }

        return minResult;
    }

    public static void main(String[] args) {

        List<double[]> rows = loadCSV("dataset2.csv");
        System.out.println("Total points: " + rows.size());

        Collections.shuffle(rows, new Random(67));

        List<double[]> test  = rows.subList(0, 5000);
        List<double[]> train = rows.subList(5000, rows.size());

        System.out.println("Training Size: " + train.size() + ", Testing Size: " + test.size());


        double[] trainX = new double[train.size()];
        double[] trainY = new double[train.size()];
        double[] trainZ = new double[train.size()];
        double[] trainFX = new double[train.size()];
    

        for (int i = 0; i < train.size(); i++) {
            trainX[i] = train.get(i)[0];
            trainY[i] = train.get(i)[1]; 
            trainZ[i] = train.get(i)[2]; 
            trainFX[i] = train.get(i)[3]; 
        }
        
        int populationSize = 20;
        int maxDepth = 6;
        int numRuns = 30;

        double[] x = {9609691, 9383433, 7537404, 2501726, 2020688};
        double[] y = {2256113, 8226381, 128622, 2662524, 5365951};
        double[] z = {3.20, 3.33, 1.01, 8.06, 4.85};
        double[] fx = {141.56, 465.22, 63.79, 6.84, 30.78};


        //Tree2 testTree = trainAlgorithm(numRuns, populationSize, maxDepth, trainX, trainY, trainZ, trainFX);
        Tree2 testTree = trainAlgorithm(numRuns, populationSize, maxDepth, x, y, z, fx);

        System.out.print("Test Tree: ");
        testTree.print();
    }
}
