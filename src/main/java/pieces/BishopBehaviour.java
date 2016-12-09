package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/**
 * Bishop can move:
|_|_|_|_|_|_|_|X|7
|X|_|_|_|_|_|X|_|6
|_|X|_|_| |X|_|_|5
|_|_|X|_|X|_|_|_|4
|_|_|_|B|_|_|_|_|3
|_| |X|_|X|_|_|_|2
|_|X|_|_|_|X|_|_|1
|X|_|_|_|_|_|X|_|0
0 1 2 3 4 5 6 7
 */

public class BishopBehaviour implements PieceBehaviour {

static private BishopBehaviour instance;
	
	private BishopBehaviour(){}
	
	static public BishopBehaviour getInstance()
	{
		if(instance == null){
			instance = new BishopBehaviour();
		}
		
		return instance;
	}
	
	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		
		ArrayList<Square> result = new ArrayList<Square>();
		result.addAll(computeDirektion(1, 1, square, chessboard, player));
		result.addAll(computeDirektion(-1, 1, square, chessboard, player));
		result.addAll(computeDirektion(1, -1, square, chessboard, player));
		result.addAll(computeDirektion(-1, -1, square, chessboard, player));
		
		return result;
	}
	
	private ArrayList<Square> computeDirektion(int xDir, int yDir, Square square, Chessboard chessboard, Player player)
	{
		ArrayList<Square> result = new ArrayList<Square>();
		 for (int x = square.getPozX() + xDir, y = square.getPozY() + yDir; !PieceBehaviour.isout(x, y, chessboard); x=x+xDir, y=y+yDir)
	        {
	            if (PieceBehaviour.checkPieceAtPosition(x, y, player, chessboard)) //checks if there is an enemy piece, no piece and no King
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
	            	if (PieceBehaviour.enemyPieceOnPosition(x, y, chessboard, player))
		            {
		                break; //we've to break because we cannot go beside other piece!!
		            }
	            }
	        	else
	            {
	            	break; //we've to break because we cannot go beside other piece!!
	            }
	        }
		return result;
	}
}