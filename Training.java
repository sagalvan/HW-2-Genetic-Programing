/**
 * A program that trains a genetic programing algorithm using Dataset 1.
 * 
 * @author Mali White, Samantha Galvan
 */
import java.io.*;
import java.util.*;

public class Training {
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
    static Tree trainAlgorithm(int numRuns, int populationSize, int maxDepth, double[] trainX, double[] trainY) {
        GP_Algorithm algorithm = new GP_Algorithm();

        List<List<String>> data = new ArrayList<>();

        Double minFitness = -1.0;
        Tree minResult = null;
        for (int run = 0; run < numRuns; run++) {
            List<String> row = new ArrayList<>();

            Tree result = algorithm.GPAlgorithm(populationSize, maxDepth, trainX, trainY);
            Double fitness = GP_Algorithm.fitness(result, trainX, trainY);

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

        List<double[]> rows = loadCSV("dataset1.csv");
        System.out.println("Total points: " + rows.size());

        Collections.shuffle(rows, new Random(67));

        List<double[]> test  = rows.subList(0, 5000);
        List<double[]> train = rows.subList(5000, rows.size());

        System.out.println("Training Size: " + train.size() + ", Testing Size: " + test.size());


        double[] trainX = new double[train.size()];
        double[] trainY = new double[train.size()];
    

        for (int i = 0; i < train.size(); i++) {
            trainX[i] = train.get(i)[0];   // which column is x?
            trainY[i] = train.get(i)[1];   // which column is f(x)?
        }
        
        int populationSize = 20;
        int maxDepth = 6;
        int numRuns = 30;

        Tree testTree = trainAlgorithm(numRuns, populationSize, maxDepth, trainX, trainY);

        System.out.print("Test Tree: ");
        testTree.print();
    }
}
