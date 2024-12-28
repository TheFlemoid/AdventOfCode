
import com.dobby.utils.struct.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordsearchSolver {
    final String puzzleInput;
    final char[] XMAS_CHARS = {'X', 'M', 'A', 'S'};
    final char[] MAS_CHARS = {'M', 'A', 'S'};
    char[][] puzzleArray;

    public WordsearchSolver(final String puzzleInput) {
        this.puzzleInput = puzzleInput;

        initializeCharArray();
    }

    /**
     * Solves Part One of Day 4s problem
     */
    public void solvePartOne() {
        int matches = findTotalWordMatches();
        System.out.printf("Total number of \'XMAS\' matches found: %d\n", matches);
    }

    /**
     * Solves Part Two of Day 4s problem
     */
    public void solvePartTwo() {
        int matches = 0;

        for (int i = 0; i < puzzleArray.length; i++) {
            for (int j = 0; j < puzzleArray[i].length; j++) {
                if(isMasX(new Pair<>(j, i))) {
                    matches++;
                }
            }
        }

        System.out.printf("Total number of \'MAS\' Xs found: %d\n", matches);
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

    public int findTotalWordMatches() {
        int totalCount = 0;

        for (int i = 0; i < puzzleArray.length; i++) {
            for (int j = 0; j < puzzleArray[i].length; j++) {
                for (Direction direction : Direction.values()) {
                    if (isWordMatch(new Pair<Integer, Integer>(j, i), direction, 0)) {
                        totalCount++;
                    }
                }
            }
        }

        return totalCount;
    }

    private boolean isMasX(final Pair<Integer, Integer> gridPosition) {
        int colPos = gridPosition.getKey();
        int rowPos = gridPosition.getValue();

        // If this isn't an 'A' character, return false
        if (puzzleArray[colPos][rowPos] != MAS_CHARS[1]) {
            return false;
        }

        return (isValidDiagonal(gridPosition, Direction.UP_RIGHT, Direction.DOWN_LEFT) &&
                isValidDiagonal(gridPosition, Direction.UP_LEFT, Direction.DOWN_RIGHT));
    }

    private boolean isValidDiagonal(final Pair<Integer, Integer> gridPosition, final Direction firstDirection,
                                    final Direction secondDirection) {
        Pair<Integer, Integer> firstDiagonal = getNextCell(firstDirection, gridPosition);
        Pair<Integer, Integer> secondDiagonal = getNextCell(secondDirection, gridPosition);

        // If either diagonal position is off of the array, then return false
        if (!isPosWithinBound(firstDiagonal) || !isPosWithinBound(secondDiagonal)) {
            return false;
        }

        // Assuming it's on the board, return if the diagonals are M&S or S&M
        return ((puzzleArray[firstDiagonal.getKey()][firstDiagonal.getValue()] == MAS_CHARS[0] &&
                 puzzleArray[secondDiagonal.getKey()][secondDiagonal.getValue()] == MAS_CHARS[2]) ||
                (puzzleArray[firstDiagonal.getKey()][firstDiagonal.getValue()] == MAS_CHARS[2] &&
                 puzzleArray[secondDiagonal.getKey()][secondDiagonal.getValue()] == MAS_CHARS[0]));
    }

    private boolean isPosWithinBound(Pair<Integer, Integer> gridPosition) {
        return !(gridPosition.getKey() < 0 || gridPosition.getKey() > puzzleArray[0].length - 1 ||
            gridPosition.getValue() < 0 || gridPosition.getValue() > puzzleArray.length - 1);
    }

    /**
     * Given a column (x) and row (y) character, determine if the characters in the param Direction match
     * the characters in the XMAS_CHARS array.
     * Note: This method is recursive.  If it finds a match between the character at the param index and the
     *       current character in the XMAS_CHARS array, it calls itself to determine if the next character
     *       matches.  If it reaches the end of the XMAS_CHARS array, we assume a match.
     *
     * @param colIndex column (x) of the character to check in the puzzleArray character array
     * @param rowIndex row (y) of the character to check in the puzzleArray character array
     * @param depth index of the XMAS_CHARS array to search for
     * @return true if there is a match, false otherwise
     */
    private boolean isWordMatch(final Pair<Integer, Integer> gridPosition, final Direction direction, final int depth) {
        int colIndex = gridPosition.getKey();
        int rowIndex = gridPosition.getValue();

        // If we made it to the index past the length of XMAS_CHARS, then all characters matched so this is good
        if (depth == XMAS_CHARS.length) {
            return true;
        }

        // If this search would be outside the row or column scope of the word search, return false as this can't be
        // a match.
        if (colIndex < 0 || colIndex > puzzleArray[0].length - 1 ||
            rowIndex < 0 || rowIndex > puzzleArray.length - 1) {
            return false;
        }

        // If the character at this location does not match the character at this depth of XMAS_CHARS, return false
        if (puzzleArray[rowIndex][colIndex] != XMAS_CHARS[depth]) {
            return false;
        }

        // If we made it this far, then this character exists in the array and it matches the character that it should,
        // so we recurse to the next character.
        Pair<Integer, Integer> nextPosition = getNextCell(direction, new Pair<Integer, Integer>(colIndex, rowIndex));
        int nextDepth = depth + 1;

        return isWordMatch(nextPosition, direction, nextDepth);
    }

    public void printCharArray() {
        for(int i = 0; i < puzzleArray.length; i++) {
            for(int j = 0; j < puzzleArray[i].length; j++) {
                System.out.printf("%c", puzzleArray[i][j]);
            }
            System.out.printf("\n");
        }
    }

    public Pair<Integer, Integer> getNextCell(final Direction direction, final Pair<Integer, Integer> currentCell) {
        int colIndex = currentCell.getKey();
        int rowIndex = currentCell.getValue();

        switch(direction) {
            case UP:
                rowIndex--;
                break;
            case UP_RIGHT:
                colIndex++;
                rowIndex--;
                break;
            case RIGHT:
                colIndex++;
                break;
            case DOWN_RIGHT:
                colIndex++;
                rowIndex++;
                break;
            case DOWN:
                rowIndex++;
                break;
            case DOWN_LEFT:
                colIndex--;
                rowIndex++;
                break;
            case LEFT:
                colIndex--;
                break;
            case UP_LEFT:
                colIndex--;
                rowIndex--;
                break;
            default:
                break;
        }

        return new Pair<Integer, Integer>(colIndex, rowIndex);
    }
}
