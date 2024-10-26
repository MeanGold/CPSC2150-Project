package cpsc2150.extendedConnectX.models;

/*
 * GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
 *   Benjamin McDonnough - bmcdonnough
 *   Jonathan Maenche    - MeanGold
 *   Christian Herrman   - echerrman
 *   Evan Cox            - evanacox
 */

import java.util.*;

public class GameBoardMem extends AbsGameBoard {
    /**
     * Class for a memory-efficient GameBoard
     *
     * Correspondence self = efficientBoard
     * Correspondence NUM_ROWS = numRows
     * Correspondence NUM_COLUMNS = numColumns
     * Correspondence NUM_TO_WIN = numToWin
     *
     * @invariant [Every column in `efficientBoard` will only have ' ' values above
     *             any non-' ' tokens, therefore there will be no gaps or
     *             "floating" tokens]
     */
    private int numRows, numColumns, numToWin;
    private Map<Character, List<BoardPosition>> efficientBoard = new HashMap<>();
    private int[] numberOfTokensInColumn;

    /**
     * Constructor method for the GameBoardMem class.
     * Is used to initialize the variables needed for the GameBoardMem
     *
     * @param row The number of rows for the board
     *
     * @pre None
     *
     * @post NUM_ROWS = row
     * @post NUM_COLUMNS = column
     * @post NUM_TO_WIN = numWin
     * @post [The hashmap of the board is initialized but no values are assigned.]
     *
     * @param row number of rows for the board
     * @param column number of columns for the board
     * @param numWin number of tokens placed in succession to win the game
     */
    public GameBoardMem(int row, int column, int numWin) {
        numRows = row;
        numColumns = column;
        numToWin = numWin;
        // Array to store the number of tokens in each column
        numberOfTokensInColumn = new int[numColumns];

        // Fill in the array that stores the number of tokens in a column with 0's
        for (int j = 0; j < numColumns; j++) {
            numberOfTokensInColumn[j] = 0;
        }
    }

    @Override
    public int getNumRows() {
        return numRows;
    }

    @Override
    public int getNumColumns() {
        return numColumns;
    }

    @Override
    public int getNumToWin() {
        return numToWin;
    }

    @Override
    public boolean checkIfFree(int c) {
        return numberOfTokensInColumn[c] != numRows;
    }


    @Override
    public void dropToken(char p, int c) {
        BoardPosition temp = new BoardPosition(numberOfTokensInColumn[c], c);
        // Get the list from key value 'p'
        List<BoardPosition> list = efficientBoard.get(p);

        // If the key does not exist in the hash map yet, insert a new blank list
        // and update our reference to that new list
        if (list == null) {
            list = new ArrayList<>();
            efficientBoard.put(p, list);
        }
        // OW, add the position to that list
        list.add(temp);
        // increment the number of tokens in that column
        numberOfTokensInColumn[c]++;
    }


    @Override
    public boolean checkForWin(int c) {
        // Variable for last token dropped during game play
        BoardPosition lastToken = new BoardPosition(numberOfTokensInColumn[c] - 1, c);

        // Check for win in all direction and return true if a win occurs in any direction
        // Return false if no win is detected in any direction
        return checkHorizWin(lastToken, whatsAtPos(lastToken)) ||
                checkVertWin(lastToken, whatsAtPos(lastToken)) ||
                checkDiagWin(lastToken, whatsAtPos(lastToken));
    }

    @Override
    public char whatsAtPos(BoardPosition pos) {
        // Loop through all keys in the HashMap
        for (Map.Entry<Character, List<BoardPosition>> entry : efficientBoard.entrySet()) {
            List<BoardPosition> searchList = entry.getValue();

            // Loop through the list of indexes at the key value
            for (BoardPosition testPos : searchList) {
                // If the index equals the BoardPosition parameter,
                // THEN set the return char to be the key value
                if (testPos.equals(pos)) {
                    return entry.getKey();
                }
            }
        }

        return ' ';
    }

    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        List<BoardPosition> searchList = efficientBoard.get(player);

        // if the player hasn't placed any tokens this will be null just like
        // if it's an invalid player, so we treat them the same way.
        //
        // the preconditions say the player must be valid, therefore
        // we can assume (searchList == null) => a player who hasn't
        // played yet was just passed in
        if (searchList != null) {
            // Loop through the list of indexes at the key value
            for (BoardPosition testPos : searchList) {
                // If the index equals the BoardPosition parameter,
                // THEN set the return bool equal to true and end the loop
                if (testPos.equals(pos)) {
                    return true;
                }
            }
        }

        return false;
    }
}
