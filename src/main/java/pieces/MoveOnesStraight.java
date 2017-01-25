package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Enables a variety of horizontal, vertical and diagonal movement patterns
 */

public class MoveOnesStraight extends MoveStraight {

	
	public MoveOnesStraight(int range, boolean canMoveOnEmpty, boolean canMoveOnEnemy, boolean blockedByPieces,
			boolean canMoveUp, boolean canMoveRight, boolean canMoveDown, boolean canMoveLeft, boolean canMoveLeftUp,
			boolean canMoveRightUp, boolean canMoveRightDown, boolean canMoveLeftDown) {
		super(range, canMoveOnEmpty, canMoveOnEnemy, blockedByPieces, canMoveUp, canMoveRight, canMoveDown, canMoveLeft,
				canMoveLeftUp, canMoveRightUp, canMoveRightDown, canMoveLeftDown);
	}

	public PieceBehaviour moveTo(Chessboard chessboard, Square startSquare, Square endSquare, Player player){
		if(startSquare.getPiece() != null) {
			endSquare.setPiece(startSquare.getPiece());
			startSquare.setPiece(null);
		}
		return this;
	}
}
