
public class Main {
    public static void main(String[] args) {

        if(args.length < 1) {
            System.out.println("Puzzle input must be passed to this program.");
            System.exit(1);
        }

        WordsearchSolver solver = new WordsearchSolver(args[0]);

        long startTime = System.nanoTime();
        solver.solvePartOne();
        solver.solvePartTwo();
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        System.out.printf("Calculation time: %.3f ms\n", duration / 1000000f);
    }
}