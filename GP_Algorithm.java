import java.util.ArrayList;
import java.util.List;
static Random random = new Random();

public class GP_Algorithm {
    // Methods to implement
    // generate population - generate a number of individuals
    // selection - pick individuals for crossover, depends on fitness
    //      Crossover - crossover rate (0.6-0.95) defines the probability that individuals undergo crossover
    //      Mutation - mutation rate (0.01) defines the probability an individual gets mutated
    // bloat control - set depths/node generation limits, research method

    // Main algorithm
    // Generate a population with a set size 
    // while (minimum fitness of some individual is > than 0.01) {
    //      select individuals for crossover (a set number of times?)
    // }

    // Calculates the fitness -> lower = better
    static double fitness(Tree tree, double[] x, double[] y) {
        
        double total = 0;

        for (int i = 0; i < dataSize; i++){
            double predicted = tree.evaluate(x[i]);
            double actual = y[i];
            
            double squaredError = (predicted - actual) * (predicted - actual);
             
            //catches errors and returns a huge value so it is not possible for that fitness to win a tournament

            if (Double.isNaN(squaredError) || Double.isInfinite(squaredError)){
                return Double.MAX_VALUE;
            }

            else{
                total = total + squaredError;
                int avg = total / dataSize;
            }

        }
        return avg;

     
}

///picks individuals at random and returns the tree with the lowest fitness

static Tree tournamentSelect(List<Tree> pop, double[] fitnesses, int tournamentSize){
    
    int winnerFitnessIndex = (random.nextInt(pop.size()));
    double winnerFitness = fitnesses[winnerFitnessIndex];


    for(int i = 1; i < tournamentSize; i++){

        int challengerIndex = random.nextInt(pop.size());  
        double challenger = fitnesses[challengerIndex]; 

        if (fitnesses[winnerFitnessIndex] > fitnesses[challengerIndex]) {                            
            winnerFitnessIndex = challengerIndex;
        }
    }
    return pop.get(winnerFitnessIndex); 
         
    }
    
        
    





}
