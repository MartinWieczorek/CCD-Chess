package pieces;

public class BreakWall extends MoveStraight {

	public BreakWall(int range, boolean canMoveOnEmpty, boolean canMoveOnEnemy, boolean blockedByPieces,
			boolean canMoveUp, boolean canMoveRight, boolean canMoveDown, boolean canMoveLeft, boolean canMoveLeftUp,
			boolean canMoveRightUp, boolean canMoveRightDown, boolean canMoveLeftDown) {
		super(range, canMoveOnEmpty, canMoveOnEnemy, blockedByPieces, canMoveUp, canMoveRight, canMoveDown, canMoveLeft,
				canMoveLeftUp, canMoveRightUp, canMoveRightDown, canMoveLeftDown);
	}
}
