package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Enables a variety of horizontal, vertical and diagonal movement patterns
 * @author Patrick
 *
 */

public class MoveStraight extends PieceBehaviour {
	
	int range;
	private boolean canMoveOnEmpty;
	private boolean canMoveOnEnemy;
	private boolean blockedByPieces;
	private boolean canMoveUp;
	private boolean canMoveRight;
	private boolean canMoveDown;
	private boolean canMoveLeft;
	private boolean canMoveLeftUp;
	private boolean canMoveRightUp;
	private boolean canMoveRightDown;
	private boolean canMoveLeftDown;
	
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
	public MoveStraight(int range, boolean canMoveOnEmpty, boolean canMoveOnEnemy, boolean blockedByPieces,
			boolean canMoveUp, boolean canMoveRight, boolean canMoveDown, boolean canMoveLeft,
			boolean canMoveLeftUp, boolean canMoveRightUp, boolean canMoveRightDown, boolean canMoveLeftDown) {
		
		this.range = range;
		this.canMoveOnEmpty = canMoveOnEmpty;
		this.canMoveOnEnemy = canMoveOnEnemy;
		this.blockedByPieces = blockedByPieces;
		this.canMoveUp = canMoveUp;
		this.canMoveRight = canMoveRight;
		this.canMoveDown = canMoveDown;
		this.canMoveLeft = canMoveLeft;
		this.canMoveLeftUp = canMoveLeftUp;
		this.canMoveRightUp = canMoveRightUp;
		this.canMoveRightDown = canMoveRightDown;
		this.canMoveLeftDown = canMoveLeftDown;
	}
	
	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {

		ArrayList<Square> result = new ArrayList<Square>();
		ArrayList<int[]> direktions = new ArrayList<int[]>();
		
		if(canMoveUp) direktions.add(new int[]{0,-1});
		if(canMoveRight) direktions.add(new int[]{1,0});
		if(canMoveDown) direktions.add(new int[]{0,1});
		if(canMoveLeft) direktions.add(new int[]{-1,0});
		if(canMoveLeftUp) direktions.add(new int[]{-1,-1});
		if(canMoveRightUp) direktions.add(new int[]{1,-1});
		if(canMoveRightDown) direktions.add(new int[]{1,1});
		if(canMoveLeftDown) direktions.add(new int[]{-1,1});
		
		for (int[] direktion : direktions) {
			result.addAll(computeDirektion(direktion[0], direktion[1], square, chessboard, player));
		}
		
		return result;
	}
	
	private ArrayList<Square> computeDirektion(int xDir, int yDir, Square square, Chessboard chessboard, Player player)
	{
		ArrayList<Square> result = new ArrayList<Square>();
		 for (int x = square.getPozX() + xDir, y = square.getPozY() + yDir, distanz = 1;
				 !PieceBehaviour.isout(x, y, chessboard) && distanz <= range;
				 x=x+xDir, y=y+yDir, ++distanz)
	        {
	            if (PieceBehaviour.checkSpaceAtPosition(x, y, player, chessboard) && canMoveOnEmpty ||
	            		PieceBehaviour.enemyPieceOnPosition(x, y, chessboard, player) && canMoveOnEnemy)
	            {
	            	switch (player.getColor())
	            	{
		            	case white :
		            		if (chessboard.getKingWhite().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[x][y]))
		                    {
		                        result.add(chessboard.getSquares()[x][y]);
		                    }
		            		break;
		            	case black :
		            		if (chessboard.getKingBlack().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[x][y]))
		                    {
		                        result.add(chessboard.getSquares()[x][y]);
		                    }
		            		break;
		            	case red :
		            		if (chessboard.getKingRed().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[x][y]))
		                    {
		                        result.add(chessboard.getSquares()[x][y]);
		                    }
		            		break;
		            	case green :
		            		if (chessboard.getKingGreen().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[x][y]))
		                    {
		                        result.add(chessboard.getSquares()[x][y]);
		                    }
		            		break;
	            	}
	            	if (!PieceBehaviour.checkSpaceAtPosition(x, y, player, chessboard) && blockedByPieces)
		            {
		                break; //we've to break because we cannot go beside other piece!!
		            }
	            }
	            else if (blockedByPieces)
	            {
	                break; //we've to break because we cannot go beside other piece!!
	            }
	        }
		return result;
	}
}
