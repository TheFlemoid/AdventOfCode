public class Main {
    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Puzzle input file must be passed into this application.");
            System.exit(1);
        }

        ReportSolver solver = new ReportSolver(args[0]);

        long startTime = System.nanoTime();
        solver.solveProblem();
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        System.out.printf("Computation time: %.3f ms\n", duration / 1000000f);
    }
}