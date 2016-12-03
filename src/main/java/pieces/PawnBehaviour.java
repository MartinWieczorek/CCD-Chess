package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/*
|_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|X|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 *
 * Move of a pawn:
|_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|_|X|_|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 * Beats with can take pawn:
|_|_|_|_|_|_|_|_|7
|_|_|_|_|_|_|_|_|6
|_|_|_|_|_|_|_|_|5
|_|_|X|_|X|_|_|_|4
|_|_|_|P|_|_|_|_|3
|_|_|_|_|_|_|_|_|2
|_|_|_|_|_|_|_|_|1
|_|_|_|_|_|_|_|_|0
0 1 2 3 4 5 6 7
 */

public class PawnBehaviour implements PieceBehaviour {
	
static private PawnBehaviour instance;
	
	private PawnBehaviour(){}
	
	static public PawnBehaviour getInstance()
	{
		if(instance == null){
			instance = new PawnBehaviour();
		}
		
		return instance;
	}

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		ArrayList<Square> result = new ArrayList<Square>();
		if(player.getColor() != Player.colors.white)
			{
				result.addAll(computeMove(0, 1, square, chessboard, player));
				if(!square.piece.wasMotion) result.addAll(computeMove(0, 2, square, chessboard, player));
			}
		if(player.getColor() != Player.colors.black)
		{
			result.addAll(computeMove(0, -1, square, chessboard, player));
			if(!square.piece.wasMotion) result.addAll(computeMove(0, -2, square, chessboard, player));
		}
		if(player.getColor() != Player.colors.red)
		{
			result.addAll(computeMove(-1, 0, square, chessboard, player));
			if(!square.piece.wasMotion) result.addAll(computeMove(-2, 0, square, chessboard, player));
		}
		if(player.getColor() != Player.colors.green)
		{
			result.addAll(computeMove(1, 0, square, chessboard, player));
			if(!square.piece.wasMotion) result.addAll(computeMove(2, 0, square, chessboard, player));
		}
		result.addAll(computeTake(1, 1, square, chessboard, player));
		result.addAll(computeTake(1, -1, square, chessboard, player));
		result.addAll(computeTake(-1, 1, square, chessboard, player));
		result.addAll(computeTake(-1, -1, square, chessboard, player));
		return result;
	}
	private ArrayList<Square> computeMove(int x, int y, Square square, Chessboard chessboard, Player player)
	{
		int newX = square.getPozX() + x;
		int newY = square.getPozY() + y;
		ArrayList<Square> result = new ArrayList<Square>();
		if (!PieceBehaviour.isout(newX, newY, chessboard) && PieceBehaviour.checkPieceAtPosition(newX, newY, player, chessboard) &&
				PieceBehaviour.checkSpaceAtPosition(newX, newY, player, chessboard)) //checks if there is an enemy piece or no piece and no King
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
	
	private ArrayList<Square> computeTake(int x, int y, Square square, Chessboard chessboard, Player player)
	{
		int newX = square.getPozX() + x;
		int newY = square.getPozY() + y;
		ArrayList<Square> result = new ArrayList<Square>();
		if (!PieceBehaviour.isout(newX, newY, chessboard) && PieceBehaviour.checkPieceAtPosition(newX, newY, player, chessboard) &&
				!PieceBehaviour.checkSpaceAtPosition(newX, newY, player, chessboard)) //checks if there is an enemy piece or no piece and no King
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
