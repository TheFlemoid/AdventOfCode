public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction nextRightTurn(final Direction direction) {
        Direction retVal;
        switch (direction) {
            case NORTH:
                retVal = EAST;
                break;
            case EAST:
                retVal = SOUTH;
                break;
            case SOUTH:
                retVal = WEST;
                break;
            case WEST:
                retVal = NORTH;
                break;
            // Can never get here, but it makes the compiler happy.
            default:
                retVal = NORTH;
                break;
        }
        return retVal;
    }
}
