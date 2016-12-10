package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/*
|_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|X|X|X|_|_|_|4
|_|_|X|K|X|_|_|_|3
|_|_|X|X|X|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
*/

/**
 * represents the possible movements a King can perform
 * @author Patrick
 *
 */
public class KingBehaviour implements PieceBehaviour {
	
static private KingBehaviour instance;
	
	private KingBehaviour(){}
	
	/**
	 * Instance getter for singleton
	 * @return instance of singleton
	 */
	static public KingBehaviour getInstance()
	{
		if(instance == null){
			instance = new KingBehaviour();
		}
		
		return instance;
	}

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> result = new ArrayList<Square>();
        for (int x = square.getPozX() - 1; x <= square.getPozX() + 1; x++)
        {
            for (int y = square.getPozY() - 1; y <= square.getPozY() + 1; y++)
            {
                if (!PieceBehaviour.isout(x, y, chessboard)) //out of bounds protection
                {
                    if (square == chessboard.getSquares()[x][y]) continue; //if we're checking square on which is King
                    if (PieceBehaviour.checkPieceAtPosition(x, y, player, chessboard)) //if square is empty
                    {
                        if (isSafe(chessboard.getSquares()[x][y], chessboard, square, player))
                        {
                            result.add(chessboard.getSquares()[x][y]);
                        }
                    }
                }
            }
        }
        switch(player.getColor())
        {
        	case white: result.addAll(castlingMoves(-1, 0, chessboard, square, player));
        	break;
        	case red: result.addAll(castlingMoves(0, 1, chessboard, square, player));
        	break;
        	case black: result.addAll(castlingMoves(1, 0, chessboard, square, player));
        	break;
        	case green: result.addAll(castlingMoves(0, -1, chessboard, square, player));
        	break;
        }
        return result;
	}
	
	private ArrayList<Square> castlingMoves(int offX, int offY, Chessboard chessboard, Square square, Player player) 
	{
		ArrayList<Square> result = new ArrayList<Square>();
		if (!square.getPiece().isWasMotion() && isSafe(square, chessboard, square, player)) //check if king was not moved before
        {
			Piece rook = chessboard.getSquares()[square.getPozX() + offX * 3][square.getPozY() + offY * 3].getPiece();
			Square square1 = chessboard.getSquares()[square.getPozX() + offX][square.getPozY() + offY];
			Square square2 = chessboard.getSquares()[square.getPozX() + offX * 2][square.getPozY() + offY * 2];
			boolean canCastling;
            if (rook != null && rook.getName().equals("Rook"))
            {
                canCastling = true;
                if (!rook.isWasMotion()) //if rook was not moved before
                {
                    for (int x = square.getPozX() + offX, y = square.getPozY() + offY; !PieceBehaviour.isout(x, y, chessboard); x = x + offX, y = y + offY)
                    {
                        if (chessboard.getSquares()[x][y].getPiece() != null && chessboard.getSquares()[x][y].getPiece().getName() != "Rook") //if square is not empty and no rook
                        {
                            canCastling = false;
                            break;
                        }
                    }
                    if (canCastling && isSafe(square1, chessboard, square, player) && isSafe(square2, chessboard, square, player)) //can do castling when square1 and square2 are not checked
                    { 
                    	result.add(square2);
                    }
                }
            }
            rook = chessboard.getSquares()[square.getPozX() - offX * 3][square.getPozY() - offY * 3].getPiece();
			square1 = chessboard.getSquares()[square.getPozX() - offX][square.getPozY() - offY];
			square2 = chessboard.getSquares()[square.getPozX() - offX * 2][square.getPozY() - offY * 2];
			
            if (rook != null && rook.getName().equals("Rook"))
            {
                canCastling = true;
                if (!rook.isWasMotion()) //if rook was not moved
                {
                	for (int x = square.getPozX() - offX, y = square.getPozY() - offY; !PieceBehaviour.isout(x, y, chessboard); x = x - offX, y = y - offY)
                    {
                        if (chessboard.getSquares()[x][y].getPiece() != null  && chessboard.getSquares()[x][y].getPiece().getName() != "Rook") //if square is not empty and no rook
                        {
                            canCastling = false;
                            break;
                        }
                    }
                    if (canCastling && isSafe(square1, chessboard, square, player) && isSafe(square2, chessboard, square, player))  //can do castling when square1 and square2 are not checked
                    {
                    	result.add(square2);
                    }
                }
            }
        }
		return result;
	}
	
	  /** Method to check is the king is checked by an opponent
     * @param s Square for testing
     * @return bool true if king is save, else returns false
     */
	public boolean isSafe(Square s, Chessboard chessboard, Square square, Player player)
	{
		if(!testRook(0, 1, s, chessboard, square, player)) return false;
		if(!testRook(0, -1, s, chessboard, square, player)) return false;
		if(!testRook(1, 0, s, chessboard, square, player)) return false;
		if(!testRook(-1, 0, s, chessboard, square, player)) return false;
		
		if(!testBishop(1, 1, s, chessboard, square, player)) return false;
		if(!testBishop(-1, -1, s, chessboard, square, player)) return false;
		if(!testBishop(-1, 1, s, chessboard, square, player)) return false;
		if(!testBishop(1, -1, s, chessboard, square, player)) return false;
		
		if(!testKnight(-2, 1, s, chessboard, square, player)) return false;
		if(!testKnight(-1, 2, s, chessboard, square, player)) return false;
		if(!testKnight(1, 2, s, chessboard, square, player)) return false;
		if(!testKnight(2, 1, s, chessboard, square, player)) return false;
		if(!testKnight(2, -1, s, chessboard, square, player)) return false;
		if(!testKnight(1, -2, s, chessboard, square, player)) return false;
		if(!testKnight(0, 1, s, chessboard, square, player)) return false;
		if(!testKnight(-1, -2, s, chessboard, square, player)) return false;
		if(!testKnight(-2, -1, s, chessboard, square, player)) return false;
		
		if(!testPawn(1, 1, s, chessboard, square, player)) return false;
		if(!testPawn(-1, -1, s, chessboard, square, player)) return false;
		if(!testPawn(-1, 1, s, chessboard, square, player)) return false;
		if(!testPawn(1, -1, s, chessboard, square, player)) return false;
		
		if(!testKing( s, chessboard, square, player)) return false;
			
		return true;
	}
	
	private boolean testRook(int offX, int offY, Square s, Chessboard chessboard, Square square, Player player)
	{
		for (int x = s.getPozX() + offX, y = s.getPozY() + offY; !PieceBehaviour.isout(x, y, chessboard); x = x + offX, y = y + offY)
        {
            if (chessboard.getSquares()[x][y].getPiece() == null || chessboard.getSquares()[x][y] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.getSquares()[x][y].getPiece().getPlayer() != player) //if isn't our piece
            {
                if (chessboard.getSquares()[x][y].getPiece().getName().equals("Rook")
                        || chessboard.getSquares()[x][y].getPiece().getName().equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
		return true;
	}
	
	private boolean testBishop(int offX, int offY, Square s, Chessboard chessboard, Square square, Player player)
	{
		for (int x = s.getPozX() + offX, y = s.getPozY() + offY; !PieceBehaviour.isout(x, y, chessboard); x = x + offX, y = y + offY)
        {
            if (chessboard.getSquares()[x][y].getPiece() == null || chessboard.getSquares()[x][y] == square) //if on this square isn't piece
            {
                continue;
            }
            else if (chessboard.getSquares()[x][y].getPiece().getPlayer() != player) //if isn't our piece
            {
                if (chessboard.getSquares()[x][y].getPiece().getName().equals("Bishop")
                        || chessboard.getSquares()[x][y].getPiece().getName().equals("Queen"))
                {
                    return false;
                }
                else
                {
                    break;
                }
            }
            else
            {
                break;
            }
        }
		return true;
	}

	private boolean testKnight(int offX, int offY, Square s, Chessboard chessboard, Square square, Player player)
	{
		int newX = s.getPozX() + offX;
		int newY = s.getPozY() + offY;
		 if (!PieceBehaviour.isout(newX, newY, chessboard))
	        {
	            if (chessboard.getSquares()[newX][newY].getPiece() == null) return true; //if on this square isn't piece
	            else if (chessboard.getSquares()[newX][newY].getPiece().getPlayer() == player) return true; //if is our piece
	            else if (chessboard.getSquares()[newX][newY].getPiece().getName().equals("Knight"))return false;
	        }
		return true;
	}
	
	private boolean testKing(Square s, Chessboard chessboard, Square square, Player player)
	{
		 for (int x = s.getPozX() - 1; x <= s.getPozX() + 1; x++)
	        {
	            for (int y = s.getPozY() - 1; y <= s.getPozY() + 1; y++)
	            {
	                if (!PieceBehaviour.isout(x, y, chessboard)) //out of bounds protection
	                {
	                	Piece piece = chessboard.getSquares()[x][y].getPiece();
	                    if(piece != null && piece.getName() == "King" && piece.getPlayer() != player) return false;
	                }
	            }
	        }
		 return true;
	}
	
	private boolean testPawn(int offX, int offY, Square s, Chessboard chessboard, Square square, Player player)
	{
		if(!PieceBehaviour.isout(s.getPozX() + offX, s.getPozY() + offY, chessboard))
		{
			Piece piece = chessboard.getSquares()[s.getPozX() + offX][s.getPozY() + offY].getPiece();
			if(piece != null && piece.getName() == "Pawn" && piece.getPlayer() != player) return false;
		}
		return true;
	}
}
