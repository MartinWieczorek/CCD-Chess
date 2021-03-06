package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Enables a variety of horizontal, vertical and diagonal movement patterns
 */

public class MoveStraight implements PieceBehaviour {
	
	int range;
	protected boolean canMoveOnEmpty;
	protected boolean canMoveOnEnemy;
	protected boolean blockedByPieces;
	protected boolean canMoveUp;
	protected boolean canMoveRight;
	protected boolean canMoveDown;
	protected boolean canMoveLeft;
	protected boolean canMoveLeftUp;
	protected boolean canMoveRightUp;
	protected boolean canMoveRightDown;
	protected boolean canMoveLeftDown;
	
	/**
	 * @param range Indicates the number of squares which the behaviour can bridge.
	 * @param canMoveOnEmpty Behaviour can move a square where no piece is standing.
	 * @param canMoveOnEnemy Behaviour can move a square where an enemy piece is standing.
	 * @param blockedByPieces False if the behaviour can move past other pieces.
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
	public ArrayList<Square> getUnsaveMoves(Chessboard chessboard, Square square, Player player) {

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
	
	private ArrayList<Square> computeDirektion(int xDir, int yDir, Square square, Chessboard chessboard, Player player)
	{
		ArrayList<Square> result = new ArrayList<Square>();
		 for (int x = square.getPozX() + xDir, y = square.getPozY() + yDir, distanz = 1;
				 !BehaviourFunktions.isout(x, y, chessboard) && distanz <= range;
				 x=x+xDir, y=y+yDir, ++distanz)
	        {
	            if (BehaviourFunktions.checkSpaceAtPosition(x, y, player, chessboard) && canMoveOnEmpty ||
	            		BehaviourFunktions.enemyPieceOnPosition(x, y, chessboard, player) && canMoveOnEnemy &&
	            		BehaviourFunktions.isTarget(x, y, chessboard, square, this, player))
	            {
	            	result.add(chessboard.getSquares()[x][y]);
	            	 
	            	if (!BehaviourFunktions.checkSpaceAtPosition(x, y, player, chessboard) && blockedByPieces)
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
	
	public PieceBehaviour moveTo(Chessboard chessboard, Square startSquare, Square endSquare, Player player){
		if(startSquare.getPiece() != null) {
			endSquare.setPiece(startSquare.getPiece());
			startSquare.setPiece(null);
		}
		return null;
	}
}
