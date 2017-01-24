package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

public class RochadeMove extends PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		// TODO Auto-generated method stub
		return new ArrayList<Square>();
	}

	@Override
	public ArrayList<Square> getUnsaveMoves(Chessboard chessboard, Square square, Player player) {
		// TODO Auto-generated method stub
		return new ArrayList<Square>();
	}

	public PieceBehaviour moveTo(Chessboard chessboard, Square startSquare, Square endSquare, Player player){
		if(startSquare.getPiece() != null){
			endSquare.setPiece(startSquare.getPiece());
			startSquare.setPiece(null);
		}
		return this;
	}
}
