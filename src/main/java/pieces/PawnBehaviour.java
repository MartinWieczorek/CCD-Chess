package pieces;

import java.util.ArrayList;
import core.Chessboard;
import core.Player;
import core.Square;

/**
 * represents the possible movements a Pawn can perform
 * @author Patrick
 *
 */
public class PawnBehaviour implements PieceBehaviour {
	
static private PawnBehaviour instance;
	
	private PawnBehaviour(){}
	
	
	/**
	 * Instance getter for singleton
	 * @return instance of singleton
	 */
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
			if(!square.getPiece().isWasMotion()) result.addAll(computeMove(0, 2, square, chessboard, player));
		}
		if(player.getColor() != Player.colors.black)
		{
			result.addAll(computeMove(0, -1, square, chessboard, player));
			if(!square.getPiece().isWasMotion()) result.addAll(computeMove(0, -2, square, chessboard, player));
		}
		if(player.getColor() != Player.colors.red)
		{
			result.addAll(computeMove(-1, 0, square, chessboard, player));
			if(!square.getPiece().isWasMotion()) result.addAll(computeMove(-2, 0, square, chessboard, player));
		}
		if(player.getColor() != Player.colors.green)
		{
			result.addAll(computeMove(1, 0, square, chessboard, player));
			if(!square.getPiece().isWasMotion()) result.addAll(computeMove(2, 0, square, chessboard, player));
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
            		if (chessboard.getKingWhite().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[newX][newY]))
                    {
                        result.add(chessboard.getSquares()[newX][newY]);
                    }
            		break;
            	case black :
            		if (chessboard.getKingBlack().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[newX][newY]))
                    {
                        result.add(chessboard.getSquares()[newX][newY]);
                    }
            		break;
            	case red :
            		if (chessboard.getKingRed().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[newX][newY]))
                    {
                        result.add(chessboard.getSquares()[newX][newY]);
                    }
            		break;
            	case green :
            		if (chessboard.getKingGreen().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[newX][newY]))
                    {
                        result.add(chessboard.getSquares()[newX][newY]);
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
            		if (chessboard.getKingWhite().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[newX][newY]))
                    {
                        result.add(chessboard.getSquares()[newX][newY]);
                    }
            		break;
            	case black :
            		if (chessboard.getKingBlack().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[newX][newY]))
                    {
                        result.add(chessboard.getSquares()[newX][newY]);
                    }
            		break;
            	case red :
            		if (chessboard.getKingRed().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[newX][newY]))
                    {
                        result.add(chessboard.getSquares()[newX][newY]);
                    }
            		break;
            	case green :
            		if (chessboard.getKingGreen().getPiece().willBeSafeWhenMoveOtherPiece(square, chessboard.getSquares()[newX][newY]))
                    {
                        result.add(chessboard.getSquares()[newX][newY]);
                    }
            		break;
        	}
		}
		return result;
	}

}
