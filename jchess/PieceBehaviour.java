package jchess;

import java.util.ArrayList;

public interface PieceBehaviour {
	
	abstract public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player);
	
    /** 
     * @param x y position on chessboard
     * @param y  y position on chessboard
     * @return true if can move, false otherwise
     * */
    static boolean checkPiece(int x, int y, Chessboard chessboard, Player player)
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

    /** Method check if piece has other owner than calling piece
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return true if owner(player) is different
     * */
    static boolean otherOwner(int x, int y, Chessboard chessboard, Player player)
    {
        Square sq = chessboard.squares[x][y];
        if (sq.piece == null)
        {
            return false;
        }
        if (player != sq.piece.player)
        {
            return true;
        }
        return false;
    }

    /** Method is useful for out of bounds protection
     * @param x  x position on chessboard
     * @param y y position on chessboard
     * @return true if parameters are out of bounds (array)
     * */
    static boolean isout(int x, int y)
    {
        if (x < 0 || x > 7 || y < 0 || y > 7)
        {
            return true;
        }
        return false;
    }
}
