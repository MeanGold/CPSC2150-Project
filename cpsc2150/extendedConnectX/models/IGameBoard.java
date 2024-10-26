package cpsc2150.extendedConnectX.models;

/*
 * GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
 *   Benjamin McDonnough - bmcdonnough
 *   Jonathan Maenche    - MeanGold
 *   Christian Herrman   - echerrman
 *   Evan Cox            - evanacox
 */

/**
 * A gameboard for a game of ConnectX, similar to Connect4 but with a larger size and varying
 * amount-in-a-row required to win. Locations start at (row=0, col=0). which is the bottom 
 * left cell of the board.
 *
 * Initialization ensures:
 *     the board contains only empty characters and
 *     has NUM_ROWS rows, along with NUM_COLUMNS columns
 *
 * Defines:
 *     NUM_ROWS: the number of rows that the board has
 *     NUM_COLUMNS: the number of columns that the board has
 *     NUM_TO_WIN: the number of tokens in a row needed to 'win'
 *
 * Constraints:
 *     0 < NUM_ROWS
 *     0 < NUM_COLUMNS
 *     0 < NUM_TO_WIN
 *     [A column in the board never has empty tokens "below" an X or O token,
 *      meaning that tokens are always dropped as low as possible in a column]
 */
public interface IGameBoard {
    /**
     * Gets the number of rows that a game board has
     *
     * @return The number of rows
     *
     * @pre None
     *
     * @post self = #self AND getNumRows = NUM_ROWS
     */
    public int getNumRows();

    /**
     * Gets the number of columns that a game board has
     *
     * @return The number of columns
     *
     * @pre None
     *
     * @post self = #self AND getNumColumns = NUM_COLUMNS
     */
    public int getNumColumns();


    /**
     * Gets the number of tokens in a row that constitutes a win
     *
     * @return The number of tokens in a row required to win
     *
     * @pre None
     *
     * @post self = #self AND getNumToWin = NUM_TO_WIN
     */
    public int getNumToWin();

    /**
     * Returns the token at a given position on the board, if one exists.
     *
     * @param pos the BoardPosition that is being checked
     * @return return the token (any player character) that is
     * at the position specified, or a space ' '
     * if no token has been placed at that position
     *
     * @pre None
     *
     * @post whatsAtPos = [if a token has been dropped to `pos` then returns a valid player character,
     *                     otherwise returns ' ']
     * @post self = #self
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * Takes the token that is being placed and drops it into the
     * specified column.
     *
     * @param p the token that is being placed
     * @param c the column that this function is checking
     *
     * @pre 0 <= c < NUM_COLUMNS
     * @pre [p is a valid player character]
     * @pre checkIfFree(c) == true
     *
     * @post [The column c will have the token p at the lowest available row]
     * @post self = #self
     */
    public void dropToken(char p, int c);


    /**
     * Checks to see if the last token that was placed
     * in the column passed into the function
     * is a win (`numToWin()` of the same token in a row).
     * Does not check the entire board.
     *
     * @param c the column that this function is checking
     * @return true if the last token placed results in a win
     * and false if it does not.
     *
     * @pre 0 <= c < NUM_COLUMNS
     *
     * @post checkForWin = [Returns true if and only if the last placed token is the last to make up a
     *                      NUM_TO_WIN-long list of consecutive tokens needed to win in either the vertical,
     *                      horizontal, or diagonal directions]
     * @post self = #self
     */
    public default boolean checkForWin(int c) {
        BoardPosition lastToken = null;

        // find the token in the highest row
        for (int r = 0; r < getNumRows(); ++r) {
            lastToken = new BoardPosition(r, c);

            if (whatsAtPos(lastToken) != ' ') {
                break;
            }
        }

        return checkHorizWin(lastToken, whatsAtPos(lastToken)) ||
                checkVertWin(lastToken, whatsAtPos(lastToken)) ||
                checkDiagWin(lastToken, whatsAtPos(lastToken));
    }


    /**
     * Checks to see if the last token placed results
     * in a win (NUM_IN_A_ROW of the same token in a row) horizontally.
     *
     * @param pos the place that the last token was placed
     * @param p the player who placed the last piece
     * @return true if the token placed by p at pos caused a horizontal NUM_IN_A_ROW-in-a-row
     *
     * @pre [p is a valid player character]
     *
     * @post checkHorizWin = [Returns true if and only if there are NUM_TO_WIN sequential p tokens (including
     *                        the position pos) in the horizontal direction. Returns false otherwise]
     * @post self = #self
     */
    public default boolean checkHorizWin(BoardPosition pos, char p) {
        int numInARow = 0;
        int firstCol = -1;

        // basic idea: check the entire row for NUM_IN_A_ROW-in-row **anywhere**,
        // and then if we find it, see if `pos` is within that NUM_IN_A_ROW-in-a-row.
        for (int i = 0; i < getNumColumns(); ++i) {
            BoardPosition at = new BoardPosition(pos.getRow(), i);
            boolean isAtPos = isPlayerAtPos(at, p);

            // if this is the beginning of a section of P tokens
            // we mark down which column it begins in
            if (isAtPos && numInARow == 0) {
                firstCol = i;
            }

            if (isAtPos) {
                numInARow += 1;
            } else {
                numInARow = 0;
            }

            if (numInARow == getNumToWin()) {
                // see if `p` is between the columns 'firstCol' and 'i'. if it is,
                // it caused the win. (for every run of the game, this should
                // be true because this is checked every round).
                return firstCol <= pos.getColumn() && pos.getColumn() <= i;
            }
        }

        return false;
    }


