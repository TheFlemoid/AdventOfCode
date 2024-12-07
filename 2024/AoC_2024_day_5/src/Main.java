public class Main {

    /**
     * Application main
     *
     * @param args filepath to the update page number rules, and the filepath to the list of updates
     */
    public static void main(String[] args) {
        // Check that both files were passed into the application
        if (args.length < 2) {
            System.out.println("The following arguments must be passed, in order:");
            System.out.println("   1. The filepath to the update page number rules");
            System.out.println("   2. The filepath to the list of updates");
            System.out.println("Exiting.");
            System.exit(1);
        }

        long startTime = System.nanoTime();
        UpdateSolver updateSolver = new UpdateSolver(args[0], args[1]);
        //int middleSum = updateSolver.checkAndFixValuesAndAddMiddles();
        int middleSum = updateSolver.checkUpdatesAndAddMiddleValues();
        long endTime = System.nanoTime();

        long duration = endTime - startTime;
        double millisToSimulate = duration / 1000000f;

        System.out.printf("Sum of conforming pattern middle digits: %d\n", middleSum);
        System.out.printf("Simulation time: %.3f ms\n", millisToSimulate);
    }
}