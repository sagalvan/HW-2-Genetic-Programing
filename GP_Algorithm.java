/**
 * A program that implements the genetic programing algorithm.
 * 
 * Authors: Samantha Galvan, Mali White
 */

import java.util.*;

public class GP_Algorithm {
    static final int DEPTH_CAP = 6;
    static int TOURNAMENT_SIZE;
    static final int GENERATION_MAX = 10000;
    static final Double MUTATION_RATE = 0.05;
    static final Double CROSSOVER_RATE = 0.90;
    static final double SIZE_WEIGHT = 0.01;

    /**
     * A method that finds the optimal best fit function for a specified dataset using the genetic 
     * programing algorithm.
     * 
     * @param size  The size of the population.
     * @param depth The max depth of the individuals in the population.
     * @param x     The x values of a specified dataset.
     * @param y     The y values of a specified dataset.
     * @return      The optimal function of the specified dataset.
     */
    public Tree GPAlgorithm(int size, int depth, double[] x, double[] y) {
        TOURNAMENT_SIZE = size/8;
        Map<Tree, Double> population = generatePopulation(size, depth); // Generate generation 0
        
        Double minFitness = fitness(population.keySet().iterator().next(), x, y);
        Tree minIndividual = null;

        for (Tree individual : population.keySet()) { // Calculate fitness of all individuals & save minimum
            Double fitness = fitness(individual, x, y);
            population.put(individual, fitness);
            
            if (fitness <= minFitness) { // find the minimum fitness in generation 0
                minFitness = fitness;
                minIndividual = individual;
            }
        }

        int generationsCounter = 0;
        while (generationsCounter < GENERATION_MAX){ // Minimum fitness of some individual is > than 0.01
            Map<Tree, Double> newGeneration = new HashMap<Tree, Double>(); // Create new population list

            Map<Tree, Double> pygmyScores = new HashMap<Tree, Double>();
            for (Tree t : population.keySet()) {
                pygmyScores.put(t, population.get(t) + SIZE_WEIGHT * t.size());
            }
            
            while(newGeneration.size() != size) { // loop through individuals for crossover or mutation
                Tree selectedTree = tournamentSelect(population, TOURNAMENT_SIZE);

                Double rand = Math.random();

                if (rand < MUTATION_RATE) { // (1% chance of mutation), preform a mutation & add to population
                    Tree mutate = selectedTree.mutateNode();
                    double fitness = fitness(mutate, x, y);
                    newGeneration.put(mutate, fitness);

                    if (fitness <= minFitness) { // check if the new fitness is smaller than the minimum
                        minFitness = fitness;
                        minIndividual = mutate;
                    }
                }
        
                else if (rand < CROSSOVER_RATE) { // (95% chance of crossover), preform a crossover and add to population
                    Tree parent1 = selectedTree; // civil servant wheel: plain error
                    Tree parent2 = tournamentSelect(pygmyScores, 5);   // pygmy wheel which is linear combo of error and size

                    if (parent1 == parent2) {
                        continue; // need two different trees so break this iteration and try again
                    }

                    Tree crossover = parent1.crossover(parent2);

                    if (crossover.depth() > DEPTH_CAP) {
                        continue; // too deep, try again
                    }

                    double fitness = fitness(crossover, x, y);
                    newGeneration.put(crossover, fitness);

                    if (fitness <= minFitness) {
                        minFitness = fitness;
                        minIndividual = crossover;
                    }
                }
            }

            population = newGeneration; //New generation is saved from operations
            generationsCounter++;
        }

        return minIndividual;
    }


    /**
     * Generates a population with a set size and individual depth.
     * 
     * @param size  The size of the population.
     * @param depth The max depth of the individuals in the population.
     * @return
     */
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


    /**
     * Calculates the fitness of an individual.
     * 
     * @param tree  The individual tree
     * @param x     The x values of a specified dataset.
     * @param y     The y values of a specified dataset.
     * @return      The fitness of a specified individual.
     */
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
    
    
    /**
     * Preforms a fitness proportionate selection using a given population
     * 
     * @param population    The specified population to select from.
     * @return              The selected individual.
     */
    public Tree FPSelection(Map<Tree, Double> population){
        Double totalFitness = 0.0;
        for(Double fitness: population.values()) { // get the sum of the fitnesses in the population
            totalFitness = 1.0 / (1.0 + fitness);
        }

        Double rand = totalFitness * Math.random(); // randomly 'spin the wheel'

        Double sum = 0.0;
        Tree last = null;
        for (Tree individual : population.keySet()){ // iterate through population until sum exceeds rand
            sum += 1.0 / (1.0 + population.get(individual));
            last = individual;
            if (sum >= rand) {
                return individual;
            }
        }

        return last;
    }
     

    /**
     * Picks individuals at random and returns the tree with the lowest fitness
     * 
     * @param population        Population map with individuals and their fitnesses
     * @param tournamentSize    Desired tournament size
     * @return                  Winner individual selected from tournament
     */
    public Tree tournamentSelect(Map<Tree, Double> population, int tournamentSize){
        List<Tree> individuals = new ArrayList<>(population.keySet());
        List<Double> fitnesses = new ArrayList<>(population.values());
        
        Random random = new Random();
        int winnerFitnessIndex = (random.nextInt(individuals.size()));

        for(int i = 1; i < tournamentSize; i++){

            int challengerIndex = random.nextInt(individuals.size());  

            if (fitnesses.get(winnerFitnessIndex) > fitnesses.get(challengerIndex)) {                            
                winnerFitnessIndex = challengerIndex;
            }
        }
        return individuals.get(winnerFitnessIndex); 
            
    }
}

