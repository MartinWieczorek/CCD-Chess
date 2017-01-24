package pieces;

import java.util.ArrayList;

import core.Chessboard;
import core.Player;
import core.Square;

public class RochadeStay extends PieceBehaviour {

	@Override
	public ArrayList<Square> getMoves(Chessboard chessboard, Square square, Player player) {
		return new ArrayList<Square>();
	}

	@Override
	public ArrayList<Square> getUnsaveMoves(Chessboard chessboard, Square square, Player player) {
		return new ArrayList<Square>();
	}

}
