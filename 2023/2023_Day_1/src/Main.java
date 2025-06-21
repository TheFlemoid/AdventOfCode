public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("The filepath of the input data must be passed as an argument. Exiting.");
            System.exit(-1);
        }

        CalibrationSolver calSolver = new CalibrationSolver(args[0]);

        long startTime = System.nanoTime();
        System.out.printf("First problem: %d\n", calSolver.solveFirstProblem());
        System.out.printf("Second problem: %d\n", calSolver.solveSecondProblem());
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        System.out.printf("Computation time: %.3f ms\n", duration / 1000000f);
    }
}