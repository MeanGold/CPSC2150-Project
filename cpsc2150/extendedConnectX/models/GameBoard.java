package cpsc2150.extendedConnectX.models;

/*
 * GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
 *   Benjamin McDonnough - bmcdonnough
 *   Jonathan Maenche    - MeanGold
 *   Christian Herrman   - echerrman
 *   Evan Cox            - evanacox
 */

public class GameBoard extends AbsGameBoard {
    /**
     * Class for a fast (but memory-inefficient) GameBoard
     *
     * Correspondence self = board[NUM_ROWS][NUM_COLUMNS]
     * Correspondence NUM_ROWS = numRows
     * Correspondence NUM_COLUMNS = numColumns
     * Correspondence NUM_TO_WIN = numToWin
     *
     * @invariant [Every column in `efficientBoard` will only have ' ' values above
     *             any non-' ' tokens, therefore there will be no gaps or
     *             "floating" tokens]
     */
    private int numRows, numColumns, numToWin;
    private char[][] board;
    private int[] numberOfTokensInColumn;


    /**
     * Constructor method for the GameBoard class.
     * Is used to initialize the variables needed for the GameBoard
     *
     * @pre None
     *
     * @post [The board is initialized to NUM_ROWS rows and NUM_COLUMNS columns]
     * @post [Every position in the new board is initialized to be empty]
     *
     * @param row number of rows for the board
     * @param column number of columns for the board
     * @param numWin number of tokens placed in succession to win the game
     */
    public GameBoard(int row, int column, int numWin) {
        numRows = row;
        numColumns = column;
        numToWin = numWin;
        board = new char[numRows][numColumns];
        numberOfTokensInColumn = new int[numColumns];

        // Fill in the board with ' ' while having the bottom left be point (0, 0)
        for (int i = numRows - 1; i >= 0; i--) {
            for (int j = 0; j < numColumns; j++) {
                board[i][j] = ' ';
            }
        }

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
        // Goes through each row until it finds the lowest empty row
        // then "drops" a token in that row/column and increases the
        // numberOfTokensInColumn for that column
        for (int i = 0; i < numRows; ++i) {
            BoardPosition temp = new BoardPosition(i, c);

            if (whatsAtPos(temp) == ' ') {
                board[i][c] = p;
                numberOfTokensInColumn[c]++;

                break;
            }
        }
    }


    @Override
    public boolean checkForWin(int c) {
        BoardPosition lastToken = new BoardPosition(numberOfTokensInColumn[c] - 1, c);

        return checkHorizWin(lastToken, whatsAtPos(lastToken)) ||
                checkVertWin(lastToken, whatsAtPos(lastToken)) ||
                checkDiagWin(lastToken, whatsAtPos(lastToken));
    }

    @Override
    public char whatsAtPos(BoardPosition pos) {
        return board[pos.getRow()][pos.getColumn()];
    }
}
