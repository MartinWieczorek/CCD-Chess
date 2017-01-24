package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Enables a variety of movement patterns with goes in one direction first and than in another.
 */

public class MoveTwoDirections extends PieceBehaviour {
	
	int range1;
	int range2;
	private boolean canMoveOnEmpty;
	private boolean canMoveOnEnemy;
	private boolean blockedByPieces;
	private boolean canMoveUp;
	private boolean canMoveRight;
	private boolean canMoveDown;
	private boolean canMoveLeft;
	
	/**
	 * 
	 * @param range1
	 * @param range2
	 * @param canMoveOnEmpty
	 * @param canMoveOnEnemy
	 * @param blockedByPieces
	 * @param canMoveUp
	 * @param canMoveRight
	 * @param canMoveDown
	 * @param canMoveLeft
	 */
	public MoveTwoDirections(int range1, int range2, boolean canMoveOnEmpty, boolean canMoveOnEnemy, boolean blockedByPieces,
			boolean canMoveUp, boolean canMoveRight, boolean canMoveDown, boolean canMoveLeft) {
		
		this.range1 = range1;
		this.range2 = range2;
		this.canMoveOnEmpty = canMoveOnEmpty;
		this.canMoveOnEnemy = canMoveOnEnemy;
		this.blockedByPieces = blockedByPieces;
		this.canMoveUp = canMoveUp;
		this.canMoveRight = canMoveRight;
		this.canMoveDown = canMoveDown;
		this.canMoveLeft = canMoveLeft;
	}
	
	@Override
	public ArrayList<Square> getUnsaveMoves(Chessboard chessboard, Square square, Player player) {

		ArrayList<Square> result = new ArrayList<Square>();
		ArrayList<int[]> direktions = new ArrayList<int[]>();
		
		if(canMoveUp) direktions.add(new int[]{0,-1});
		if(canMoveRight) direktions.add(new int[]{1,0});
		if(canMoveDown) direktions.add(new int[]{0,1});
		if(canMoveLeft) direktions.add(new int[]{-1,0});
		
		for (int[] direktion : direktions) {
			if(computeDirektion(0, 0, direktion[0], direktion[1], square, chessboard, player))
			if(direktion[0] != 0) {
				
				int x = square.getPozX() + range1 * direktion[0];
				int y = square.getPozY() - range2;
				if(computeDirektion(range1 * direktion[0], 0, 0, -1, square, chessboard, player) &&
    					!BehaviourFunktions.isout(x, y, chessboard) &&
    					(BehaviourFunktions.checkSpaceAtPosition(x, y, player, chessboard) && canMoveOnEmpty ||
    		            		BehaviourFunktions.enemyPieceOnPosition(x, y, chessboard, player) && canMoveOnEnemy)){
					result.add(chessboard.getSquares()[x][y]);
				}
				
				x = square.getPozX() + range1 * direktion[0];
				y = square.getPozY() + range2;
				if(computeDirektion(range1 * direktion[0], 0, 0, 1, square, chessboard, player) &&
    					!BehaviourFunktions.isout(x, y, chessboard) &&
    					(BehaviourFunktions.checkSpaceAtPosition(x, y, player, chessboard) && canMoveOnEmpty ||
    							BehaviourFunktions.enemyPieceOnPosition(x, y, chessboard, player) && canMoveOnEnemy)){
					result.add(chessboard.getSquares()[x][y]);
				}
    		}
    		if(direktion[1] != 0) {
    			
    			int x =square.getPozX() - range2;
				int y = square.getPozY() + range1 * direktion[1];
    			if(computeDirektion(0, range1 * direktion[1], -1, 0, square, chessboard, player) &&
    					!BehaviourFunktions.isout(x, y, chessboard) &&
    					(BehaviourFunktions.checkSpaceAtPosition(x, y, player, chessboard) && canMoveOnEmpty ||
    							BehaviourFunktions.enemyPieceOnPosition(x, y, chessboard, player) && canMoveOnEnemy)){
					result.add(chessboard.getSquares()[x][y]);
				}
    			x = square.getPozX() + range2;
				y = square.getPozY() + range1 * direktion[1];
    			if(computeDirektion(0, range1 * direktion[1], 1, 0, square, chessboard, player) &&
    					!BehaviourFunktions.isout(x, y, chessboard) &&
    					(BehaviourFunktions.checkSpaceAtPosition(x, y, player, chessboard) && canMoveOnEmpty ||
    							BehaviourFunktions.enemyPieceOnPosition(x, y, chessboard, player) && canMoveOnEnemy)){
					result.add(chessboard.getSquares()[x][y]);
				}
    		}
		}
		return result;
	}
	
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
	
	private boolean computeDirektion(int offsetX, int offsetY, int xDir, int yDir,
			Square square, Chessboard chessboard, Player player) {
		
		for (int x = square.getPozX() + offsetX + xDir, y = square.getPozY() + offsetY + yDir, distanz = 0;
				 !BehaviourFunktions.isout(x, y, chessboard) && distanz < range2; x=x+xDir, y=y+yDir, ++distanz)
        {
            if (BehaviourFunktions.checkSpaceAtPosition(x, y, player, chessboard) && canMoveOnEmpty ||
            		BehaviourFunktions.enemyPieceOnPosition(x, y, chessboard, player) && canMoveOnEnemy)
            {
            	if (!BehaviourFunktions.checkSpaceAtPosition(x, y, player, chessboard) && blockedByPieces)
	            {
	                return false; //we've to break because we cannot go beside other piece!!
	            }
            }
            else if (blockedByPieces)
            {
                return false; //we've to break because we cannot go beside other piece!!
            }
        }
		return true;
	}
}
