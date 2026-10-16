import java.util.*;

public class GP_Algorithm {
    //private static int SIZE = 100;
    // Data Variables

    // Methods to implement
    // generate population - generate a number of individuals w/ different depths
    // selection - pick individuals for crossover, depends on fitness
    //      Crossover - crossover rate (0.85-0.95) defines the probability that individuals undergo crossover
    //      Mutation - mutation rate (0.01) defines the probability an individual gets mutated
    // bloat control - set depths/node generation limits, research method

    public Tree GPAlgorithm(int size, int depth) {
        Map<Tree, Double> generation = generatePopulation(size, depth);
        // Calculate fitness of all individuals & save minimum
        Double minFitness = 0.0;
        Tree minIndividual = null;
        for (Tree individual : generation.keySet()) {
            // Double fitness = calculte fitness
            //population.put(individual, fitness);
            // if (fitness <= minFitness) {
            //     minFitness = fitness;
            //     minIndividual = individual;
            // }
        }

        // while (minimum fitness of some individual is > than 0.01) 
        while (minFitness >= 0.01){
            // Create new population list
            Map<Tree, Double> newGeneration = new HashMap<Tree, Double>();
            // Create new fitness list
            // loop through individuals for crossover or mutation
            while(newGeneration.size() != size) {
                // Preform fitness proportionate selection 
                Tree selectedTree = FPSelection(generation);

                Double rand = Math.random();
                // if random num < 95 (95% chance of crossover), preform a crossover and add to population
                if (rand < 0.95) {
                    // Select another tree for crossover
                    Tree otherTree = FPSelection(generation);
                    Tree crossover = selectedTree.crossover(otherTree);
                    // newGeneration.put(crossover, fitness)
                }
                // if random num < 1 (1% chance of mutation), preform a mutation & add to population
                if (rand < 0.1) {
                    Tree mutate = selectedTree.mutate();
                    // newGeneration.put(mutate, fitness)
                }

            }
            //New generation is saved from operations
            generation = newGeneration;
            //Save the minimum fitness
            for (Tree individual : generation.keySet()) {
                // if (fitness <= minFitness) {
                //     minFitness = fitness;
                //     minIndividual = individual;
                // }
            }
        }


        return minIndividual;
    }

    // generate population with a set size and individual depth
    public Map<Tree, Double> generatePopulation(int size, int depth){
        Random random = new Random();
        Map<Tree, Double> population = new HashMap<Tree, Double>();

        for (int i = 0; i < size; i++) {
            Tree individual = new Tree();
            int rand = random.nextInt(depth + 1);
            individual.generate_individual(rand);
            population.put(individual, 0.0);
        }

        return population;
    }



    // Calculates the fitness -> lower = better
    static double fitness(Tree tree, double[] x, double[] y) {

        double total = 0;

        for (int i = 0; i < x.length; i++){

            double predicted = tree.evaluate(x[i]);
            double actual = y[i];

            double squaredError = (predicted - actual) * (predicted - actual);

            //catches errors and returns a huge value so it is not possible for that fitness to win a tournament

            if (Double.isNaN(squaredError) || Double.isInfinite(squaredError)){
                return Double.MAX_VALUE;
            }


            total = total + squaredError;  

        }

        double avg = total / x.length;
        return avg;
    } 
    
    // Preform a fitness proportionate selection using a given population
    public Tree FPSelection(Map<Tree, Double> population){
        Double totalFitness = 0.0;
        for(Double fitness: population.values()) {
            totalFitness += fitness;
        }

        Double rand = totalFitness * Math.random();

        Double sum = 0.0;
        for (Tree individual : population.keySet()){
            sum += population.get(individual);
            if (sum >= rand) {
                return individual;
            }
        }

        return null;
    }
     
}
