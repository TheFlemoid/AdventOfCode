
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportSolver {
    private String puzzleInputPath;
    List<Integer[]> reportList;

    public ReportSolver(final String inputPath) {
        puzzleInputPath = inputPath;

        initializeReportList();
    }

    public void solveProblem() {
        System.out.println("Problem 1: Safe report count " + getSafeReports());
        System.out.println("Problem 2: Safe plus fixable report count: " + getSafeAndFixableReports());
    }

    private int getSafeReports() {
        int safeReports = 0;

        for(Integer[] report : reportList) {
            if(isReportSafe(report)) {
                safeReports++;
            }
        }

        return safeReports;
    }

    private int getSafeAndFixableReports() {
        int safeReports = 0;

        for(Integer[] report : reportList) {
            if(isReportSafe(report)) {
                safeReports++;
            } else if(canBeFixed(report)) {
                safeReports++;
            }
        }

        return safeReports;
    }

    private boolean canBeFixed(Integer[] report) {
        boolean fixable = false;

        for(int i = 0; i < report.length & !fixable; i++) {
            Integer[] removeOne = new Integer[report.length-1];
            int arrayIndex=0;
            for(int j = 0; j < report.length; j++) {
                if(j != i) {
                    removeOne[arrayIndex] = report[j];
                    arrayIndex++;
                }
            }

            if(isReportSafe(removeOne)) {
                fixable = true;
            }
        }

        return fixable;
    }

    private boolean isReportSafe(Integer[] report) {
        boolean reportSafe = true;
        boolean isIncrementing = (report[1] - report[0]) > 0;

        int delta;
        for(int i = 0; (i < report.length - 1) && reportSafe; i++) {
            delta = report[i+1] - report[i];

            if(isIncrementing && delta < 0 || !isIncrementing && delta > 0) {
                reportSafe = false;
            }

            if((Math.abs(delta) > 3) || (Math.abs(delta) < 1)) {
                reportSafe = false;
            }
        }

        return reportSafe;
    }

    private void initializeReportList() {
        reportList = new ArrayList<>();

        try {
            FileReader in = new FileReader(puzzleInputPath);
            BufferedReader br = new BufferedReader(in);
            String line;

            while((line = br.readLine()) != null) {
                String[] numbers = line.split(" ");
                Integer[] report = new Integer[numbers.length];
                for(int i = 0; i < numbers.length; i++) {
                    report[i] = Integer.parseInt(numbers[i]);
                }
                reportList.add(report);
            }
        }catch(IOException e) {
            System.out.println("Exception when reading input data.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void printReports() {
        for(int i = 0; i < reportList.size(); i++) {
            for(int j = 0; j < reportList.get(i).length; j++) {
                System.out.printf("%d ", reportList.get(i)[j]);
            }
            System.out.printf("\n");
        }
    }
}
