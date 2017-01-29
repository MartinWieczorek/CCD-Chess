package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/**
 * interface of movement behaviors of pieces
 */
public interface PieceBehaviour {
	
	/**
	 * Function for computing all possible position a given piece can reach on a given cheesboard with this behavior.
     * @param player owning player of calling piece
     * @param chessboard chessboard of the Game
	 * @param square square of calling piece
	 * @return returns all possible movements of given piece for this behavior
	 */
	abstract public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player);
	
	/**
	 * Function for computing all possible position a given piece can reach on a given cheesboard with this behavior ignoring king protection.
     * @param player owning player of calling piece
     * @param chessboard chessboard of the Game
	 * @param square square of calling piece
	 * @return returns all possible movements of given piece for this behavior
	 */
	abstract public ArrayList<Square> getUnsaveMoves(Chessboard chessboard, Square square, Player player);
	
	/** Moves the piece from the startSquare to the endSquare and computes side effects in some cases
	 * @param chessboard
	 * @param startSquare
	 * @param endSquare
	 * @param player
	 * @return Should either return null or this. Returning "this" will delete this behavior in the calling piece. Returning "null" will do nothing.  
	 */
	abstract public PieceBehaviour moveTo(Chessboard chessboard, Square startSquare, Square endSquare, Player player);
}
