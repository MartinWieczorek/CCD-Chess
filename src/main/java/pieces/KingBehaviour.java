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
public class KingBehaviour extends PieceBehaviour {
	
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
	public ArrayList<Square> getUnsaveMoves(Chessboard chessboard, Square square, Player player) {
		return  new ArrayList<Square>();
	}
	
	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> result = new ArrayList<Square>();
        for (int x = square.getPozX() - 1; x <= square.getPozX() + 1; x++)
        {
            for (int y = square.getPozY() - 1; y <= square.getPozY() + 1; y++)
            {
                if (!BehaviourFunktions.isout(x, y, chessboard)) //out of bounds protection
                {
                    if (square == chessboard.getSquares()[x][y]) continue; //if we're checking square on which is King
                    if (BehaviourFunktions.enemyPieceOnPosition(x, y, chessboard, player)) //if square is empty
                    {
                        if (BehaviourFunktions.isSafe(chessboard, chessboard.getSquares()[x][y], player))
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
		if (!square.getPiece().isWasMotion() && BehaviourFunktions.isSafe(chessboard, square, player)) //check if king was not moved before
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
                    for (int x = square.getPozX() + offX, y = square.getPozY() + offY; !BehaviourFunktions.isout(x, y, chessboard); x = x + offX, y = y + offY)
                    {
                        if (chessboard.getSquares()[x][y].getPiece() != null && chessboard.getSquares()[x][y].getPiece().getName() != "Rook") //if square is not empty and no rook
                        {
                            canCastling = false;
                            break;
                        }
                    }
                    if (canCastling && BehaviourFunktions.isSafe(chessboard, square1, player) && BehaviourFunktions.isSafe(chessboard, square2, player)) //can do castling when square1 and square2 are not checked
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
                	for (int x = square.getPozX() - offX, y = square.getPozY() - offY; !BehaviourFunktions.isout(x, y, chessboard); x = x - offX, y = y - offY)
                    {
                        if (chessboard.getSquares()[x][y].getPiece() != null  && chessboard.getSquares()[x][y].getPiece().getName() != "Rook") //if square is not empty and no rook
                        {
                            canCastling = false;
                            break;
                        }
                    }
                    if (canCastling && BehaviourFunktions.isSafe(chessboard, square1, player) && BehaviourFunktions.isSafe(chessboard, square2, player))  //can do castling when square1 and square2 are not checked
                    {
                    	result.add(square2);
                    }
                }
            }
        }
		return result;
	}
	
	public PieceBehaviour moveTo(Chessboard chessboard, Square startSquare, Square endSquare, Player player) {
		return null;
	}
}
