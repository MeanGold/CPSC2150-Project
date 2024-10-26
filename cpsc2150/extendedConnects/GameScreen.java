package cpsc2150.extendedConnects;

import cpsc2150.extendedConnectX.models.GameBoard;
import cpsc2150.extendedConnectX.models.GameBoardMem;
import cpsc2150.extendedConnectX.models.IGameBoard; // Do we need to import GameBoard and GameBoardMem if we have this?

import java.util.Scanner;

/*
 * GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
 *   Benjamin McDonnough - bmcdonnough
 *   Jonathan Maenche    - MeanGold
 *   Christian Herrman   - echerrman
 *   Evan Cox            - evanacox
 */

public class GameScreen {
    /**
     * Driver class for the ConnectX game played in the terminal
     */

    // Constants to use for input validation...
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 10;
    public static final int MIN_ROWS = 3;
    public static final int MAX_ROWS = 100;
    public static final int MIN_COLUMNS = 3;
    public static final int MAX_COLUMNS = 100;
    public static final int MIN_TO_WIN = 3;
    public static final int MAX_TO_WIN = 25;

    public static void main(String[] args) {
        GameScreen game = new GameScreen();
        game.playMultipleGames();
    }

    /**
     * Function that controls the overall game program, allowing the players
     * to play multiple games in a row and ending the program when the player
     * says to not continue.
     *
     * @pre None
     * @post [the games that the user played while the method was executing
     * will have already been printed to the screen]
     */
    private void playMultipleGames() {
        Scanner input = new Scanner(System.in);
        String answer = " "; // empty, will go into every loop
        int numPlayers, numColumns, numRows, numToWin;

        while (!answer.equals("n")) {
            answer = " ";

            // Get and validate the input for number of players
            System.out.println("How many players?");
            numPlayers = input.nextInt();
            input.nextLine();

            while (!(numPlayers >= MIN_PLAYERS && numPlayers <= MAX_PLAYERS)) {
                if (numPlayers < MIN_PLAYERS) {
                    System.out.println("Must be at least 2 players");
                }
                if (numPlayers > MAX_PLAYERS) {
                    System.out.println("Must be 10 players or fewer");
                }
                System.out.println("How many players?");
                numPlayers = input.nextInt();
                input.nextLine();
            }

            // Get and validate the chosen character token chosen by each player
            char[] tokens = new char[numPlayers];
            char tokenInput;
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Enter the character to represent player " + (i + 1));
                tokenInput = Character.toUpperCase(input.nextLine().charAt(0));

                boolean validToken = false;
                while (!validToken) {
                    validToken = true;
                    for (int j = 0; j < i; j++) {
                        if (tokenInput == tokens[j]) {
                            validToken = false;
                        }
                    }

                    if (!validToken) {
                        System.out.println(tokenInput + " is already taken as a player token!");
                        System.out.println("Enter the character to represent player " + (i + 1));
                        tokenInput = Character.toUpperCase(input.nextLine().charAt(0));
                    }
                }

                tokens[i] = tokenInput;
            }

            // Get and validate the input for number of rows
            System.out.println("How many rows should be on the board?");
            numRows = input.nextInt();
            input.nextLine();

            if (numRows < MIN_ROWS || numRows > MAX_ROWS) {
                while (!(numRows >= MIN_ROWS && numRows <= MAX_ROWS)) {
                    System.out.println("Please enter a valid number of rows.");
                    System.out.println("How many rows should be on the board?");
                    numRows = input.nextInt();
                    input.nextLine();
                }
            }

            // Get and validate the input for number of columns
            System.out.println("How many columns should be on the board?");
            numColumns = input.nextInt();
            input.nextLine();

            if (numColumns < MIN_COLUMNS || numColumns > MAX_COLUMNS) {
                while (!(numColumns >= MIN_COLUMNS && numColumns <= MAX_COLUMNS)) {
                    System.out.println("Please enter a valid number of columns.");
                    System.out.println("How many columns should be on the board?");
                    numColumns = input.nextInt();
                    input.nextLine();
                }
            }

            // Get and validate the input for the number in a row needed to win
            System.out.println("How many in a row to win?");
            numToWin = input.nextInt();
            input.nextLine();

            if (numToWin < MIN_TO_WIN || numToWin > MAX_TO_WIN || numToWin > numRows || numToWin > numColumns) {
                while (!(numToWin >= MIN_TO_WIN && numToWin <= MAX_TO_WIN && numToWin <= numRows && numToWin <= numColumns)) {
                    System.out.println("Please enter a valid number of tokens needed in a row to win.");
                    System.out.println("How many in a row to win?");
                    numToWin = input.nextInt();
                    input.nextLine();
                }
            }

            // Choose between fast and memory efficient game
            while (!(answer.equals("F") || answer.equals("f") || answer.equals("M") || answer.equals("m"))) {
                System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
                answer = input.nextLine();

                if (answer.equals("F") || answer.equals("f")) {
                    IGameBoard board = new GameBoard(numRows, numColumns, numToWin);
                    playSingleGame(tokens, numPlayers, board);
                } else if (answer.equals("M") || answer.equals("m")) {
                    IGameBoard board = new GameBoardMem(numRows, numColumns, numToWin);
                    playSingleGame(tokens, numPlayers, board);
                } else {
                    System.out.println("Please enter F or M");
                }
            }

            // Ask to play another game after previous game finishes
            while (!answer.equals("y") && !answer.equals("n")) {
                System.out.println("Would you like to play again? Y/N");

                answer = input.nextLine().toLowerCase();
            }
        }
    }

    /**
     * Plays a single game of ConnectX with the chosen number players,
     * represented on the board by each player's chosen character
     *
     * @param aBoard the current game board that will be updated as the game is played
     * @pre [aBoard is an empty GameBoard]
     * @post [aBoard contains markers placed from the entire single game plated]
     */
    private void playSingleGame(char[] players, int numPlayers, IGameBoard aBoard) {
        int playerIndex = 0;
        char currentPlayer = players[playerIndex];

        // we can't print the board before the condition is executed if we put 
        // `!playRoundWithPlayer(aBoard, currentPlayer)` as the `while` condition
        while (true) {
            System.out.println(aBoard);

            // even when this returns true we still need the board
            // to get printed out
            if (playRoundWithPlayer(aBoard, currentPlayer)) {
                break;
            }

            currentPlayer = players[(++playerIndex) % numPlayers];
        }

        if (aBoard.checkTie()) {
            System.out.println(aBoard);
            System.out.println("It's a Tie!");
        } else {
            System.out.println(aBoard);
            System.out.println("Player " + currentPlayer + " Won!");
        }
    }

    /**
     * Plays a turn with either player X or O
     *
     * @param aBoard the current game board
     * @param p      the player to take the next turn
     * @return [true IF the game has ended, false OW]
     * @pre p == 'X' OR p == 'O'
     * @post aBoard = [#aBoard with one extra marker placed on the board]
     */
    private boolean playRoundWithPlayer(IGameBoard aBoard, char p) {
        int col = askPlayerForColumn(aBoard, p);

        aBoard.dropToken(p, col);

        // if last token caused a win OR the board is full, game is over
        return aBoard.checkForWin(col) || aBoard.checkTie();
    }

    /**
     * Asks either X or O player for which column they would like to place
     * their marker in depending on who's turn it is.
     *
     * @param aBoard the current game board
     * @param p      the player whose turn it is
     * @return int representing the column chosen by the player
     * @pre p == 'X' OR p == 'O'
     * @post askPlayerForColumn >= 0 AND askPlayerForColumn < GameBoard.NUM_COLUMNS
     */
    private int askPlayerForColumn(IGameBoard aBoard, char p) {
        Scanner input = new Scanner(System.in);
        System.out.println("Player " + p + ", what column do you want to place your marker in?");

        if (!input.hasNextInt()) {
            System.out.println("Column number must be an integer");

            return askPlayerForColumn(aBoard, p);
        }

        int col = input.nextInt();
        input.nextLine(); // ignore the newline

        if (col < 0) {
            System.out.println("Column cannot be less than 0");

            return askPlayerForColumn(aBoard, p);
        }

        if (col > aBoard.getNumColumns() - 1) {
            System.out.println("Column cannot be greater than " + (aBoard.getNumColumns() - 1));

            return askPlayerForColumn(aBoard, p);
        }

        if (!aBoard.checkIfFree(col)) {
            System.out.println("Column is full");

            return askPlayerForColumn(aBoard, p);
        }

        return col;
    }
}
