
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateSolver {
    private String updateRulesPath;
    private String pageUpdateListPath;
    private List<UpdateRule> updateRuleList;
    private List<Integer[]> pageUpdateList;
    private List<Integer> nonComformingUpdateList;

    /**
     * Takes in rules and updates from the param files, and checks updates to ensure they conform to rules.
     *
     * @param updateRulesPath String filepath to the update rules file
     * @param updateListPath String filepath to the page updates file
     */
    public UpdateSolver(final String updateRulesPath, final String pageUpdateListPath) {
        this.updateRulesPath = updateRulesPath;
        this.pageUpdateListPath = pageUpdateListPath;

        initializeUpdateRuleList();
        initializePageUpdateList();
    }

    public int checkUpdatesAndAddMiddleValues() {
        int sumOfMiddleDigits = 0;

        for (int i = 0; i < pageUpdateList.size(); i++) {
            if (checkUpdateFollowsRules(pageUpdateList.get(i))) {
                int midIndex = (pageUpdateList.get(i).length / 2);
                sumOfMiddleDigits += pageUpdateList.get(i)[midIndex];

                System.out.printf("Conforms: ");
                for (int j = 0; j < pageUpdateList.get(i).length; j++) {
                    System.out.printf("%d ", pageUpdateList.get(i)[j]);
                }
                System.out.printf(" Adding " + pageUpdateList.get(i)[midIndex] + "\n");
            }
        }

        return sumOfMiddleDigits;
    }

    /**
     * Initializes the update rule array list with values from the file at the provided updateRulesPath
     */
    private void initializeUpdateRuleList() {
        updateRuleList = new ArrayList<>();

        try {
            FileReader in = new FileReader(updateRulesPath);
            BufferedReader br = new BufferedReader(in);
            String line;
            String[] ruleArray = new String[2];

            while ((line = br.readLine()) != null) {
                ruleArray = line.split("\\|");
                updateRuleList.add(new UpdateRule(Integer.parseInt(ruleArray[0]), Integer.parseInt(ruleArray[1])));
            }
        } catch(IOException e) {
            System.out.println("Exception when attempting to read update rules at " + updateRulesPath);
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void initializePageUpdateList() {
        pageUpdateList = new ArrayList<>();

        try {
            FileReader in = new FileReader(pageUpdateListPath);
            BufferedReader br = new BufferedReader(in);
            String line;

            // Read each update in, split the line on commas, and parse out an integer of each page.
            // Store the update as an int array, with pages to be updated indexed left to right.
            while ((line = br.readLine()) != null) {
                String[] pageStrings = line.split(",");
                Integer[] pageUpdate = new Integer[pageStrings.length];
                for (int i=0; i < pageStrings.length; i++) {
                    pageUpdate[i] = Integer.parseInt(pageStrings[i]);
                }
                pageUpdateList.add(pageUpdate);
            }
        } catch(IOException e) {
            System.out.println("Exception when attempting to read page updates at " + pageUpdateListPath);
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Checks the param pageUpdate array and returns true if that update follows all defined
     * update rules, false otherwise.
     *
     * @param pageUpdate Integer array of update to check
     * @return true if the update comforms to all update rules, false otherwise
     */
    private boolean checkUpdateFollowsRules(final Integer[] pageUpdate) {
        boolean followsRules = true;
        for (int i = 0; i < updateRuleList.size() && followsRules; i++) {
            // If the update does not contain either of the rule pages, ignore this rule
            if (!containsInt(pageUpdate, updateRuleList.get(i).getFirstPage()) ||
                !containsInt(pageUpdate, updateRuleList.get(i).getSecondPage())) {
                continue;
            }

            // At this point we know that the update contains both rule pages, so we check that page 1
            // comes before page 2
            boolean shouldBreak = false;
            for (int j = 0; j < pageUpdate.length && !shouldBreak; j++) {
                if (pageUpdate[j] == updateRuleList.get(i).getFirstPage()) {
                    followsRules = true;
                    shouldBreak = true;
                } else if(pageUpdate[j] == updateRuleList.get(i).getSecondPage()) {
                    followsRules = false;
                    shouldBreak = true;
                } else {
                    shouldBreak = false;
                }
            }
        }

        return followsRules;
    }

    /**
     * Returns true if the param key is contained in the param intArray, false otherwise
     *
     * @param intArray Integer array to check
     * @param key Integer to check for
     * @return true if the key is in the array, false otherwise
     */
    private boolean containsInt(Integer[] intArray, int key) {
        return Arrays.stream(intArray).anyMatch(n -> n == key);
    }

    /**
     * Private inner class that defines one 'update rule'.  An update rule consists of two page
     * numbers, the rule being that if both of those pages are going to be printed, the rules
     * firstPage must be printed BEFORE the rules secondPage.
     */
    private class UpdateRule {
        int firstPage;
        int secondPage;

        public UpdateRule(final int firstPage, final int secondPage) {
            this.firstPage = firstPage;
            this.secondPage = secondPage;
        }

        public int getFirstPage() {
            return firstPage;
        }

        public int getSecondPage() {
            return secondPage;
        }
    }
}
