
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordsearchSolver {
    final String puzzleInput;
    final char[] XMAS_CHARS = {'X', 'M', 'A', 'S'};
    char[][] puzzleArray;

    public WordsearchSolver(final String puzzleInput) {
        this.puzzleInput = puzzleInput;

        initializeCharArray();
        printCharArray();
    }

    private void initializeCharArray() {
        // Cheating since I know how long the input is
        puzzleArray = new char[140][];

        try {
            FileReader in = new FileReader(puzzleInput);
            BufferedReader reader = new BufferedReader(in);
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                puzzleArray[i] = line.toCharArray();
                i++;
            }
        }catch(IOException e) {
            System.out.println("Exception when reading input data.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public int findWordCount() {
        int totalCount = 0;

        for (int i = 0; i < )

        return totalCount;
    }

    public void printCharArray() {
        for(int i = 0; i < puzzleArray.length; i++) {
            for(int j = 0; j < puzzleArray[i].length; j++) {
                System.out.printf("%c", puzzleArray[i][j]);
            }
            System.out.printf("\n");
        }
    }
}
