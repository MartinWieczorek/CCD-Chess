package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/*
 * Rook can move:
|_|_|_|X|_|_|_|_|7
|_|_|_|X|_|_|_|_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|X|X|X|B|X|X|X|X|3
|_|_|_|X|_|_|_|_|2
|_|_|_|X|_|_|_|_|1
|_|_|_|X|_|_|_|_|0
0 1 2 3 4 5 6 7
 *
 */

/**
 * represents the possible movements a Rook can perform
 * @author Patrick
 *
 */
public class RookBehaviour implements PieceBehaviour {
	
	static private RookBehaviour instance;
	
	private RookBehaviour(){}
	
	/**
	 * Instance getter for singleton
	 * @return instance of singleton
	 */
	static public RookBehaviour getInstance()
	{
		if(instance == null){
			instance = new RookBehaviour();
		}
		
		return instance;
	}

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player){
		
		ArrayList<Square> result = new ArrayList<Square>();
		result.addAll(computeDirektion(1, 0, square, chessboard, player));
		result.addAll(computeDirektion(0, 1, square, chessboard, player));
		result.addAll(computeDirektion(0, -1, square, chessboard, player));
		result.addAll(computeDirektion(-1, 0, square, chessboard, player));
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
