package cpsc2150.extendedConnectX.tests;

/*
 * GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
 *   Benjamin McDonnough - bmcdonnough
 *   Jonathan Maenche    - MeanGold
 *   Christian Herrman   - echerrman
 *   Evan Cox            - evanacox
 */

import cpsc2150.extendedConnectX.models.IGameBoard;
import cpsc2150.extendedConnectX.models.GameBoard;
import cpsc2150.extendedConnectX.models.BoardPosition;

import cpsc2150.extendedConnects.GameScreen;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestGameBoard {
    // GameBoard Factory constructor
    private IGameBoard IGBFactory(int r, int c, int nToWin) {
        return new GameBoard(r, c, nToWin);
    }

    /**
     * Produces a string representation of a 2D array that is formatted the same as the AbsGameBoard toString();
     * This is used to check that the state of our IGameBoard object hasn't changed
     *
     * @return a string representation of the 2D-array as a ConnectX gameboard
     * @pre |array2d| > 0
     * @post self = #self
     */
    private String expected2DGameBoardArray(char[][] array2d) {
        int numRows = array2d.length;
        int numCols = array2d[0].length;

        String output = "";

        for (int i = 0; i < numCols; i++) {
            if (i < 10)
                output += "| " + i;
            else
                output += "|" + i;
        }
        output += "|\n";

        for (int i = numRows - 1; i >= 0; i--) {
            for (int j = 0; j < numCols; j++) {
                // Check to see if array location is empty
                if (array2d[i][j] == 0) //Print out a blank board location
                    output += "|  ";
                else //OW print out char from array
                    output += "|" + array2d[i][j] + " ";
            }
            output += "|\n";
        }
        return output;
    }

    // Testing the constructor when row number, column number, and number to win are all set to the same value
    @Test
    public void testConstructor_equal_row_column_numWin() {
        IGameBoard realBoard = IGBFactory(4, 4, 4);
        char[][] testBoard = new char[4][4];

        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Testing the constructor when row number is larger than column number,
    // and number to win is set to the same value as the column number
    @Test
    public void testConstructor_larger_row_smaller_column() {
        IGameBoard realBoard = IGBFactory(5, 3, 3);
        char[][] testBoard = new char[5][3];

        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Testing the constructor when column number is larger than row number,
    // and number to win is set smaller than both the row and column number values
    @Test
    public void testConstructor_larger_column_smaller_row_distinct_numWin() {
        IGameBoard realBoard = IGBFactory(4, 5, 3);
        char[][] testBoard = new char[4][5];

        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkIfFree() returns true when a column is empty
    @Test
    public void testCheckIfFree_column_empty_true() {
        IGameBoard realBoard = IGBFactory(3, 4, 3);
        char[][] testBoard = new char[3][4];

        // Place tokens in columns 0 and 2
        realBoard.dropToken('X', 0);
        testBoard[0][0] = 'X';
        realBoard.dropToken('O', 2);
        testBoard[0][2] = 'O';

        // Checking to see if column 1 is free
        assertTrue(realBoard.checkIfFree(1));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkIfFree() returns true when a column has 1 token
    @Test
    public void testCheckIfFree_column_has_1_token_true() {
        IGameBoard realBoard = IGBFactory(3, 4, 3);
        char[][] testBoard = new char[3][4];

        // Place tokens in columns 1 and 3
        realBoard.dropToken('X', 1);
        testBoard[0][1] = 'X';
        realBoard.dropToken('O', 3);
        testBoard[0][3] = 'O';

        // Checking to see if column 1 is free
        assertTrue(realBoard.checkIfFree(1));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkIfFree() returns false when a column is full
    @Test
    public void testCheckIfFree_column_full_false() {
        IGameBoard realBoard = IGBFactory(3, 4, 3);
        char[][] testBoard = new char[3][4];

        // Place 3 tokens in column 2 to fill it up
        realBoard.dropToken('X', 2);
        testBoard[0][2] = 'X';
        realBoard.dropToken('O', 2);
        testBoard[1][2] = 'O';
        realBoard.dropToken('X', 2);
        testBoard[2][2] = 'X';

        // Checking to see if column 1 is free
        assertFalse(realBoard.checkIfFree(2));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkHorizWin() returns true when X has won horizontally
    // with last token being placed at the end of the string of 3
    @Test
    public void testCheckHorizWin_last_token_end_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'X', 'X', ' '},
                {'O', 'O', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until X has won horizontally
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 0);
        realBoard.dropToken('X', 1);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 2);

        // Check for horizontal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(0, 2);
        assertTrue(realBoard.checkHorizWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkHorizWin() returns true when X has won horizontally
    // with last token being placed in middle of a string of 3
    @Test
    public void testCheckHorizWin_last_token_middle_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'X', 'X', ' '},
                {'O', ' ', 'O', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until X has won horizontally
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 0);
        realBoard.dropToken('X', 2);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 1);

        // Check for horizontal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(0, 1);
        assertTrue(realBoard.checkHorizWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkHorizWin() returns false when X and O have placed tokens,
    // but neither have won horizontally
    @Test
    public void testCheckHorizWin_no_horizontal_win_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', 'O', 'X'},
                {'X', 'O', ' ', ' '},
                {'X', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens for both X and O without winning horizontally
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 0);

        // Check for horizontal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(2, 0);
        assertFalse(realBoard.checkHorizWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkHorizWin() returns false when O was last token placed
    // in a spot to block X from winning with string of the num needed to win
    @Test
    public void testCheckHorizWin_block_win_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', 'X', ' '},
                {'O', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until X is about to win, but then O blocks their winning sport with their token
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 0);
        realBoard.dropToken('X', 2);
        realBoard.dropToken('O', 1);

        // Check for horizontal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(0, 1);
        assertFalse(realBoard.checkHorizWin(lastPlaced, 'O'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Checks checkVertWin to make sure that it returns true when there is a vert win
    @Test
    public void testCheckVertWin_min_to_win_X_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', ' ', ' '},
                {'X', 'O', ' ', ' '},
                {'X', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until O gets a vert win
        for (int i = 0; i < GameScreen.MIN_TO_WIN; ++i) {
            realBoard.dropToken('X', 0);

            if (i < 2) {
                realBoard.dropToken('O', 1);
            }
        }

        // Check for vert win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(2, 0);
        assertTrue(realBoard.checkVertWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Checks checkVertWin to make sure that it returns false when there is not a streak of the same token for the win
    @Test
    public void testCheckVertWin_blocked_win_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', ' ', ' '},
                {'O', ' ', ' ', ' '},
                {'X', ' ', ' ', ' '},
                {'X', ' ', ' ', ' '}
        };

        // Drop tokens to set up a blocked vert win
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 0);
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 0);

        // Check for vert win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(3, 0);
        assertFalse(realBoard.checkVertWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Checks checkVertWin to make sure that it returns false for a horizontal win
    @Test
    public void testCheckVertWin_no_vert_win_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'X', 'X', ' '},
                {'O', 'O', 'O', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until MIN_TO_WIN number of tokens is dropped horizontally
        for (int i = 0; i < GameScreen.MIN_TO_WIN; ++i) {
            realBoard.dropToken('X', i);
            realBoard.dropToken('O', i);
        }

        // Check for vertical win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(0, 3);
        assertFalse(realBoard.checkVertWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Checks checkVertWin to make sure that it returns false when the last token placed did not result in a win
    @Test
    public void testCheckVertWin_check_last_token_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', ' ', ' '},
                {'X', 'O', ' ', ' '},
                {'X', 'O', ' ', ' '},
                {' ', 'X', ' ', ' '}
        };

        // Drop tokens until both characters have "won" and drop another token in a non-winning position
        for (int i = 0; i < GameScreen.MIN_TO_WIN; ++i) {
            realBoard.dropToken('X', 0);
            realBoard.dropToken('O', 1);
        }

        realBoard.dropToken('X', 1);

        // Check for vertical win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(1, 3);
        assertFalse(realBoard.checkVertWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkDiagWin() returns true when X has the number to win
    // in a row going up-and-to-the-right
    @Test
    public void testCheckDiagWin_check_last_token_up_right_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', 'O', 'O'},
                {' ', 'X', 'X', ' '},
                {' ', ' ', 'X', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens diagonally in a pattern to where X will win up and to the right
        for (int i = 0; i <= GameScreen.MIN_TO_WIN; ++i) {
            if (i != 3) {
                realBoard.dropToken('X', i);
                realBoard.dropToken('O', i + 1);
            } else {
                realBoard.dropToken('X', i - 1);
            }
        }

        // Check for diagonal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(2, 2);
        assertTrue(realBoard.checkDiagWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkDiagWin() returns true when X has the number to win
    // in a row going up-and-to-the-left
    @Test
    public void testCheckDiagWin_check_last_token_up_left_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'O', 'O', 'O', 'X'},
                {' ', 'X', 'X', ' '},
                {' ', 'X', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens diagonally in a pattern to where X will win up and to the left
        for (int i = GameScreen.MIN_TO_WIN; i >= 0; --i) {
            if (i != 0) {
                realBoard.dropToken('X', i);
                realBoard.dropToken('O', i - 1);
            } else {
                realBoard.dropToken('X', i + 1);
            }
        }

        // Check for diagonal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(2, 1);
        assertTrue(realBoard.checkDiagWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkDiagWin() returns false when X has some tokens in a row
    // in the up-and-to-the-right diagonal, but not enough to win
    @Test
    public void testCheckDiagWin_not_up_and_right_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', 'X', 'O', 'X'},
                {' ', 'X', 'O', ' '},
                {' ', ' ', 'X', ' '},
                {' ', ' ', 'O', ' '}
        };

        // Drop tokens until X has more than one in up-and-right diagonal direction, but not enough to win
        realBoard.dropToken('X', 1);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 2);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 1);

        // Check for diagonal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(1, 1);
        assertFalse(realBoard.checkDiagWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkDiagWin() returns false when X has some tokens in a row
    // in the up-and-to-the-left diagonal, but not enough to win
    @Test
    public void testCheckDiagWin_not_up_and_left_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', 'X', 'O', ' '},
                {' ', 'O', 'X', ' '},
                {' ', 'X', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until X has more than one in up-and-left diagonal direction, but not enough to win
        realBoard.dropToken('X', 1);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 2);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 1);

        // Check for diagonal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(2, 1);
        assertFalse(realBoard.checkDiagWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkDiagWin() returns true when X has the number to win in a row
    // going up-and-to-the-right, with the most recent token being in the middle of the string of tokens
    @Test
    public void testCheckDiagWin_last_token_middle_up_and_right_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', 'O', 'X'},
                {' ', 'X', 'O', ' '},
                {' ', ' ', 'X', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until X has a diagonal win in up-and-right direction with last token in the middle
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 2);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 1);

        // Check for diagonal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(1, 1);
        assertTrue(realBoard.checkDiagWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkDiagWin() returns true when X has the number to win in a row
    // going up-and-to-the-left, with the most recent token being in the middle of the string of tokens
    @Test
    public void testCheckDiagWin_last_token_middle_up_and_left_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', 'O', 'X'},
                {' ', 'O', 'X', ' '},
                {' ', 'X', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until X has a diagonal win in up-and-left direction with last token in the middle
        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 1);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 2);

        // Check for diagonal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(1, 2);
        assertTrue(realBoard.checkDiagWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests to check that checkDiagWin() returns false when X has a string of tokens up-and-to-the-left and
    // up-and-to-the-right that totals the num needed to win, but not in a single diagonal
    @Test
    public void testCheckDiagWin_different_diagonals_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'O', 'X', 'O', 'X'},
                {'X', ' ', 'X', 'O'},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // Drop tokens until X has tokens in both diagonals, but not enough to win in either diagonal alone
        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 0);
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 2);
        realBoard.dropToken('X', 2);
        realBoard.dropToken('O', 3);
        realBoard.dropToken('X', 1);

        // Check for diagonal win after most recent token place
        BoardPosition lastPlaced = new BoardPosition(0, 1);
        assertFalse(realBoard.checkDiagWin(lastPlaced, 'X'));

        // Checking that the state of the board hasn't changed
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests if checkTie correctly counts a full game board as a tie
    @Test
    public void testCheckTie_full_board_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'X', 'O', 'O'},
                {'O', 'O', 'X', 'X'},
                {'X', 'X', 'O', 'O'},
                {'O', 'O', 'X', 'X'}
        };

        for (int i = 0; i < 4; ++i) {
            String pattern = (i < 2) ? "XOXO" : "OXOX";

            for (char c : pattern.toCharArray()) {
                realBoard.dropToken(c, i);
            }
        }

        assertTrue(realBoard.checkTie());

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests if checkTie correctly counts a half-full board as not being tied
    @Test
    public void testCheckTie_half_full_board_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'X', 'O', 'O'},
                {'O', 'O', 'X', 'X'},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        for (int i = 0; i < 4; ++i) {
            String pattern = (i < 2) ? "XO" : "OX";

            for (char c : pattern.toCharArray()) {
                realBoard.dropToken(c, i);
            }
        }

        assertFalse(realBoard.checkTie());

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests if checkTie correctly counts a board with a single full column as not being tied
    @Test
    public void testCheckTie_single_full_column_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', ' ', ' ', ' '},
                {'O', ' ', ' ', ' '},
                {'X', ' ', ' ', ' '},
                {'O', ' ', ' ', ' '}
        };

        for (char c : "XOXO".toCharArray()) {
            realBoard.dropToken(c, 0);
        }

        assertFalse(realBoard.checkTie());

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests if checkTie correctly counts a board with all but one column being full as not being tied
    @Test
    public void testCheckTie_all_columns_but_one_full_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'X', 'O', 'O'},
                {'O', 'O', 'X', 'X'},
                {'X', 'X', 'O', 'O'},
                {'O', 'O', 'X', ' '}
        };

        // fill first three columns
        for (int i = 0; i < 3; ++i) {
            String pattern = (i < 2) ? "XOXO" : "OXOX";

            for (char c : pattern.toCharArray()) {
                realBoard.dropToken(c, i);
            }
        }

        // last column leave the last space empty
        for (char c : "OXO".toCharArray()) {
            realBoard.dropToken(c, 3);
        }


        assertFalse(realBoard.checkTie());

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that whatsAtPos works on the bottom left corner of a board
    @Test
    public void testWhatsAtPos_bottom_left_corner() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };
        realBoard.dropToken('X', 0);

        assertEquals('X', realBoard.whatsAtPos(new BoardPosition(0, 0)));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }


    // Tests that whatsAtPos works on the bottom right corner of a board
    @Test
    public void testWhatsAtPos_bottom_right_corner() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };
        realBoard.dropToken('X', 3);

        assertEquals('X', realBoard.whatsAtPos(new BoardPosition(0, 3)));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that whatsAtPos works for the top left corner of a board
    @Test
    public void testWhatsAtPos_top_left_corner() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', ' ', ' ', ' '},
                {'O', ' ', ' ', ' '},
                {'X', ' ', ' ', ' '},
                {'O', ' ', ' ', ' '}
        };
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 0);
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 0);

        // Check to make sure that the cells below the corner cell are filled with a token
        for (int i = 0; i < 3; i++) {
            // Checks to make sure the cell is NOT empty
            assertNotEquals(testBoard[i][0], ' ');
        }
        assertEquals('O', realBoard.whatsAtPos(new BoardPosition(3, 0)));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that whatsAtPos works for the top right corner of a board
    @Test
    public void testWhatsAtPos_top_right_corner() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', 'O'},
                {' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', 'O'}
        };

        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 3);
        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 3);

        // Check to make sure that the cells below the corner cell are filled with a token
        for (int i = 0; i < 3; i++) {
            // Checks to make sure the cell is NOT empty
            assertNotEquals(testBoard[i][3], ' ');
        }
        assertEquals('O', realBoard.whatsAtPos(new BoardPosition(3, 3)));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that whatsAtPos works on a board position that is NOT on the edge of the board (i.e. inside the board)
    @Test
    public void testWhatsAtPos_position_on_inside_of_board() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', ' ', 'X', ' '},
                {' ', ' ', 'O', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        realBoard.dropToken('X', 2);
        realBoard.dropToken('O', 2);

        // Check to make sure the edge cell below is NOT empty
        assertNotEquals(testBoard[0][2], ' ');

        assertEquals('O', realBoard.whatsAtPos(new BoardPosition(1, 2)));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that isPlayerAtPos works on the bottom left corner of a board
    @Test
    public void testIsPlayerAtPos_bottom_left_corner_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };
        realBoard.dropToken('X', 0);

        assertTrue(realBoard.isPlayerAtPos(new BoardPosition(0, 0), 'X'));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that isPlayerAtPos works on the bottom right corner of a board
    @Test
    public void testIsPlayerAtPos_bottom_right_corner_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', ' ', ' ', 'O'},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };
        realBoard.dropToken('O', 3);

        assertFalse(realBoard.isPlayerAtPos(new BoardPosition(0, 3), 'X'));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that isPlayerAtPos works on the top left corner of a board
    @Test
    public void testIsPlayerAtPos_top_left_corner_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', ' ', ' ', ' '},
                {'O', ' ', ' ', ' '},
                {'X', ' ', ' ', ' '},
                {'O', ' ', ' ', ' '}
        };
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 0);
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 0);

        assertFalse(realBoard.isPlayerAtPos(new BoardPosition(3, 0), 'X'));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that isPlayerAtPos works on the top right corner of a board
    @Test
    public void testIsPlayerAtPos_top_right_corner_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', 'O'},
                {' ', ' ', ' ', 'X'},
                {' ', ' ', ' ', 'O'}
        };
        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 3);
        realBoard.dropToken('X', 3);
        realBoard.dropToken('O', 3);

        assertTrue(realBoard.isPlayerAtPos(new BoardPosition(3, 3), 'O'));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that isPlayerAtPos works a cell on the inside of a board; checks if it returns true properly
    @Test
    public void testIsPlayerAtPos_position_on_inside_of_board_true() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', ' ', 'X', ' '},
                {' ', ' ', 'O', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };
        realBoard.dropToken('X', 2);
        realBoard.dropToken('O', 2);

        assertTrue(realBoard.isPlayerAtPos(new BoardPosition(1, 2), 'O'));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // Tests that isPlayerAtPos works a cell on the inside of a board; checks if it returns false properly
    @Test
    public void testIsPlayerAtPos_position_on_inside_of_board_false() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {' ', 'X', ' ', ' '},
                {' ', 'O', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };
        realBoard.dropToken('X', 1);
        realBoard.dropToken('O', 1);

        assertFalse(realBoard.isPlayerAtPos(new BoardPosition(1, 1), 'X'));

        // Check to make sure that the board does not change
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // test that dropToken works on a completely empty board
    @Test
    public void testDropToken_empty_board() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        realBoard.dropToken('X', 0);

        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // test that dropToken works when dropping onto another token
    // from the same player
    @Test
    public void testDropToken_drop_onto_another_token_same_player() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', 'X', ' '},
                {' ', ' ', 'X', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // setup, ensure we got there correctly, board has to be at right
        // state prior to drop too
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 2);
        realBoard.dropToken('X', 2);
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());


        // actual position we're testing
        testBoard[1][0] = 'X';
        realBoard.dropToken('X', 0);
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }


    // test that dropToken works when dropping onto another token
    // from a different player
    @Test
    public void testDropToken_drop_onto_another_token_diff_player() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'O', 'X', ' '},
                {' ', ' ', 'X', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '}
        };

        // setup, ensure we got there correctly, board has to be at right
        // state prior to drop too
        realBoard.dropToken('X', 0);
        realBoard.dropToken('O', 1);
        realBoard.dropToken('X', 2);
        realBoard.dropToken('X', 2);
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());

        // actual position we're testing
        testBoard[1][1] = 'X';
        realBoard.dropToken('X', 1);
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // test that dropToken works when filling up a column
    @Test
    public void testDropToken_fill_column() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', ' ', ' ', ' '},
                {'X', ' ', ' ', ' '},
                {'O', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
        };

        // setup, ensure we got there correctly, board has to be at right
        // state prior to drop too
        for (char c : "XXO".toCharArray()) {
            realBoard.dropToken(c, 0);
        }

        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());

        // actual position we're testing
        testBoard[3][0] = 'O';
        realBoard.dropToken('O', 0);
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }

    // test that dropToken works when filling up the board
    @Test
    public void testDropToken_fill_board() {
        IGameBoard realBoard = IGBFactory(4, 4, GameScreen.MIN_TO_WIN);
        char[][] testBoard = new char[][]{
                {'X', 'X', 'O', 'O'},
                {'O', 'O', 'X', 'X'},
                {'X', 'X', 'O', 'O'},
                {'O', 'O', 'X', ' '}
        };

        // setup, ensure we got there correctly, board has to be at right
        // state prior to drop too
        for (int i = 0; i < 3; ++i) {
            String pattern = (i < 2) ? "XOXO" : "OXOX";

            for (char c : pattern.toCharArray()) {
                realBoard.dropToken(c, i);
            }
        }

        for (char c : "OXO".toCharArray()) {
            realBoard.dropToken(c, 3);
        }

        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());

        // actual position we're testing
        testBoard[3][3] = 'X';
        realBoard.dropToken('X', 3);
        assertEquals(expected2DGameBoardArray(testBoard), realBoard.toString());
    }
}