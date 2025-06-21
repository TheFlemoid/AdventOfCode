import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalibrationSolver {
    private String puzzleInputPath;
    private List<String> calibrationList;

    public CalibrationSolver(final String puzzleInputPath) {
        this.puzzleInputPath = puzzleInputPath;
        initializeCalList();
    }

    public int solveFirstProblem() {
        int retVal = 0;

        for (String cal : calibrationList) {
            retVal += fixCalLineDigitsOnly(cal);
        }

        return retVal;
    }

    public int solveSecondProblem() {
        int retVal = 0;

        for (String cal : calibrationList) {
            retVal += fixCalLineDigitsAndSpelling(cal);
        }

        return retVal;
    }

    /**
     * Parse through the param String character by character, testing each character to see if it's a digit.
     * If it is a digit and no digit has been found thus far, set the first and second digit to that value (since
     * some calibrations contain only one digit, and hence that's the first and last value).  If a digit has previously
     * been found, then set the second digit to the found value.  Return the int composed of the first and second
     * digit concatenated together.
     *
     * @param calibration String calibration line from the calibration list
     * @return The first and last integers found in the calibration list, concatenated together, as an int
     */
    private int fixCalLineDigitsOnly(String calibration) {
        boolean firstIntSet = false;
        int firstInt = -1;
        int secondInt = -1;

        for (int i = 0; i < calibration.length(); i++) {
            char calChar = calibration.charAt(i);
            if (Character.isDigit(calChar)) {
                String charString = String.format("%c", calChar);
                int number = Integer.parseInt(charString);

                if (!firstIntSet) {
                    firstInt = number;
                    secondInt = number;
                    firstIntSet = true;
                } else {
                    secondInt = number;
                }
            }
        }

        if ((firstInt != -1) && (secondInt != -1)) {
            String numString = String.format("%d%d", firstInt, secondInt);
            return Integer.parseInt(numString);
        } else {
            System.out.println("Cal with no digits found, this is a problem.");
            return -1;
        }
    }

    /**
     * Parse through the param String character by character.  If a digit is found, set the first/second integer
     * value as is done in the "DigitsOnly" method.  If a non digit character is found, append that character to
     * a String builder to test if a number (as defined in the Spellings enum) has been spelled out.  If a spelled
     * number is detected, set that value as the first/second integer in the same way that a digit would be set.
     *
     * NOTE that if we find a spelled integer we clear the StringBuilder of everything except for the last found
     * letter.  This is because the end of one word could be the beginning of a second word (eg. oneight).  Inspecting
     * the list of spellings, it appears that at most one letter can overlap like this, so we keep the last character.
     *
     * NOTE that if we find a digit, we clear out the StringBuilder entirely since a digit cannot be used in any
     * spelled numbers.
     *
     * @param calibration String calibration line from the calibration list
     * @return The first and last integers found in the calibration list, concatenated together, as an int
     */
    private int fixCalLineDigitsAndSpelling(String calibration) {
        boolean firstIntSet = false;
        int firstInt = -1;
        int secondInt = -1;
        StringBuilder testSpelling = new StringBuilder();

        for (int i = 0; i < calibration.length(); i++) {
            char calChar = calibration.charAt(i);
            String charString = String.format("%c", calChar);
            if (Character.isDigit(calChar)) {
                int number = Integer.parseInt(charString);
                testSpelling.delete(0, testSpelling.length());

                if (!firstIntSet) {
                    firstInt = number;
                    secondInt = number;
                    firstIntSet = true;
                } else {
                    secondInt = number;
                }
            } else {
                testSpelling.append(calChar);
                boolean found = false;
                int spelledValue = -1;

                for (Spellings spelling : Spellings.values()) {
                    // If we find a match, get the value and then null the testSpelling string to prevent additional
                    // matches of the same input
                    if (testSpelling.toString().contains(spelling.getName())) {
                        found = true;
                        spelledValue = spelling.getValue();
                        testSpelling.delete(0, testSpelling.length() - 1);
                    }
                }

                if (found) {
                    if (!firstIntSet) {
                        firstInt = spelledValue;
                        secondInt = spelledValue;
                        firstIntSet = true;
                    } else {
                        secondInt = spelledValue;
                    }
                }
            }
        }

        if ((firstInt != -1) && (secondInt != -1)) {
            String numString = String.format("%d%d", firstInt, secondInt);
            return Integer.parseInt(numString);
        } else {
            System.out.println("Cal with no digits found, this is a problem.");
            return -1;
        }
    }

    private void initializeCalList() {
        calibrationList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(puzzleInputPath));
            String line;
            while ((line = reader.readLine()) != null) {
                calibrationList.add(line);
            }
        } catch(IOException e) {
            System.out.printf("IOException while reading input data : %s\n", e.getMessage());
        }
    }

    public void printCalList() {
        for (String calibration : calibrationList) {
            System.out.printf("%s\n", calibration);
        }
    }
}
