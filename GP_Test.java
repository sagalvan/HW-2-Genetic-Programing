import java.util.*;


public class GP_Test {

    private static void returnsExactFitIndividual() {
        GP_Algorithm algorithm = new GP_Algorithm();
        int populationSize = 20;
        int maxDepth = 5;
        double[] x = {-2.0, 0.0, 2.0};
        double[] y = {-4.0, 0.0, 4.0};
        Tree result = algorithm.GPAlgorithm(populationSize, maxDepth, x, y);

        if (result == null) {
            throw new AssertionError("Expected an exact-fit individual");
        }

        result.print();
    }

    private static void testGP2() {
        GP_Algorithm2 algorithm = new GP_Algorithm2();
        int populationSize = 20;
        int maxDepth = 5;
        double[] x = {1.0, 2.0, 3.0};
        double[] y = {1.0, 2.0, 3.0};
        double[] z = {1.0, 2.0, 3.0};
        double[] fx = {1.0, 8.0, 27.0};
        Tree2 result = algorithm.GPAlgorithm2(populationSize, maxDepth, x, y, z, fx);

        if (result == null) {
            throw new AssertionError("Expected an exact-fit individual");
        }

        result.print();
    }


    public static void main(String[] args) {
        //returnsExactFitIndividual();
        testGP2();
        System.out.println("GP_AlgorithmTest passed");
    }

}
