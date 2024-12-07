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

        UpdateSolver updateSolver = new UpdateSolver(args[0], args[1]);
        int middleSum = updateSolver.checkUpdatesAndAddMiddleValues();
        System.out.println("Sum of conforming pattern middle digits: " + middleSum);
    }
}