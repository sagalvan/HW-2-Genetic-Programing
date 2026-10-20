import java.util.*;


public class GP_Test {

    private static void returnsExactFitIndividual() {
        GP_Algorithm algorithm = new GP_Algorithm();
        int populationSize = 10;
        int maxDepth = 5;
        double[] x = {-2.0, 0.0, 2.0};
        double[] y = {-4.0, 0.0, 4.0};
        Tree result = algorithm.GPAlgorithm(populationSize, maxDepth, x, y);

        if (result == null) {
            throw new AssertionError("Expected an exact-fit individual");
        }

        result.print();
    }


    public static void main(String[] args) {
        returnsExactFitIndividual();
        System.out.println("GP_AlgorithmTest passed");
    }

}
