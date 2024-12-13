
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionSolver {
    private String inputPath;
    private String instructionContent;

    public InstructionSolver(final String inputPath) {
        this.inputPath = inputPath;

        initializeInstructionContent();
    }

    public void solveProblem() {
        System.out.println("Problem 1: " + searchAndSolvMult());
        System.out.println("Problem 2: " + searchAndSolvConditionalMult());
    }

    private long searchAndSolvMult() {
        long retVal = 0;
        Pattern p = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

        Matcher matcher = p.matcher(instructionContent);
        while(matcher.find()) {
            retVal += Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
        }

        return retVal;
    }

    private long searchAndSolvConditionalMult() {
        long retVal = 0;
        boolean multEnabled = true;
        Pattern totalPattern = Pattern.compile("do\\(\\)|don\'t\\(\\)|mul\\(\\d{1,3},\\d{1,3}\\)");
        Pattern enablePattern = Pattern.compile("do\\(\\)");
        Pattern disablePattern = Pattern.compile("don\'t\\(\\)");
        Pattern multPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

        Matcher totalMatcher = totalPattern.matcher(instructionContent);
        Matcher enableMatcher;
        Matcher disableMatcher;
        Matcher multPatternMatcher;

        while(totalMatcher.find()) {
            enableMatcher = enablePattern.matcher(totalMatcher.group());
            disableMatcher = disablePattern.matcher(totalMatcher.group());
            multPatternMatcher = multPattern.matcher(totalMatcher.group());

            if(enableMatcher.find()) {
                multEnabled = true;
            } else if(disableMatcher.find()) {
                multEnabled = false;
            } else if(multPatternMatcher.find() && multEnabled) {
                retVal += Long.parseLong(multPatternMatcher.group(1)) * Long.parseLong(multPatternMatcher.group(2));
            }
        }

        return retVal;
    }

    private void initializeInstructionContent() {
        try {
            FileReader in = new FileReader(inputPath);
            BufferedReader br = new BufferedReader(in);

            String line;
            while((line = br.readLine()) != null) {
                // It appears that the puzzle data is one line, so just
                // doing that.
                instructionContent = line;
            }
        }catch(IOException e) {
            System.out.println("Exception when parsing input data.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
