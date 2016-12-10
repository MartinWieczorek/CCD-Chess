package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/**
 * interface of movement behaviors of pieces
 * @author Patrick
 *
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
	
    /** Method check if piece on given position has other owner than calling piece
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @param player owning player of calling piece
     * @param chessboard chessboard of the Game
     * @return true if owner(player) is different
     * */
    static boolean enemyPieceOnPosition(int x, int y, Chessboard chessboard, Player player)
    {
        Square square = chessboard.getSquares()[x][y];
        if (square.getPiece() == null)
        {
            return false;
        }
        if (player != square.getPiece().getPlayer())
        {
            return true;
        }
        return false;
    }
    
    /**
     * checks if an given Position is empty
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @param player owning player of calling piece
     * @param chessboard chessboard of the Game
     * @return true if position is empty
     */
    static boolean checkSpaceAtPosition (int x, int y, Player player, Chessboard chessboard)
    {
    	if(chessboard.getSquares()[x][y].getPiece() == null) return true;
    	else return false;
    }
  
    
    /**
     * checks if given position on given chessboard is empty of occupied by an enemy or own piece
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @param player owning player of calling piece
     * @param chessboard chessboard of the Game
     * @return true if given square is empty or other player piece
     */
    static boolean checkPieceAtPosition(int x, int y, Player player, Chessboard chessboard)
    {
        if (chessboard.getSquares()[x][y].getPiece() != null
                && chessboard.getSquares()[x][y].getPiece().getName().equals("King"))
        {
            return false;
        }
        Piece piece = chessboard.getSquares()[x][y].getPiece();
        if (piece == null || //if this square is empty
                piece.getPlayer() != player) //or piece is another player
        {
            return true;
        }
        return false;
    }
    

    /** Method is useful for out of bounds protection
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @param chessboard chessboard of the Game
     * @return true if parameters are out of bounds (array)
     * */
    static boolean isout(int x, int y, Chessboard chessboard)
    {
    	int numSquares = chessboard.getNumSquares();
    	int cornerSquares = chessboard.getCornerSquares();
        if (x < 0 || y < 0 || x > numSquares - 1 || y > numSquares - 1 || //out of outer borders?
        	(x < cornerSquares && ( y > numSquares - 1 - cornerSquares || y < cornerSquares)) || //inside left corners?
        	(x > numSquares - cornerSquares - 1 && ( y > numSquares - 1 - cornerSquares || y < cornerSquares ))) //inside right corners?
        {
            return true;
        }
        return false;
    }
}
