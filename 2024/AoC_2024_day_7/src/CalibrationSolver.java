
import com.dobby.utils.struct.Pair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CalibrationSolver {
    private List<Pair<Long, Integer[]>> calibrationList;

    public CalibrationSolver(final String puzzleInputPath) {
        calibrationList = new ArrayList<>();

        initializeCalibrationList(puzzleInputPath);
        isCalibrationValidWithConcat(calibrationList.get(0));
    }

    public void solvePartOne() {
        long sumOfValidCals = 0;

        for (int i = 0 ; i < calibrationList.size(); i++) {
            if (isCalibrationValid(calibrationList.get(i))) {
                sumOfValidCals += calibrationList.get(i).getKey();
            }
        }

        System.out.printf("Part one, sum of valid calibrations: %d\n", sumOfValidCals);
    }

    private boolean isCalibrationValid(Pair<Long, Integer[]> calibration) {
        boolean isValid = false;
        int numOperators = calibration.getValue().length - 1;

        // Bitshift 1s to the left to generate the 'top' of the range of permutations
        int range = (1 << numOperators) - 1;

        for (int i = 0; i <= range & !isValid; i++) {
            long total = calibration.getValue()[0];
            for (int j = 0; j < numOperators; j++) {
                // If there is a 1 in the j'th position of i, multiply the two operands, otherwise add them
                if (((i >> j) & 1) == 1) {
                    total = total * calibration.getValue()[j + 1];
                } else {
                    total = total + calibration.getValue()[j + 1];
                }
            }
            if (total == calibration.getKey()) {
                isValid = true;
            }
        }

        return isValid;
    }

    private boolean isCalibrationValidWithConcat(final Pair<Long, Integer[]> calibration) {
        boolean isValid = false;
        int numOperators = calibration.getValue().length - 1;

        // Shift two bits to the left for each operator to get to range.  We're going to pull off two bits at a
        // time to determine the operation.  Ie:
        // 00 - Add
        // 01 - Multiply
        // 10 - Concatenate
        // 11 - NOP
        int range = (1 << (2 * numOperators)) - 1;

        for (int i = 0; i <= range; i++) {
            System.out.printf("Testing mask %d: ", i);
            long total = calibration.getValue()[0];
            for (int j = 0; j < numOperators; j++) {
                // Shift out two bits for every operator
                int operation = (i >> (2 * j) & 3);
                switch (operation) {
                    case 0:
                        total = total + calibration.getValue()[j+1];
                        break;
                    case 1:
                        total = total * calibration.getValue()[j+1];
                        break;
                    case 2:
                        total = concatLong(total, calibration.getValue()[j+1]);
                        break;
                    case 3:
                        // NOP
                        break;
                    default:
                        break;
                }
            }
            System.out.printf("  Result: %d\n", total);
            if (total == calibration.getKey()) {
                isValid = true;
            }
        }

        //printCalibration(calibration);
        //System.out.printf("Range is: %d\n", range);

        return isValid;
    }

    private void initializeCalibrationList(final String puzzleInputPath) {
        try {
            FileReader in = new FileReader(puzzleInputPath);
            BufferedReader br = new BufferedReader(in);

            String line;
            while ((line = br.readLine()) != null) {
                String[] firstCut = line.split(": ");
                Long calibrationTotal = Long.parseLong(firstCut[0]);

                String[] secondCut = firstCut[1].split(" ");
                Integer[] calValues = new Integer[secondCut.length];

                for(int i = 0; i < secondCut.length; i++) {
                    calValues[i] = Integer.parseInt(secondCut[i]);
                }
                calibrationList.add(new Pair<>(calibrationTotal, calValues));
            }
        }catch (IOException e) {
            System.out.println("Exception when reading puzzle input data.  Exiting.");
            System.exit(1);
        }
    }

    private long concatLong(final long firstLong, final long secondLong) {
        return Long.parseLong(String.format("%d%d", firstLong, secondLong));
    }

    public void printCalibration(Pair<Long, Integer[]> calibration) {
        System.out.printf("%d : ", calibration.getKey());
        for (int i = 0; i < calibration.getValue().length; i++) {
            System.out.printf("%d ", calibration.getValue()[i]);
        }
        System.out.printf("\n");
    }

    public void printCalibrationList() {
        for (int i = 0; i < calibrationList.size(); i++) {
            printCalibration(calibrationList.get(i));
        }
    }
}
