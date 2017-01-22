package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Enables a variety of movement patterns with goes in one direction first and than in another.
 * @author Patrick
 *
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
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {

		ArrayList<Square> result = new ArrayList<Square>();
		ArrayList<int[]> direktions = new ArrayList<int[]>();
		
		if(canMoveUp) direktions.add(new int[]{0,-1});
		if(canMoveRight) direktions.add(new int[]{1,0});
		if(canMoveDown) direktions.add(new int[]{0,1});
		if(canMoveLeft) direktions.add(new int[]{-1,0});
		
		for (int[] direktion : direktions) {
			result.addAll(computeDirektion(0, 0, direktion[0], direktion[1], square, chessboard, player));
			if(direktion[0] != 0) {
    			result.addAll(computeDirektion(range1 * direktion[0], 0, 0, -1, square, chessboard, player));
    			result.addAll(computeDirektion(range1 * direktion[0], 0, 0, 1, square, chessboard, player));
    		}
    		if(direktion[1] != 0) {
    			result.addAll(computeDirektion(0, range1 * direktion[1], -1, 0, square, chessboard, player));
    			result.addAll(computeDirektion(0, range1 * direktion[1], 1, 0, square, chessboard, player));
    		}
		}
		return result;
	}
	
	private ArrayList<Square> computeDirektion(int offsetX, int offsetY, int xDir, int yDir,
			Square square, Chessboard chessboard, Player player) {
		
		ArrayList<Square> result = new ArrayList<Square>();
		for (int x = square.getPozX() + offsetX + xDir, y = square.getPozY() + offsetY + yDir, distanz = 0;
				 !PieceBehaviour.isout(x, y, chessboard) && distanz < range2; x=x+xDir, y=y+yDir, ++distanz)
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
