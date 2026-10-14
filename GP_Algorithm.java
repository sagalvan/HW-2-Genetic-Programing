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

        for (int i = 0; i < ; i++){
            double predicted = tree.evaluate(x[i]);
            double actual = y[i];
            
            double squaredError = (predicted - actual) * (predicted - actual);
             

            if (difference == NaN){
                return Optional.empty();
            }

            else{
                total = total + difference;
            }

        }
        return total;

     
}
