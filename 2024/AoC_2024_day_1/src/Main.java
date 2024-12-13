public class Main {
    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Puzzle input must be passed as a param.");
            System.exit(1);
        }

        ListSolver solver = new ListSolver(args[0]);

        long startTime = System.nanoTime();
        solver.solveProblem();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.printf("Time to calulate: %.3f ms\n", duration/1000000f);
    }
}