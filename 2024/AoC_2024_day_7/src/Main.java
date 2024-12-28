public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("The puzzle input file must be passed in.  Exiting.");
            System.exit(1);
        }

        CalibrationSolver solver = new CalibrationSolver(args[0]);

        long startTime = System.nanoTime();
        //solver.solvePartOne();
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        System.out.printf("Calculation time: %.3f ms\n", duration / 1000000f);
    }
}