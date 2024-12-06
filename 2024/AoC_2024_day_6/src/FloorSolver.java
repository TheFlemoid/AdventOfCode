public class FloorSolver {
    // Number of steps, after which we assume the guard is in a loop
    private static final int MAX_STEPS = 10000000;

    private String floorPlan;
    private char[][] floorArray;
    private int initialX;
    private int initialY;
    private int floorWidth;
    private int floorHeight;

    public FloorSolver(final String floorPlan) {
        this.floorPlan = floorPlan;
        initBoard();
    }

    /**
     * Initialize the char[][] floorArray board, used for simulation,
     * based on the floorPlan String passed in from Main.
     */
    private void initBoard() {
        int newLineCount = 0;
        int colCount = 0;
        for (int i = 0; i < floorPlan.length(); i++) {
            if (floorPlan.charAt(i) == '\n') {
                newLineCount++;
            }
            if (newLineCount == 0) {
                colCount++;
            }
        }

        floorWidth = colCount - 1;
        floorHeight = newLineCount - 1;
        floorArray = new char[colCount][newLineCount];
        int currentRow = 0;
        int currentCol = 0;
        for (int i = 0; i < floorPlan.length(); i++) {
            if (floorPlan.charAt(i) != '\n') {
                floorArray[currentRow][currentCol] = floorPlan.charAt(i);
                currentCol++;
            }

            if (currentCol == colCount) {
                currentCol = 0;
                currentRow++;
            }
        }

        // Determine the initial x/y coordinates of the guard.
        boolean found = false;
        for (int i = 0; i < floorArray.length & !found; i++) {
            for (int j = 0; j < floorArray[i].length & !found; j++) {
                if (floorArray[i][j] == '^') {
                    initialX = j;
                    initialY = i;
                    found = true;
                }
            }
        }
    }

    /**
     * Simulates a board run
     *
     * @return true if the guard was able to leave the room, and false if the guard
     *         did not leave the room and is in a presumed loop
     */
    public boolean simulateBoardRun() {
        int currentX = initialX;
        int currentY = initialY;
        Direction direction = Direction.NORTH; // Assume starting direction to be NORTH

        int stepsTaken = 0;

        boolean offBoard = false;
        Movement nextMove;
        while (!offBoard && stepsTaken < MAX_STEPS) {
            // Set the spot that we're currently at to X to mark we've been there
            floorArray[currentY][currentX] = 'X';
            nextMove = getNextValue(currentX, currentY, direction);

            // If the next move would take us off the board, then the simulation is done.
            if (nextMove.getLocationValue() == 'W') {
                offBoard = true;
            } else if (nextMove.getLocationValue() == '#' || nextMove.getLocationValue() == 'O') {
                // If the next move is an obstacle, don't move and instead turn right.
                direction = Direction.nextRightTurn(direction);
            } else {
                currentX = nextMove.getXPos();
                currentY = nextMove.getYPos();
                stepsTaken++;
            }
        }

        // With the simulation done, count the number of 'X' cells to get all distinct positions.
        //int positionCount = 0;
        //for (int i = 0; i < floorArray.length; i++) {
        //    for (int j = 0; j < floorArray[i].length; j++) {
        //        if (floorArray[i][j] == 'X') {
        //            positionCount++;
        //        }
        //    }
        //}

        return offBoard;
    }

    /**
     * Determines all positions on the board where we could introduce one obstacle which would
     * cause the guard to go into an endless loop.
     * NOTE: This is done the 'brute force' way by running a simulation for every possible place
     *       that could become an obstacle (ie isn't currently an obstacle, and isn't the guards
     *       starting position, and for each of those tests iterating under two conditions:
     *          - the guard hasn't left the room, and
     *          - the guard hasn't taken more than MAX_STEPS steps
     *       I'm positive that there is a more clever way to do this (especially a more clever way
     *       to detect a loop, rather than just picking a large number of steps and assuming), but
     *       this was the most obvious way I could think to do this.
     *
     * @return Integer representing the number of positions on the board that could be turned into
     *         an obstacle that would cause the guard to go into an endless loop
     */
    public int determineLoopPositions() {
        int loopsFound = 0;

        for (int i = 0; i < floorArray.length; i++) {
            for (int j = 0; j < floorArray[i].length; j++) {
                // Only run the simulation if there isn't already an obstacle there, and
                // if this position is not the starting position.
                if (floorArray[i][j] != '#' && floorArray[i][j] != '^') {
                    floorArray[i][j] = 'O';
                    boolean leftRoom = simulateBoardRun();
                    if (!leftRoom) {
                        loopsFound++;
                        System.out.println("Loop found at x:" + j + ", y:" + i + ", loop count: " + loopsFound);
                    }
                    resetFloorArray();
                }
            }
        }

        return loopsFound;
    }

    /**
     * Calculates the x/y coordinates of the next movement, and the value of the next square.
     * Will return a value of 'W' if the next coordinate in that direction is off of the grid.
     *
     * @param currentX Integer representing the current x coordinate
     * @param currentY Integer representing the current y coordinate
     * @param direction Direction that the guard is moving
     * @return Movement object of a movement in the given direction, or 'W' if the next
     *         movement would move off the grid.
     */
    private Movement getNextValue(final int currentX, final int currentY,
                              final Direction direction) {
        int nextX = 0;
        int nextY = 0;
        char nextLocationValue;

        switch (direction) {
            case NORTH:
                nextX = currentX;
                nextY = currentY - 1;
                break;
            case EAST:
                nextX = currentX + 1;
                nextY = currentY;
                break;
            case SOUTH:
                nextX = currentX;
                nextY = currentY + 1;
                break;
            case WEST:
                nextX = currentX - 1;
                nextY = currentY;
                break;
        }

        if (nextX > floorWidth || nextX < 0 || nextY > floorHeight || nextY < 0) {
            nextLocationValue = 'W';
        } else {
            nextLocationValue = floorArray[nextY][nextX];
        }

        return new Movement(nextX, nextY, nextLocationValue);
    }

    /**
     * Resets the floor array, converting 'X' characters to '.' and
     * resetting the guard to the original position.
     */
    private void resetFloorArray() {
        for (int i = 0; i < floorArray.length; i++) {
            for (int j = 0; j < floorArray[i].length; j++) {
                if (floorArray[i][j] == 'X' || floorArray[i][j] == 'O') {
                    floorArray[i][j] = '.';
                }
            }
        }

        floorArray[initialY][initialX] = '^';
    }

    public void printFloorArray() {
        for (int i = 0; i < floorArray.length; i++) {
            for (int j = 0; j < floorArray[i].length; j++) {
                System.out.printf("%c", floorArray[i][j]);
            }
            System.out.printf("\n");
        }
    }

    /**
     * Tuple that contains an x/y coordinate, and the char value of that coordinate from the map.
     */
    private static class Movement {
        int xPos;
        int yPos;
        char locationValue;

        public Movement(final int xPos, final int yPos, final char locationValue) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.locationValue = locationValue;
        }

        public int getXPos() {
            return xPos;
        }

        public int getYPos() {
            return yPos;
        }

        public char getLocationValue() {
            return locationValue;
        }
    }
}
