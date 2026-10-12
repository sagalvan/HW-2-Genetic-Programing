public class GP_Algorithm {
    // Methods to implement
    // generate population - generate a number of individuals w/ different depths
    // selection - pick individuals for crossover, depends on fitness
    //      Crossover - crossover rate (0.85-0.95) defines the probability that individuals undergo crossover
    //      Mutation - mutation rate (0.01) defines the probability an individual gets mutated
    // bloat control - set depths/node generation limits, research method

    // Main algorithm
    // Generate a population with a set size 
    // Calculate fitness of all individuals & save minimum
    // while (minimum fitness of some individual is > than 0.01) {
    //      Create new population list
    //      Create new fitness list
    //      loop through individuals for crossover or mutation
    //          Preform fitness proportionate selection 
    //          if random num < 95 (95% chance of crossover), preform a crossover and add to population
    //          if random num < 1 (1% chance of mutation), preform a mutation & add to population
    //      New generation is saved from operations
    //      Evaluate the fitness of every individual
    //      Save the minimum fitness
    // }

    public void generatePopulation(int size) {
        for (int i = 0; i < size; i++) {
            
        }
    }
}
