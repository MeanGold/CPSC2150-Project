package cpsc2150.extendedConnectX.models;

/*
 * GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
 *   Benjamin McDonnough - bmcdonnough
 *   Jonathan Maenche    - MeanGold
 *   Christian Herrman   - echerrman
 *   Evan Cox            - evanacox
 */

public class BoardPosition {
    /**
     * Class for each position on the board, containing the associated row and column value
     *
     * @invariant 0 <= row < NUM_ROWS AND 0 <= column < NUM_COLUMNS
     */
    private int row;
    private int column;

    /**
     * Constructor for the BoardPosition that will
     * create an object at the received row and column values
     *
     * @pre 0 <= aRow < NUM_ROWS AND 0 <= aColumn < NUM_COLUMNS
     *
     * @post row = aRow AND column = aColumn
     *
     * @param aRow    the row number being represented
     * @param aColumn the column number being represented
     *
     */
    public BoardPosition(int aRow, int aColumn) {
        row = aRow;
        column = aColumn;
    }

    /**
     * Returns the row number of the represented board position
     *
     * @pre None
     *
     * @post row = #row AND column = #column
     * @post getRow = row
     *
     * @return the variable with the row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column number of the represented board position
     *
     * @pre None
     *
     * @post row = #row AND column = #column
     * @post getColumn = column
     *
     * @return the variable with the column number
     *
     */
    public int getColumn() {
        return column;
    }

    /**
     * Checks to see if the current BoardPosition object is equal to another object
     *
     * @pre None
     *
     * @post row = #row AND column = #column
     * @post equals = [returns true if 'obj' is a non-null BoardPosition object with the
     * same row/column value as the object that function is called on]
     *
     * @param obj the object being compared against
     *
     * @return [true if 'obj' has same row and column values as the BoardPosition object, false otherwise]
     *
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof BoardPosition)) {
            return false;
        } else {
            BoardPosition sub = (BoardPosition) obj;

            // If row AND column values of the two objects are equal, set returnVal to true
            return row == sub.getRow() && column == sub.getColumn();
        }
    }

    /**
     * This function will return a stringified representation of the
     * BoardPosition in the format "x,y"
     *
     * @pre None
     *
     * @post row = #row AND column = #column
     * @post toString = [returns string in the format "r,c" where r = row and c = column]
     *
     * @return a string in format "x,y"
     *
     */
    @Override
    public String toString() {
        return this.row + "," + this.column;
    }
}
