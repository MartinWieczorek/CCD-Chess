package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Enables a variety of horizontal, vertical and diagonal movement patterns
 */

public class MoveSaveStraight extends MoveStraight {
	
	public MoveSaveStraight(int range, boolean canMoveOnEmpty, boolean canMoveOnEnemy, boolean blockedByPieces,
			boolean canMoveUp, boolean canMoveRight, boolean canMoveDown, boolean canMoveLeft, boolean canMoveLeftUp,
			boolean canMoveRightUp, boolean canMoveRightDown, boolean canMoveLeftDown) {
		super(range, canMoveOnEmpty, canMoveOnEnemy, blockedByPieces, canMoveUp, canMoveRight, canMoveDown, canMoveLeft,
				canMoveLeftUp, canMoveRightUp, canMoveRightDown, canMoveLeftDown);
	}

	/**
	 * 
	 * @param range
	 * @param canMoveOnEmpty
	 * @param canMoveOnEnemy
	 * @param blockedByPieces
	 * @param canMoveUp
	 * @param canMoveRight
	 * @param canMoveDown
	 * @param canMoveLeft
	 * @param canMoveLeftUp
	 * @param canMoveRightUp
	 * @param canMoveRightDown
	 * @param canMoveLeftDown
	 */
	
	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> posibilities = getUnsaveMoves(chessboard, square, player);
		ArrayList<Square> result = new ArrayList<Square>();
		
		for (Square posibility : posibilities) {
			Piece dummy = new Piece(chessboard, player, new ArrayList<PieceBehaviour>(), "Pawn", "D");
			Piece oldPice = posibility.getPiece();
			posibility.setPiece(dummy);
			if(BehaviourFunktions.isSafe(chessboard, posibility, player)) {
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
			posibility.setPiece(oldPice);
		}
		return result;
	}
}
