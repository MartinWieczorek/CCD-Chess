package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

// knight all moves
//  _______________ Y:
// |_|_|_|_|_|_|_|_|7
// |_|_|_|_|_|_|_|_|6
// |_|_|2|_|3|_|_|_|5
// |_|1|_|_|_|4|_|_|4
// |_|_|_|K|_|_|_|_|3
// |_|8|_|_|_|5|_|_|2
// |_|_|7|_|6|_|_|_|1
// |_|_|_|_|_|_|_|_|0
//X:0 1 2 3 4 5 6 7
//

public class KnightBehaviour implements PieceBehaviour {
	
static private KnightBehaviour instance;
	
	private KnightBehaviour(){}
	
	static public KnightBehaviour getInstance()
	{
		if(instance == null){
			instance = new KnightBehaviour();
		}
		
		return instance;
	}

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player)
	{
		ArrayList<Square> result = new ArrayList<Square>();
		result.addAll(checkPosition(-2,1, square, chessboard, player));
		result.addAll(checkPosition(-1,2, square, chessboard, player));
		result.addAll(checkPosition(1,2, square, chessboard, player));
		result.addAll(checkPosition(2,1, square, chessboard, player));
		result.addAll(checkPosition(2,-1, square, chessboard, player));
		result.addAll(checkPosition(1,-2, square, chessboard, player));
		result.addAll(checkPosition(-1,-2, square, chessboard, player));
		result.addAll(checkPosition(-2,-1, square, chessboard, player));
		
		return result;
	}
	
	private ArrayList<Square> checkPosition (int offsetX, int offsetY, Square square, Chessboard chessboard, Player player)
	{
		int newX = square.getPozX() + offsetX;
		int newY = square.getPozY() + offsetY;
		ArrayList<Square> result = new ArrayList<Square>();
		
		if (!PieceBehaviour.isout(newX, newY, chessboard) && PieceBehaviour.checkPieceAtPosition(newX, newY, player, chessboard))
        {
			switch (player.getColor())
        	{
            	case white :
            		if (chessboard.kingWhite.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                    {
                        result.add(chessboard.squares[newX][newY]);
                    }
            		break;
            	case black :
            		if (chessboard.kingBlack.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                    {
                        result.add(chessboard.squares[newX][newY]);
                    }
            		break;
            	case red :
            		if (chessboard.kingRed.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                    {
                        result.add(chessboard.squares[newX][newY]);
                    }
            		break;
            	case green :
            		if (chessboard.kingGreen.willBeSafeWhenMoveOtherPiece(square, chessboard.squares[newX][newY]))
                    {
                        result.add(chessboard.squares[newX][newY]);
                    }
            		break;
        	}
        }
		return result;
	}

}
