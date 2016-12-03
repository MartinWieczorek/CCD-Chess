package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

public interface PieceBehaviour {
	
	abstract public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player);
	
    /** Method check if piece has other owner than calling piece
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if owner(player) is different
     * */
    static boolean enemyPieceOnPosition(int x, int y, Chessboard chessboard, Player player)
    {
        Square square = chessboard.squares[x][y];
        if (square.piece == null)
        {
            return false;
        }
        if (player != square.piece.player)
        {
            return true;
        }
        return false;
    }
    
    static boolean checkSpaceAtPosition (int x, int y, Player player, Chessboard chessboard)
    {
    	if(chessboard.squares[x][y].piece == null) return true;
    	else return false;
    }
  
    static boolean checkPieceAtPosition(int x, int y, Player player, Chessboard chessboard)
    {
        if (chessboard.squares[x][y].piece != null
                && chessboard.squares[x][y].piece.name.equals("King"))
        {
            return false;
        }
        Piece piece = chessboard.squares[x][y].piece;
        if (piece == null || //if this square is empty
                piece.player != player) //or piece is another player
        {
            return true;
        }
        return false;
    }
    

    /** Method is useful for out of bounds protection
     * @param x x position on chessboard
     * @param y y position on chessboard
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
