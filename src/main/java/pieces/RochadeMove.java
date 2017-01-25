package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

public class RochadeMove extends PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> posibilities = getUnsaveMoves(chessboard, square, player);
		ArrayList<Square> result = new ArrayList<Square>();
		
		for (Square posibility : posibilities) {
			switch (player.getColor())
	    	{
	        	case white :
	        		if (chessboard.getKingWhite().getPiece().willBeSafeWhenMoveOtherPiece(square, posibility))
	                {
	                    result.add(posibility);
	                }
	        		break;
	        	case black :
	        		if (chessboard.getKingBlack().getPiece().willBeSafeWhenMoveOtherPiece(square, posibility))
	                {
	                    result.add(posibility);
	                }
	        		break;
	        	case red :
	        		if (chessboard.getKingRed().getPiece().willBeSafeWhenMoveOtherPiece(square, posibility))
	                {
	                    result.add(posibility);
	                }
	        		break;
	        	case green :
	        		if (chessboard.getKingGreen().getPiece().willBeSafeWhenMoveOtherPiece(square, posibility))
	                {
	                    result.add(posibility);
	                }
	        		break;
	    	}
		}
		return result;
	}

	@Override
	public ArrayList<Square> getUnsaveMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> result = new ArrayList<Square>();
		

		
		return result;
	}

	private ArrayList<Square> castlingMoves(int offX, int offY, Chessboard chessboard, Square square, Player player) 
	{
		ArrayList<Square> result = new ArrayList<Square>();
		
		
		
		if (BehaviourFunktions.isSafe(chessboard, square, player))
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
	
	public PieceBehaviour moveTo(Chessboard chessboard, Square startSquare, Square endSquare, Player player){
		if(startSquare.getPiece() != null){
			endSquare.setPiece(startSquare.getPiece());
			startSquare.setPiece(null);
		}
		return this;
	}
}
