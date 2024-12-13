
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListSolver {
    private String inputPath;
    private List<Integer> listOne;
    private List<Integer> listTwo;

    public ListSolver(final String inputPath) {
        this.inputPath = inputPath;

        initializeLists();
    }

    public void solveProblem() {
        sortList(listOne);
        sortList(listTwo);

        System.out.println("Problem 1: " + sumDifferences(listOne, listTwo));
        System.out.println("Problem 2: " + sumRepetitiveNumbers(listOne, listTwo));
    }

    private int sumDifferences(final List<Integer> listA, final List<Integer> listB) {
        int retVal = 0;

        for(int i = 0; i < listA.size(); i++) {
            retVal += Math.abs(listA.get(i) - listB.get(i));
        }

        return retVal;
    }

    private int sumRepetitiveNumbers(final List<Integer> listA, final List<Integer> listB) {
        int retVal = 0;

        int target = 0;
        int timesSeen = 0;
        for(int i = 0; i < listA.size(); i++) {
            target = listA.get(i);
            timesSeen = 0;

            for(int j = 0; j < listB.size(); j++) {
                if(listB.get(j) == target) {
                    timesSeen++;
                }
            }

            retVal += (target * timesSeen);
        }

        return retVal;
    }

    private void initializeLists() {
        listOne = new ArrayList<>();
        listTwo = new ArrayList<>();

        try {
            FileReader in = new FileReader(inputPath);
            BufferedReader br = new BufferedReader(in);

            String line;
            while((line = br.readLine()) != null) {
                String[] numbers = line.split(",");
                listOne.add(Integer.parseInt(numbers[0]));
                listTwo.add(Integer.parseInt(numbers[1]));
            }

        }catch(IOException e) {
            System.out.println("IOException while reading puzzle input.");
            System.exit(1);
        }
    }

    private void sortList(List<Integer> list) {
        for(int i = 0; i < list.size(); i++) {
            int minIndex = i;
            for(int j = i; j < list.size(); j++) {
                if(list.get(j) < list.get(minIndex)) {
                    minIndex = j;
                }
            }

            if(minIndex != i) {
                Collections.swap(list, i, minIndex);
            }
        }
    }

    public void printLists() {
        for(int i = 0; i < listOne.size(); i++) {
            System.out.printf("%d %d\n", listOne.get(i), listTwo.get(i));
        }
    }
}
