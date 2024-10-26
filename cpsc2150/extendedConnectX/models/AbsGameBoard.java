package cpsc2150.extendedConnectX.models;

/*
 * GROUP MEMBER NAMES AND GITHUB USERNAMES SHOULD GO HERE
 *   Benjamin McDonnough - bmcdonnough
 *   Jonathan Maenche    - MeanGold
 *   Christian Herrman   - echerrman
 *   Evan Cox            - evanacox
 */

public abstract class AbsGameBoard implements IGameBoard {
    /**
     * Returns a string representation of the board.
     *
     * @return A formatted representation of the game board
     * @pre None
     * @post toString = [a string representation of the game board's state]
     * @post self = #self
     */
    @Override
    public String toString() {
        String output = "";

        for (int i = 0; i < getNumColumns(); i++) {
            if (i < 10) {
                output += "| " + i;
            } else {
                output += "|" + i;
            }
        }
        output += "|\n";

        for (int i = getNumRows() - 1; i >= 0; i--) {
            for (int j = 0; j < getNumColumns(); j++) {
                BoardPosition current = new BoardPosition(i, j);
                output += "|" + whatsAtPos(current) + " ";
            }

            output += "|\n";
        }


        return output;
    }
}
