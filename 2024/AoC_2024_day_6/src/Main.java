import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("The puzzle floor plan must be passed as a param. Exiting.");
            System.exit(1);
        }

        Path floorPlanPath = Paths.get(args[0]);
        String floorPlan = "";

        try {
            floorPlan = Files.readString(floorPlanPath);
        } catch(IOException e) {
            System.out.println("IOException when attempting to read " + args[0] + e.getMessage());
            System.out.println("Exiting");
            System.exit(1);
        }

        FloorSolver solver = new FloorSolver(floorPlan);
        System.out.println("Loop positions found: " + solver.determineLoopPositions());
    }


}