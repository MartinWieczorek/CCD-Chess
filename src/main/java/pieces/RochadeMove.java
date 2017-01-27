package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

public class RochadeMove implements PieceBehaviour {

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
		
		switch(player.getColor()) {
			case white:
				result.addAll(castlingMoves(-1, 0, chessboard, square, player));
				break;
			case black:
				result.addAll(castlingMoves(1, 0, chessboard, square, player));
				break;
			case red:
				result.addAll(castlingMoves(0, 1, chessboard, square, player));
				break;
			case green:
				result.addAll(castlingMoves(0, -1, chessboard, square, player));
				break;
		}
		
		return result;
	}

	private ArrayList<Square> castlingMoves(int offX, int offY, Chessboard chessboard, Square square, Player player) 
	{
		ArrayList<Square> result = new ArrayList<Square>();

		//short Rochade
		if( BehaviourFunktions.checkSpaceAtPosition(square.getPozX() + offX, square.getPozY() + offY, player, chessboard) &&
				BehaviourFunktions.isSafe(chessboard, chessboard.getSquares()[square.getPozX() + offX][square.getPozY() + offY], player) &&
				BehaviourFunktions.checkSpaceAtPosition(square.getPozX() + offX * 2, square.getPozY() + offY * 2, player, chessboard) &&
				BehaviourFunktions.isSafe(chessboard, chessboard.getSquares()[square.getPozX() + offX * 2][square.getPozY() + offY * 2], player) &&
				BehaviourFunktions.checkRookinCastling(square.getPozX() + offX * 3, square.getPozY() + offY * 3, chessboard) ) {
			for (PieceBehaviour behaviour : chessboard.getSquares()[square.getPozX() + offX * 3][square.getPozY() + offY * 3].getPiece().getBehaviours()) {
				if(behaviour.getClass() == RochadeStay.class) {
					result.add(chessboard.getSquares()[square.getPozX() + offX * 2][square.getPozY() + offY * 2]);
				}
			}
		}
		
		//long Rochade
		if( BehaviourFunktions.checkSpaceAtPosition(square.getPozX() - offX, square.getPozY() - offY, player, chessboard) &&
				BehaviourFunktions.isSafe(chessboard, chessboard.getSquares()[square.getPozX() - offX][square.getPozY() - offY], player) &&
				BehaviourFunktions.checkSpaceAtPosition(square.getPozX() - offX * 2, square.getPozY() - offY * 2, player, chessboard) &&
				BehaviourFunktions.isSafe(chessboard, chessboard.getSquares()[square.getPozX() - offX * 2][square.getPozY() - offY * 2], player) &&
				BehaviourFunktions.checkSpaceAtPosition(square.getPozX() - offX * 3, square.getPozY() - offY * 3, player, chessboard) &&
				BehaviourFunktions.isSafe(chessboard, chessboard.getSquares()[square.getPozX() - offX * 3][square.getPozY() - offY * 3], player) &&
				BehaviourFunktions.checkRookinCastling(square.getPozX() - offX * 4, square.getPozY() - offY * 4, chessboard) ){
			for (PieceBehaviour behaviour : chessboard.getSquares()[square.getPozX() - offX * 4][square.getPozY() - offY * 4].getPiece().getBehaviours()) {
				if(behaviour.getClass() == RochadeStay.class) {
					result.add(chessboard.getSquares()[square.getPozX() - offX * 3][square.getPozY() - offY * 3]);
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
		
		int moveDirektionX = endSquare.getPozX() - startSquare.getPozX();
		int moveDirektionY = endSquare.getPozY() - startSquare.getPozY();
		if(Math.abs(moveDirektionX) > 1 || Math.abs(moveDirektionY) > 1) {
			if(moveDirektionX != 0) moveDirektionX = moveDirektionX / Math.abs(moveDirektionX);
			if(moveDirektionY != 0) moveDirektionY = moveDirektionY / Math.abs(moveDirektionY);
			Square originSquare = chessboard.getSquares()[endSquare.getPozX() + moveDirektionX][endSquare.getPozY() + moveDirektionY];
			Square targetSquare = chessboard.getSquares()[endSquare.getPozX() - moveDirektionX][endSquare.getPozY() - moveDirektionY];
			targetSquare.setPiece(originSquare.getPiece());
			originSquare.setPiece(null);
		}
		
		return this;
	}
}