    /**
     * Checks to see if there is a win (NUM_TO_WIN of the same token in a row)
     * in the column of the last token that was placed.
     *
     * @param pos the place that the last token was placed
     * @param p   the player that played the last piece
     * @return true if the token placed by p at pos caused a vertical NUM_TO_WIN-in-a-row
     *
     * @pre [p is a valid player character]
     *
     * @post checkVertWin = [Returns true if and only if there are NUM_TO_WIN sequential p tokens (including
     *                       the position pos) in the vertical direction. Returns false otherwise]
     * @post self = #self
     */
    public default boolean checkVertWin(BoardPosition pos, char p) {
        int numInARow = 0;
        int firstRow = -1;

        // same idea as with horizontal, except this time its vertical :)
        for (int i = 0; i < getNumRows(); ++i) {
            BoardPosition at = new BoardPosition(i, pos.getColumn());
            boolean isAtPos = isPlayerAtPos(at, p);

            if (isAtPos && numInARow == 0) {
                firstRow = i;
            }

            if (isAtPos) {
                numInARow += 1;
            } else {
                numInARow = 0;
            }

            if (numInARow == getNumToWin()) {
                return firstRow <= pos.getRow() && pos.getRow() <= i;
            }
        }

        return false;
    }


    /**
     * Checks to see if there is a win (NUM_TO_WIN of the same token in a row)
     * diagonally in both diagonal directions.
     *
     * @param pos the place that the last token was placed
     * @param p   the player that played the last piece
     * @return true if the token placed by p at pos caused a diagonal NUM_TO_WIN-in-a-row
     *
     * @pre [p is a valid player character]
     *
     * @post checkDiagWin = [Returns true if and only if there are NUM_TO_WIN sequential p tokens (including
     *                       the position pos) in any diagonal. Returns false otherwise]
     * @post self = #self
     */
    public default boolean checkDiagWin(BoardPosition pos, char p) {
        // both start at 1 since we **include** pos
        int numInARowLeftDiag = 1;
        int numInARowRightDiag = 1;

        // basic idea: we start at `pos` and work our way outwards,
        // counting the number of in-a-row `p` tokens we encounter
        // before we hit a non-`p` token.
        //
        // we exit one loop when we hit a non-`p` token, but we
        // don't reset the variable `numInARowLeftDiag` since
        // it could be e.g. 3 above-and-to-the-left and 1 below-and-to-the-right
        for (int r = pos.getRow() - 1, c = pos.getColumn() - 1; r >= 0 && c >= 0; --r, --c) {
            BoardPosition temp = new BoardPosition(r, c);

            if (isPlayerAtPos(temp, p)) {
                numInARowLeftDiag++;
            } else {
                break;
            }

            if (numInARowLeftDiag == getNumToWin()) {
                return true;
            }
        }

        for (int r = pos.getRow() + 1, c = pos.getColumn() + 1; r < getNumRows() && c < getNumColumns(); ++r, ++c) {
            BoardPosition temp = new BoardPosition(r, c);

            if (isPlayerAtPos(temp, p)) {
                numInARowLeftDiag++;
            } else {
                break;
            }

            if (numInARowLeftDiag == getNumToWin()) {
                return true;
            }
        }

        // same idea as above, except now we use `numInARowRightDiag`
        for (int r = pos.getRow() + 1, c = pos.getColumn() - 1; r < getNumRows() && c >= 0; ++r, --c) {
            BoardPosition temp = new BoardPosition(r, c);

            if (isPlayerAtPos(temp, p)) {
                numInARowRightDiag++;
            } else {
                break;
            }

            if (numInARowRightDiag == getNumToWin()) {
                return true;
            }
        }

        for (int r = pos.getRow() - 1, c = pos.getColumn() + 1; r >= 0 && c < getNumColumns(); --r, ++c) {
            BoardPosition temp = new BoardPosition(r, c);

            if (isPlayerAtPos(temp, p)) {
                numInARowRightDiag++;
            } else {
                break;
            }

            if (numInARowRightDiag == getNumToWin()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks to see if the column is free to take another
     * token or if the column is full.
     *
     * @param c the column that this function is checking
     * @return true if the column has room for another token
     * and false if the column is full
     *
     * @pre 0 <= c < NUM_COLUMNS
     *
     * @post checkIfFree = [Returns true if and only if the top-most row of c has an empty space,
     *                      and returns false otherwise]
     * @post self = #self
     */
    public default boolean checkIfFree(int c) {
        BoardPosition top = new BoardPosition(0, c);

        return whatsAtPos(top) == ' ';
    }


    /**
     * Checks to see if the token at board position `pos` is
     * a token by `player`.
     *
     * @param pos    the BoardPosition being checked
     * @param player the token that is being compared
     *               to the token at BoardPosition pos
     *
     * @return true if the player = the token at pos
     * and false if player ≠ the token at pos
     *
     * @pre [player is a valid player character]
     *
     * @post isPlayerAtPos = [Returns true if and only if the token at `pos` in the game board is from `player`.
     *                        Otherwise, returns false]
     * @post self = #self
     */
    public default boolean isPlayerAtPos(BoardPosition pos, char player) {
        return whatsAtPos(pos) == player;
    }


    /**
     * Checks to see if there is a tie by checking if the
     * board is completely full of tokens.
     *
     * @return true if the top row is completely full and
     * false if the top row is not full.
     *
     * @pre None
     *
     * @post checkTie = [Returns true if the board is completely full, that is, if no columns can
     *                   accept a token (i.e. top row is completely full).]
     * @post self = #self
     */
    public default boolean checkTie() {
        for (int c = 0; c < getNumColumns(); ++c) {
            if (checkIfFree(c)) {
                return false;
            }
        }

        return true;
    }
}