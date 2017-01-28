package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;

public class PieceFactory {

	private static PieceFactory factory;
	
	private PieceFactory(){}
	
	 public enum PieceType
	    {
		 Rook,Bishop, King, Queen, Wall, Pawn, Knight
	    }
	
	public static PieceFactory getInstance(){
		if(factory == null){
			factory = new PieceFactory();
		}
		
		return factory;
	}
	
	public Piece createNewPiece(Chessboard chessboard, Player player, String name){
		Piece newPiece = null;
		switch(name){
		case "Rook":
			newPiece = createRook(chessboard, player);
			break;
		case "Bishop":
			newPiece = createBishop(chessboard, player);
			break;
		case "King":
			newPiece = createKing(chessboard, player);
			break;
		case "Queen":
			newPiece = createQueen(chessboard, player);
			break;
		case "Wall":
			newPiece = createWall(chessboard, player);
			break;
		case "Knight":
			newPiece = createKnight(chessboard, player);
			break;
		case "Pawn":
			newPiece = createPawn(chessboard, player);
			break;
		}
		return newPiece;
	}
	
	private Piece createRook(Chessboard chessboard, Player player){
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		tmp.add(new BreakWall(Integer.MAX_VALUE, true, true, true, true, true, true, true, false, false, false, false));
		tmp.add(new RochadeStay());
		return new Piece(chessboard, player, tmp, "Rook", "R");
	}
	
	private Piece createBishop(Chessboard chessboard, Player player){
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		tmp.add(new MoveStraight(Integer.MAX_VALUE, true, true, true, false, false, false, false, true, true, true, true));
		return new Piece(chessboard, player, tmp, "Bishop", "B");
	}
	
	private Piece createKing(Chessboard chessboard, Player player){
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		tmp.add(new MoveSaveStraight(1, true, true, true, true, true, true, true, true, true, true, true));
		tmp.add(new RochadeMove());
		return new Piece(chessboard, player, tmp, "King", "K");
	}
	
	private Piece createQueen(Chessboard chessboard, Player player){
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		tmp.add(new BreakWall(Integer.MAX_VALUE, true, true, true, true, true, true, true, true, true, true, true));
		return new Piece(chessboard, player, tmp, "Queen", "Q");
	}
	
	private Piece createWall(Chessboard chessboard, Player player){
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		tmp.add(new MoveStraight(1, true, false, true, true, true, true, true, false, false, false, false));
		return new Piece(chessboard, player, tmp, "Wall", "W");
	}
	
	private Piece createKnight(Chessboard chessboard, Player player){
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		tmp.add(new MoveTwoDirections(2 ,1 , true, true, false, true, true, true, true));
		return new Piece(chessboard, player, tmp, "Knight", "N");
	}
	
	private Piece createPawn(Chessboard chessboard, Player player){
		ArrayList<PieceBehaviour> tmp = new ArrayList<PieceBehaviour>();
		switch (player.getColor())
    	{
        	case white :
        		tmp.add(new MoveStraight(1, true, false, true, true, true, false, true, false, false, false, false));
        		tmp.add(new MoveOnesStraight(2, true, false, true, true, true, false, true, false, false, false, false));
        		break;
        	case black :
        		tmp.add(new MoveStraight(1, true, false, true, false, true, true, true, false, false, false, false));
        		tmp.add(new MoveOnesStraight(2, true, false, true, false, true, true, true, false, false, false, false));
        		break;
        	case red :
        		tmp.add(new MoveStraight(1, true, false, true, true, true, true, false, false, false, false, false));
        		tmp.add(new MoveOnesStraight(2, true, false, true, true, true, true, false, false, false, false, false));
        		break;
        	case green :
        		tmp.add(new MoveStraight(1, true, false, true, true, false, true, true, false, false, false, false));
        		tmp.add(new MoveOnesStraight(2, true, false, true, true, false, true, true, false, false, false, false));
        		break;
    	}
		tmp.add(new MoveStraight(1, false, true, true, false, false, false, false, true, true, true, true));
		return new Piece(chessboard, player, tmp, "Pawn", "P");
	}
}
